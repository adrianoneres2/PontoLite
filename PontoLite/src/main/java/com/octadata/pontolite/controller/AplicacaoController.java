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

	///@PreAuthorize("hasRole('ROLE_ACESSAR_REGISTRO_PONTO')")
	@GetMapping("acessarRegistroPonto")
	public String acessarRegistroPonto() {
		return "/ponto/registro-ponto";
	}

	@GetMapping("access-denied")
	public String acessoImpedido() {
		System.out.println("Sem acesso!!!");
		return "access-denied";
	}
	
	@GetMapping("error")
	public String error() {
		System.out.println("Sem acesso!!!");
		return "access-denied";
	}
}
