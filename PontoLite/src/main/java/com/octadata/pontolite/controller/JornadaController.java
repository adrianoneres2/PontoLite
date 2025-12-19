package com.octadata.pontolite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octadata.pontolite.model.Jornada;

@Controller
@RequestMapping("pontolite/jornada")
public class JornadaController {
	
	@GetMapping("/formulario")
	public String acessarFormularioJornada(Model model) {
		model.addAttribute("jornada", new Jornada());
		return "/jornada/cadastro-jornada";
	}

}
