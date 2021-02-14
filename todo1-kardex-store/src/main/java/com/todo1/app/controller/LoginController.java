package com.todo1.app.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador para las acciones de login 
 * 
 * @author Gabriel Eguiguren
 *
 */
@Controller
public class LoginController {
	
	
	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			Model model, RedirectAttributes flash, 
			Principal principal) {	// este objeto se crea cuando ya se ha hecho login
		
		if(principal != null) {
			flash.addFlashAttribute("info","Ya ha iniciado sesión previamente");
			return "redirect:/producto/"; 
		}
		
		if(error != null) {
			model.addAttribute("error", "Nombre de usuario o contraseña incorrecta");
		}
		
		if(logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito");
		}

		
		
		return "login";
	}

}
