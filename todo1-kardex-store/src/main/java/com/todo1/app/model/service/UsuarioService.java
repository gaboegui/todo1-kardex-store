package com.todo1.app.model.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo1.app.model.entity.Cliente;

/***
 * Servicio para manejar lo relacionado al login 
 * 
 * @author Gabriel Eguiguren
 *
 */
@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {

	private Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	ObjectFactory<HttpSession> httpSessionFactory;
	
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {
			Cliente usuario = clienteService.findByUsername(username);
			usuario.getRoles();
			

			// convierto la lista de Roles a lista de Authorities
			List<GrantedAuthority> authorities = usuario.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getNombre()))
					.collect(Collectors.toList());

			log.info("Usuario autenticado: " + username);
			
			HttpSession session = httpSessionFactory.getObject();
			session.setAttribute("usuario", usuario);

			return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
					authorities);
			
		} catch (Exception e) {
			String error ="Error de login: no existe en la BD el usuario: ".concat(username);
			
			log.error(error, e.getMessage());
			throw new UsernameNotFoundException(error);
		}

	}

	@Override
	public Cliente findByUsername(String username) {
		return clienteService.findByUsername(username);
	}
	
}
