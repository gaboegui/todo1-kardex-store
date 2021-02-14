package com.todo1.app.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.todo1.app.model.entity.Kardex;

public interface IKardexService {
	
	public Page<Kardex> findAll(Pageable pageable);
	
	public Kardex findOne(Long id);
	
	public void save(Kardex kardex);
	
	public void delete(Long id);
	
	public Kardex encontrarUltimoMovimientoProducto(Long productoId);

}
