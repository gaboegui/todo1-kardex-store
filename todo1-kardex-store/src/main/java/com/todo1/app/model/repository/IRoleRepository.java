package com.todo1.app.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.todo1.app.model.entity.Role;


/**
 * La administracion de Roles por el momento se lo realizara via API, no se crearan vistas para ello
 * 
 * Al añadir @RepositoryRestResource(path = "/roles") se crean automaticamente los 
 * metodos CRUD en un controlador, y queda totalmente funcional la API REST
 *
 * 
 * @author Gabriel Eguiguren
 *
 */
@RepositoryRestResource(path = "roles")
public interface IRoleRepository extends CrudRepository<Role, Long> {
	
	
}
