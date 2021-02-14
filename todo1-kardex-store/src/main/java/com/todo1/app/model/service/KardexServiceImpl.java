package com.todo1.app.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo1.app.model.entity.Kardex;
import com.todo1.app.model.repository.IKardexRepository;

/**
 * Servicio con metodo de manipulación de Kardexs
 * 
 * @author Gabriel Eguiguren
 *
 */
@Service
public class KardexServiceImpl implements IKardexService {


	@Autowired
	private IKardexRepository kardexDao;
	

	@Override
	@Transactional(readOnly = true)
	public Kardex findOne(Long id) {
		return kardexDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void save(Kardex kardex) {
		kardexDao.save(kardex);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		kardexDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Kardex> findAll(Pageable pageable) {

		return kardexDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Kardex encontrarUltimoMovimientoProducto(Long productoId) {

		return kardexDao.encontrarLastKardexOperationByProductoId(productoId);
	}

	
	

}
