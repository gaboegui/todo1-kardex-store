package com.todo1.app.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.todo1.app.model.entity.Factura;
import com.todo1.app.model.entity.Producto;

public interface IProductoService {
	
	public List<Producto> findAll();
	
	public Page<Producto> findAll(Pageable pageable);
	
	public Producto findOne(Long id);
	
	public void save(Producto producto);
	
	public void delete(Long id);

	public List<Producto> findByNombre(String term); 
	
	public void actualizarKardex(Factura factura);
	

	
	

}
