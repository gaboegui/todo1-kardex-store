package com.todo1.app.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo1.app.model.entity.Cliente;
import com.todo1.app.model.entity.Factura;
import com.todo1.app.model.entity.Producto;
import com.todo1.app.model.repository.IClienteRepository;
import com.todo1.app.model.repository.IFacturaRepository;
import com.todo1.app.model.repository.IProductoRepository;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteRepository clienteDao;

	@Autowired
	private IProductoRepository productoDao;
	
	@Autowired
	private IFacturaRepository facturaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String term) {
		
		return productoDao.findByNombreLikeIgnoreCase("%"+ term + "%");
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {

		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {

		facturaDao.save(factura);
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(Long id) {
		
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteFactura(Long id) {

		facturaDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id) {

		return facturaDao.fetchByIdWithClienteWithItemFacturaWithProducto(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente fetchByIdWithFacturas(Long id) {
		
		return clienteDao.fetchByIdWithFacturas(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findByUsername(String username) {
		
		return clienteDao.findByUsername(username);
	}

}
