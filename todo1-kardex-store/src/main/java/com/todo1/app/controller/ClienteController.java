package com.todo1.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.todo1.app.model.entity.Cliente;
import com.todo1.app.model.service.IClienteService;

/**
 * Controller para manejar las vistas y acciones sobre Cliente
 * 
 * @author Gabriel Eguiguren
 *
 */
@Controller
@SessionAttributes("cliente")
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private TextEncryptor encriptadorTexto;

	@Autowired
	private IClienteService clienteService;

	@GetMapping(value = { "/listar", "/", "" })
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);

		PageRender<Cliente> pageRender = new PageRender<>("/cliente/listar", clientes);
		model.addAttribute("page", pageRender);
		model.addAttribute("clientes", clientes);
		model.addAttribute("titulo", "Listado de Clientes");

		return "cliente/listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/form/{id}")
	public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {

		Cliente cliente = null;

		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "El Cliente: " + id.toString() + " no existe!");
				return "redirect:/cliente/listar";
			}

		} else {
			flash.addFlashAttribute("error", "El Id del Cliente no puede ser Cero!");
			return "redirect:/cliente/listar";
		}
		
		
		if (cliente.getUsername() != null && cliente.getTarjetaCreditoEncriptada() != null) {
			//decodifico los datos encriptados antes de enviar el objeto a la vista
			cliente.setUsername(encriptadorTexto.decrypt(cliente.getUsername()));
			cliente.setTarjetaCredito(encriptadorTexto.decrypt(cliente.getTarjetaCreditoEncriptada()));
		}
		

		model.addAttribute("titulo", "Editar Cliente");
		model.addAttribute("cliente", cliente);
		flash.addFlashAttribute("success", "Cliente editado con éxito!");
		return "cliente/form";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito!");
		}

		return "redirect:/cliente/listar";

	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/form")
	public String crear(Model model) {

		Cliente cliente = new Cliente();

		model.addAttribute("titulo", "Formulario de Cliente");
		model.addAttribute("cliente", cliente);

		return "cliente/form";
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "cliente/form";
		}

		// codificar los datos sensibles
		cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
		cliente.setUsername(encriptadorTexto.encrypt(cliente.getUsername()));
		cliente.setTarjetaCreditoEncriptada(encriptadorTexto.encrypt(cliente.getTarjetaCredito()));
		
		
		clienteService.save(cliente);

		String mensaje = (cliente.getId() == null) ? "Cliente creado con éxito!" : "Cliente editado con éxito!";

		status.setComplete(); // esto elimina el atributo "cliente" de la session
		flash.addFlashAttribute("success", mensaje);

		return "redirect:/cliente/listar";
	}

	@Secured("ROLE_USER")
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		// Cliente cliente = clienteService.findOne(id);
		// optimizando con la consulta fetch para que en una sola consulta traiga los
		// datos del cliente y sus facturas
		Cliente cliente = clienteService.fetchByIdWithFacturas(id);

		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/cliente/listar";
		}

		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Detalle cliente: " + cliente.getNombre());
		return "cliente/ver";
	}
}
