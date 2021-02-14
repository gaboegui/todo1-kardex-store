package com.todo1.app.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.todo1.app.model.entity.Kardex;

public interface IKardexRepository extends PagingAndSortingRepository<Kardex, Long> {
	
	/**
	 * Busca la ultima ocurrencia en el kardex de un producto
	 * 
	 * @param id identificador del producto 
	 * @return
	 */
	@Query("select k from Kardex k where k.producto.id=?1 and k.fechaRegistro =(select max(k1.fechaRegistro) from Kardex k1  where k1.producto.id = k.producto.id )")
	public Kardex encontrarLastKardexOperationByProductoId(Long id);
}
