package com.octadata.pontolite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octadata.pontolite.dto.JornadaDTO;
import com.octadata.pontolite.service.JornadaService;

@Controller
@RequestMapping("pontolite/jornada")
public class JornadaController {

	@Autowired
	JornadaService jornadaService;

	@GetMapping("/formulario")
	public String acessarFormularioJornada(Model model) {

		JornadaDTO jornadaDTO = new JornadaDTO(null, null, null, null, null, null);
		model.addAttribute("jornadaDTO", jornadaDTO);
		// model.addAttribute("jornadaDataHora", new JornadaDataHora());
		return "/jornada/cadastro-jornada";
	}

	@PostMapping("/formulario")
	public String salvarJornada(JornadaDTO jornadaDTO) {
		/* jornadaService.salvarJornada(jornadaDTO); */
		return "redirect:/pontolite/jornada/formulario";
	}
}
