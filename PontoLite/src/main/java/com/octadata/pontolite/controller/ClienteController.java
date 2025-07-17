package com.octadata.pontolite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("pontolite/cliente")
public class ClienteController {
	
	
	@GetMapping("cadastrar")
	public String cadastroCliente() {
		return "/cliente/cadastro-cliente";
	}

}
