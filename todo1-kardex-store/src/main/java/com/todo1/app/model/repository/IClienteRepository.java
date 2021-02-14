package com.todo1.app.model.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.todo1.app.model.entity.Cliente;

 
 /**
  * PagingAndSortingRepository extiende CrudRepository y se lo cambia para hacer paginación
  * 
  * @author Gabriel Eguiguren
  *
  */
public interface IClienteRepository extends PagingAndSortingRepository<Cliente, Long> {
	

	@Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")
	public Cliente fetchByIdWithFacturas(Long id);
}
