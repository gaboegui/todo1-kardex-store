package com.todo1.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todo1.app.model.entity.Cliente;
import com.todo1.app.model.entity.Factura;
import com.todo1.app.model.entity.ItemFactura;
import com.todo1.app.model.entity.Producto;
import com.todo1.app.model.service.IClienteService;


/**
 * Clase tipo control para la administracion de las Facturas/Orden de Compra
 * que se creara en el momento que el Cliente haga el Checkout del carro 
 * 
 * @author Gabriel Eguiguren
 *
 */

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	private final Logger log = LoggerFactory.getLogger(FacturaController.class);

	@Autowired
	private IClienteService clienteService;

	@Secured({"ROLE_USER"})
	@GetMapping("/ver/{id}")
	public String verDetalleFactura(@PathVariable Long id, Model model,
			RedirectAttributes flash) {
		
		
		// de esta manera carga todos objetos relacionado en una sola consulta en vez de 7
		Factura factura = clienteService.fetchByIdWithClienteWithItemFacturaWithProducto(id);
		//Factura factura = clienteService.findFacturaById(id);
		
		if (factura == null) {
			flash.addFlashAttribute("error", "La factura no existe en la BD");
			return "redirect:/listar";
		}
		
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Factura");
		
		return "factura/ver-detalle";
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/form/{clienteId}")
	public String crearFactura(@PathVariable Long clienteId, Model model, RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(clienteId);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la BD");
			return "redirect:/listar";
		}

		Factura factura = new Factura();
		factura.setCliente(cliente);

		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Crear Factura");

		return "factura/form";
	}

	// @ResponseBody sirve para inidicar que no retorna una vista o modelo,
	// y almacena la respuesta en el Response
	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		return clienteService.findByNombre(term);
	}

	@Secured({"ROLE_USER"})
	// Los vectores: item_id[] y cantidad[] estan definidos en plantilla-items.html
	@PostMapping("/form")
	public String guardarFactura(@Valid Factura factura, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, RedirectAttributes flash,
			SessionStatus status) {

		//validacion de campos
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Crear Factura");
			return "factura/form"; 
		}
		
		
		if(itemId == null || itemId.length == 0 ) {
			model.addAttribute("titulo", "Crear Factura");
			model.addAttribute("error", "La factura debe contener al menos un Producto");
			return "factura/form";
		}
		
		for (int i = 0; i < itemId.length; i++) {

			Producto producto = clienteService.findProductoById(itemId[i]);
			ItemFactura linea = new ItemFactura(cantidad[i], producto);
			factura.addItemFactura(linea);

			log.debug("ProductoId[i]: " + itemId[i] + " cantidad[i]: " + cantidad[i]);
		}

		clienteService.saveFactura(factura);
		status.setComplete();
		flash.addFlashAttribute("success", "Factura fue almacenada correctamente");

		return "redirect:/cliente/ver/" + factura.getCliente().getId();
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/eliminar/{id}")
	public String eliminarFactura(@PathVariable Long id,
			RedirectAttributes flash) {
		
		Factura factura = clienteService.findFacturaById(id);
		
		if (factura != null) {
			clienteService.deleteFactura(id);
			flash.addFlashAttribute("success", "La Factura fue eliminada");
			return "redirect:/cliente/ver/"+ factura.getCliente().getId();
		} else {
			flash.addFlashAttribute("error", "La factura no existe en la BD, no se pudo eliminar");
			return "redirect:/cliente/listar";
		}
	}

}
