package com.todo1.app.security;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.todo1.app.auth.handler.LoginSuccessHandler;

/**
 * Clase para la configuración de la Seguridad de la aplicación
 * 
 * la anotacion @EnableGlobalMethodSecurity permite que se puedan usar 
 * las anotaciones: @Secured("ROLE_ADMIN") en los controladores
 *  
 * @author Gabriel Eguiguren
 *
 */
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter  {
	
	/**
	 * Encriptador de texto para datos sensibles
	 * 
	 * @return instancia de TextEncryptor
	 */
	@Bean
	public TextEncryptor encriptadorTexto() {
		return  Encryptors.delux("LLAVE_PRIVADA_SECRETA_12345678", new String(Hex.encode("salt".getBytes(Charset.forName("utf-8")))));
	}
	
	
	/**
	 * Funcion no reversible para Hash para datos como la contraseña de usuario
	 * 
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	LoginSuccessHandler successHandler;
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		// utiliza BCrypt
		PasswordEncoder encoder = passwordEncoder();
		UserBuilder userbuilder = User.builder().passwordEncoder(password -> encoder.encode(password));
		
		// por el momento no se obtienen los clientes / usuarios de la base de datos
		builder.inMemoryAuthentication()
		.withUser(userbuilder.username("admin").password("123").roles("ADMIN", "USER"))
		.withUser(userbuilder.username("user").password("123").roles("USER"));
	}


	/**
	 * Configuro explicitamente ciertos recursos como publicos
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		

		// el acceso al listado de productos no requiere autenticacion
		http.authorizeRequests().antMatchers("/css/**", "/js/**", "/images/**", "/producto/listar", "/producto/").permitAll()
		.antMatchers("/uploads/**").hasAnyRole("USER")
		.anyRequest().authenticated()
		.and()
			.formLogin()
				.successHandler(successHandler)
				.loginPage("/login").permitAll() //aqui mapeo al controller para que no muestre el FORM login por defecto 
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
	}
}
