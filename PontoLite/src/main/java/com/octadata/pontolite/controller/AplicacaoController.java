package com.octadata.pontolite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octadata.pontolite.service.AutenticacaoService;

@Controller
@RequestMapping("pontolite")
public class AplicacaoController {
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@GetMapping
	public String principal() {
		autenticacaoService.registrarUsuarioSessao();
		return "/dashboard";
	}

	@GetMapping("acessarRegistroPonto")
	public String acessarRegistroPonto() {
		return "/ponto/registro-ponto";
	}
}
