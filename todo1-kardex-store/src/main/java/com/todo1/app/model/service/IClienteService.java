package com.todo1.app.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.todo1.app.model.entity.Cliente;
import com.todo1.app.model.entity.Factura;
import com.todo1.app.model.entity.Producto;

public interface IClienteService {
	
	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public Cliente findOne(Long id);
	
	public void save(Cliente cliente);
	
	public void delete(Long id);
	
	public Cliente findByUsername(String username);

	public List<Producto> findByNombre(String term); 
	
	public Producto findProductoById(Long id);
	
	public void saveFactura(Factura factura);
	
	public Factura findFacturaById(Long id);
	
	public void deleteFactura(Long id);
	
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
	
	public Cliente fetchByIdWithFacturas(Long id);
	

}
