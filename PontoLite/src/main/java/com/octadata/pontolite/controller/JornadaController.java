package com.octadata.pontolite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octadata.pontolite.dto.JornadaDTO;
import com.octadata.pontolite.model.Jornada;
import com.octadata.pontolite.service.AutenticacaoService;
import com.octadata.pontolite.service.ClienteService;
import com.octadata.pontolite.service.JornadaService;
import com.octadata.pontolite.util.EnumMessage;
import com.octadata.pontolite.util.ModelMessage;

@Controller
@RequestMapping("pontolite/jornada")
public class JornadaController {

	@Autowired
	JornadaService jornadaService;

	@Autowired
	ClienteService clienteService;

	@Autowired
	AutenticacaoService autenticacaoService;

	@GetMapping("/formulario")
	public String acessarFormularioJornada(Model model) {

		JornadaDTO jornadaDTO = new JornadaDTO(null, null, null, null, null, null);
		model.addAttribute("jornadaDTO", jornadaDTO);
		model.addAttribute("clientes", clienteService.listarTodos());
		return "/jornada/cadastro-jornada";
	}

	@PostMapping("/formulario")
	public String salvarJornada(JornadaDTO jornadaDTO, Model model) {
		Jornada jornada = new Jornada();
		jornada = jornadaDTO.toJornada(jornadaDTO);
		jornada.setUsuarioCadastro(autenticacaoService.getUsuarioAutenticado());
		jornadaService.salvar(jornada);
		ModelMessage.setAttribute(model, EnumMessage.SUCCESS.toString(), "Cadastrado com sucesso!!");
		return "/jornada/cadastro-jornada";
	}
}
