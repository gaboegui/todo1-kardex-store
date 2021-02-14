package com.todo1.app.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todo1.app.controller.paginator.PageRender;
import com.todo1.app.model.entity.Producto;
import com.todo1.app.model.service.IProductoService;

/**
 * Controller para manejar las vistas y acciones sobre Producto
 *  
 * 
 * @author Gabriel Eguiguren
 *
 */

@SessionAttributes("producto")
@Controller
@RequestMapping(value = {"/producto", "/", ""}) 
public class ProductoController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IProductoService productoService;

	
	/**
	 * Obtengo el listado de productos, utilizando paginación 
	 * 
	 * @param page  pagina actual del total de la lista
	 * @param model objeto con el Modelo de MVC
	 * @return la vista a donde rediccionara el Controller
	 */
	@GetMapping(value = {"/listar", "/", ""})
	public String listar(@RequestParam(name ="page", defaultValue = "0") int page,
			Model model) {
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Producto> productos = productoService.findAll(pageRequest);
		
		PageRender<Producto> pageRender = new PageRender<>("/producto/listar", productos);
		model.addAttribute("page", pageRender);
		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Listado de Productos");
		
		return "producto/listar";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/form/{id}")
	public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {

		Producto producto = null;

		if (id > 0) {
			producto = productoService.findOne(id);
			if(producto == null) {
				flash.addFlashAttribute("error","El Producto: "+id.toString()+" no existe!");
				return "redirect:/producto/listar";
			}
			
		} else {
			flash.addFlashAttribute("error","El Id del Producto no puede ser Cero!");
			return "redirect:/producto/listar";
		}

		model.addAttribute("titulo", "Editar Producto");
		model.addAttribute("producto", producto);
		return "/producto/form";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			try {
				productoService.delete(id);
			} catch (Exception e) {
				flash.addFlashAttribute("error","El producto No se puede eliminar ya que existen Facturas relacionadas!");
				log.error(e.getMessage());
				return "redirect:/producto/listar";
			}
			
			
			flash.addFlashAttribute("success","Producto eliminado con éxito!");
		}

		return "redirect:/producto/listar";

	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/form")
	public String crear(Model model) {

		Producto producto = new Producto();

		model.addAttribute("titulo", "Formulario de Producto");
		model.addAttribute("producto", producto);

		return "/producto/form";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/form")
	public String guardar(@Valid Producto producto, BindingResult result, 
			Model model, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Producto");
			return "/producto/form";
		}

		String mensaje = (producto.getId()!= null) ? "Producto editado con éxito!" : "Producto creado con éxito!"; 
		
		productoService.save(producto);

		status.setComplete(); // esto elimina el atributo "producto" de la session
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/producto/listar";
	}
	
	@Secured("ROLE_USER")
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		 Producto producto = productoService.findOne(id);
		
		
		if (producto == null) {
			flash.addFlashAttribute("error", "El producto no existe en la base de datos");
			return "redirect:/producto/listar";
		}

		model.addAttribute("producto", producto);
		model.addAttribute("titulo", "Detalle del producto");
		return "producto/ver";
	}
}
