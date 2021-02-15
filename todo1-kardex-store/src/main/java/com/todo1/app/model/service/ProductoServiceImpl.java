package com.todo1.app.model.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo1.app.OperacionKardex;
import com.todo1.app.model.entity.Factura;
import com.todo1.app.model.entity.ItemFactura;
import com.todo1.app.model.entity.Kardex;
import com.todo1.app.model.entity.Producto;
import com.todo1.app.model.repository.IKardexRepository;
import com.todo1.app.model.repository.IProductoRepository;

/**
 * Servicio con metodo de manipulación de Productos
 * 
 * @author Gabriel Eguiguren
 *
 */
@Service
public class ProductoServiceImpl implements IProductoService {


	private final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);
	
	@Autowired
	private IProductoRepository productoDao;
	
	@Autowired
	private IKardexRepository kardexDao;
	

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

		
		//TODO desplegar solo los que tengan stock mayor a cero
		return productoDao.findAll(pageable);
	}

	/**
	 * Función para actualizar el stock del producto y realizar el registro en el kardex
	 */
	@Override
	@Transactional
	public void actualizarKardex(Factura factura) {
		
		try {
			// actualizo stock de Producto y Kardex
			for ( ItemFactura itemFactura : factura.getItems() ) {
				
				 Producto producto = itemFactura.getProducto();
				 producto.setCantidadStock(itemFactura.getCantidad());
				 productoDao.save(producto);
				 
				 Kardex nuevoRegistro = new Kardex();
				 
				 nuevoRegistro.setTipoOperacion(OperacionKardex.Salida.name());
				 nuevoRegistro.setProducto(producto);
				 nuevoRegistro.setFactura(factura);
				 
				 // Trae el ultimo precio de Costo y stock del Kardex METODO DE INVENTARIO LAST PRICE
				 Kardex lastRegistro = kardexDao.encontrarLastKardexOperationByProductoId(producto.getId());
				 
				 nuevoRegistro.setPrecioDeCosto(lastRegistro.getPrecioDeCosto());
				 nuevoRegistro.setCantidadMovimiento(itemFactura.getCantidad());
				 log.info(producto.getCantidadStock().toString());
				 log.info(itemFactura.getCantidad().toString());
				 
				 nuevoRegistro.setSaldoCantidad(lastRegistro.getSaldoCantidad()-itemFactura.getCantidad());
				 
				 kardexDao.save(nuevoRegistro);
			}
		} catch (Exception e) {
			log.error("Error al registrar la Factura contra el Kardex", e);
		}
		
		
	}

	
	

}
