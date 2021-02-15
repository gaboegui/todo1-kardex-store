package com.todo1.app.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.todo1.app.controller.FacturaController;
import com.todo1.app.model.entity.Kardex;
import com.todo1.app.model.entity.Producto;
import com.todo1.app.model.repository.IKardexRepository;
import com.todo1.app.model.service.IKardexService;
import com.todo1.app.test.enums.OperacionKardex;



@SpringBootTest
public class KardexServiceTest {
	
	private final Logger log = LoggerFactory.getLogger(KardexServiceTest.class);
	
	@MockBean
	private IKardexRepository kardexRepository;
	
	@Autowired
	private IKardexService kardexService;
	
	@Test
	public void findOne(Long id) {
		
		Producto producto = new Producto("Marvel");
		
		Kardex obj = new Kardex();
		obj.setCantidadMovimiento(1);
		obj.setFechaRegistro(new Date());
		obj.setObservacion("Observacion");
		obj.setPrecioDeCosto(new BigDecimal(10));
		obj.setProducto(producto);
		obj.setSaldoCantidad(1);
		obj.setTipoOperacion(OperacionKardex.Entrada.name());
		obj.setValorTotalDeCosto(new BigDecimal(10));
		
		log.info(obj.toString());
		
		when(kardexRepository.save(obj)).thenReturn(obj);
		
		assertEquals(obj, kardexService.findOne(obj.getId()));
	}
	
	/**
	 * 
	
	public void save(Kardex kardex);
	
	public void delete(Long id);
	
	public Kardex encontrarUltimoMovimientoProducto(Long productoId);
	 * 
	 */
	

}
