package com.todo1.app.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todo1.app.controller.paginator.PageRender;
import com.todo1.app.model.entity.Kardex;
import com.todo1.app.model.entity.Producto;
import com.todo1.app.model.service.IKardexService;
import com.todo1.app.model.service.IProductoService;

/**
 * Controller para manejar las vistas y acciones sobre Kardex
 * por cuestiones de simplicidad del modelo se maneja una sola tabla de Kardex
 * 
 * 
 * @author Gabriel Eguiguren
 *
 */

@SessionAttributes("kardex")
@Controller
@RequestMapping(value = "/kardex")
public class KardexController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final static String CONST_ENTRADA = "Entrada";
	private final static String CONST_SALIDA = "Salida";

	@Autowired
	private IKardexService kardexService;
	
	@Autowired
	private IProductoService productoService;


	/**
	 * Obtengo el listado de los registros del kardex, utilizando paginación
	 * 
	 * @param page  pagina actual del total de la lista
	 * @param model objeto con el Modelo de MVC
	 * @return la vista a donde rediccionara el Controller
	 */
	@GetMapping(value = { "/listar", "/", "" })
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 20);
		
		//TODO actualmente trae todos los registros, se puede mejorar
		Page<Kardex> productosKardex = kardexService.findAll(pageRequest);

		PageRender<Kardex> pageRender = new PageRender<>("/kardex/listar", productosKardex);
		model.addAttribute("page", pageRender);
		model.addAttribute("productosKardex", productosKardex);
		model.addAttribute("titulo", "Listado de Productos del Kardex");

		return "kardex/listar";
	}

	
	/**
	 * Guarda los movimientos del kardex actual, en un nuevo registro y actualiza 
	 * el stock tanto en Kardex como en Producto 
	 * 
	 * @param kardex
	 * @param result
	 * @param model
	 * @param flash
	 * @param status
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@PostMapping("/form")
	public String guardar(@Valid Kardex kardex, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		Integer movimiento = new Integer(0);

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Kardex");
			return "/kardex/form";
		}

		Kardex nuevoMovimientoKardex = new Kardex();
		nuevoMovimientoKardex = kardex;
		nuevoMovimientoKardex.setId(null);

		// verifico que en la operacion de salida no se saque mas del el stock
		if (kardex.getTipoOperacion().equals(CONST_SALIDA)) {
			movimiento = kardex.getSaldoCantidad() - kardex.getCantidadMovimiento();
			if (movimiento < 0) {
				model.addAttribute("titulo", "Error No puede registrar una salida mayor al Stock Actual");
				flash.addFlashAttribute("error", "No puede registrar una salida mayor al Stock Actual");
				return "/kardex/form";
			}
		// entrada o inventario inicial
		} else {
			movimiento = kardex.getSaldoCantidad() + kardex.getCantidadMovimiento();
		}
		nuevoMovimientoKardex.setSaldoCantidad(movimiento);
		
		String mensaje = (kardex.getId() != null) ? "Kardex editado con éxito!"
				: "Movimiento de Kardex creado con éxito!";

		//almaceno el nuevo movimiento de Kardex
		kardexService.save(nuevoMovimientoKardex);
		
		// actualizo el saldo del producto para tenerlo disponible en el Carro de Compras
		Producto productoActualizado  = nuevoMovimientoKardex.getProducto();
		productoActualizado.setCantidadStock(nuevoMovimientoKardex.getSaldoCantidad());
		productoService.save(productoActualizado);
		

		status.setComplete(); // esto elimina el atributo "kardex" de la session
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/kardex/listar";
	}

	
	@Secured("ROLE_ADMIN")
	@GetMapping("/form/{productoId}")
	public String editar(@PathVariable Long productoId, Model model, RedirectAttributes flash) {

		Kardex kardex = null;

		if (productoId > 0) {
			kardex = kardexService.encontrarUltimoMovimientoProducto(productoId);
			if (kardex == null) {
				flash.addFlashAttribute("error", "El Kardex: " + productoId.toString() + " no existe!");
				return "redirect:/kardex/listar";
			}

		} else {
			flash.addFlashAttribute("error", "El Id del Kardex no puede ser Cero!");
			return "redirect:/kardex/listar";
		}

		model.addAttribute("titulo", "Registrar Movimiento Producto:\n" + kardex.getProducto().getNombre());
		model.addAttribute("kardex", kardex);
		return "/kardex/form";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/form")
	public String crear(Model model) {

		Kardex kardex = new Kardex();

		model.addAttribute("titulo", "Formulario de Kardex");
		model.addAttribute("kardex", kardex);

		return "/kardex/form";
	}

	
	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			try {
				kardexService.delete(id);
			} catch (Exception e) {
				flash.addFlashAttribute("error",
						"El kardex No se puede eliminar ya que existen Registros relacionados!");
				log.error(e.getMessage());
				return "redirect:/kardex/listar";
			}

			flash.addFlashAttribute("success", "Kardex eliminado con éxito!");
		}

		return "redirect:/kardex/listar";

	}

	/**
	 * Atributo para llenar los radio button del formulario
	 * 
	 * @return
	 */
	@ModelAttribute("operaciones")
	public List<String> operaciones() {
		return Arrays.asList(CONST_ENTRADA, CONST_SALIDA);
	}

}
