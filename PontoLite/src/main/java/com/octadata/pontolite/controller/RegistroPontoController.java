package com.octadata.pontolite.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.octadata.pontolite.model.RegistroPonto;
import com.octadata.pontolite.service.RegistroPontoService;

@Controller
@RequestMapping("pontolite/ponto")
public class RegistroPontoController {
	
	@Autowired
	private RegistroPontoService registroPontoService;
	
	@RequestMapping(value = "/mensagem", method = RequestMethod.POST)
	public String mensagem() {
		return "Mensagem";
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTRAR_PONTO')")
	@RequestMapping("registrar")
	public String registrarPonto() {
		   
		RegistroPonto rp = new RegistroPonto();			
		registroPontoService.salvar(rp);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("mensagem2", "Ponto registrado com suceso!");
		return "/mensagem2";
	}
	
	@PreAuthorize("hasRole('ROLE_LISTAR_REGISTRO_PONTO')")
	@GetMapping("listar")
	public String listar(Model model) {
		//List<RegistroPonto> registros = registroPontoService.listarPorUsuario();
		List<RegistroPonto> registros = registroPontoService.listarPorDataRegistroPontoHoje();
		model.addAttribute("registros", registros);
		return "/ponto/listagemPonto";
	}
}
