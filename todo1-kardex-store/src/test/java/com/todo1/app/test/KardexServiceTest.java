package com.todo1.app.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.todo1.app.model.entity.Kardex;
import com.todo1.app.model.entity.Producto;
import com.todo1.app.model.repository.IKardexRepository;
import com.todo1.app.model.service.IKardexService;
import com.todo1.app.test.enums.OperacionKardex;



@SpringBootTest
public class KardexServiceTest {
	
	@MockBean
	private IKardexRepository kardexRepository;
	
	@Autowired
	private IKardexService kardexService;
	
	@Test
	public void findOneTest() {
		
		Kardex obj = new Kardex();
		obj.setCantidadMovimiento(1);
		obj.setFechaRegistro(new Date());
		obj.setObservacion("Observacion");
		obj.setPrecioDeCosto(new BigDecimal(10));
		obj.setProducto(new Producto());
		obj.setSaldoCantidad(1);
		obj.setTipoOperacion(OperacionKardex.Entrada.name());
		obj.setValorTotalDeCosto(new BigDecimal(10));
		
		Logger log = LoggerFactory.getLogger(KardexServiceTest.class);
		log.info(obj.toString());
		
		//when(kardexRepository.save(obj)).thenReturn(obj);
		kardexService.save(obj);
		
		assertEquals(obj, kardexService.findOne(obj.getId()));
	}
	
	@Test
	public void encontrarUltimoMovimientoProductoTest() {
		
		Long productoId = new Long(1);
		Kardex kardexEnBase = kardexService.encontrarUltimoMovimientoProducto(productoId);
		
		assertEquals(kardexEnBase, kardexRepository.encontrarLastKardexOperationByProductoId(productoId));
		
	}
	
	@Test
	public void saveTest() {
		
		Kardex obj = new Kardex();
		obj.setCantidadMovimiento(1);
		obj.setFechaRegistro(new Date());
		obj.setObservacion("Observacion");
		obj.setPrecioDeCosto(new BigDecimal(10));
		obj.setProducto(new Producto());
		obj.setSaldoCantidad(1);
		obj.setTipoOperacion(OperacionKardex.Entrada.name());
		obj.setValorTotalDeCosto(new BigDecimal(10));
		
		kardexService.save(obj);
		Long idNuevo =  obj.getId();
		
		assertEquals(obj, kardexService.findOne(idNuevo));
	}
	
	
	@Test
	public void delete() {
		
		Kardex obj = kardexService.findOne(new Long(5));
		kardexService.delete(obj.getId());
		assertEquals(null, kardexService.findOne(new Long(5)));
		
	}
	
	
	

}
