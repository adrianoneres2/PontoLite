package com.octadata.pontolite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octadata.pontolite.dto.LoginDTO;
import com.octadata.pontolite.service.AutenticacaoService;

@Controller
@RequestMapping("login")
public class LoginController {

	@Autowired
	private AutenticacaoService autenticacaoService;

	/*
	 * O nome desse método precisa ser login para o spring security
	 * entender que os campos password e username serão usados para a autenticação
	 */
	@GetMapping
	public void login(LoginDTO loginDTO) {
	}

	@GetMapping("logof")
	public String logof() {
		autenticacaoService.encerrarSessao();
		return "redirect:/login";
	}

	@GetMapping("accessDenied")
	public String accessDenied() {
		if (autenticacaoService.autenticado()) {
			return "access-denied";
		}
		return "redirect:/login";
	}

}