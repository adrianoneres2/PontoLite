package com.octadata.pontolite.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
	public String RegistrarPonto() {
		   
		RegistroPonto rp = new RegistroPonto();			
		registroPontoService.salvar(rp);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("mensagem2", "Ponto registrado com suceso!");
		return "/mensagem2";
		
		//Optional<Usuario> usuario2 = usuarioRepository.findById(usuarioSessao.getCodigoUsuario());
		///List<RegistroPonto> pontos = registroPontoRepository.findAll();
		//System.out.println(pontos.isEmpty());
		
	}
}
