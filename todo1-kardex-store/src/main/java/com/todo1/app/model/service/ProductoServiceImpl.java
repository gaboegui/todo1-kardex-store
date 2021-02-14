package com.todo1.app.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo1.app.model.entity.Producto;
import com.todo1.app.model.repository.IProductoRepository;

/**
 * Servicio con metodo de manipulación de Productos
 * 
 * @author Gabriel Eguiguren
 *
 */
@Service
public class ProductoServiceImpl implements IProductoService {


	@Autowired
	private IProductoRepository productoDao;
	

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String term) {
		return productoDao.findByNombre(term);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findOne(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void save(Producto producto) {
		productoDao.save(producto);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		productoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Producto> findAll(Pageable pageable) {

		return productoDao.findAll(pageable);
	}

	
	

}
