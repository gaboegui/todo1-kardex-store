package com.todo1.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase para configurar e direccionamiento al error 403: Acceso Denegado
 * 
 * @author Gabriel E.
 *
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry ) {
		
		registry.addViewController("/error_403").setViewName("error_403");
	}

}
