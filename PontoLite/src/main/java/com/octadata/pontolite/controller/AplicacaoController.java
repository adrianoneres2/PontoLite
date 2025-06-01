package com.octadata.pontolite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octadata.pontolite.dto.LoginDto;
import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.service.AutenticacaoService;

@Controller
@RequestMapping("pontolite")
public class AplicacaoController {
	
	@Autowired
	private AutenticacaoService autenticacaoService;

	@GetMapping
	public String login(LoginDto loginDto, ModelMap map) {
		
		/* Método observa se a autenticação foi feita com sucesso e registra o objeto "Usuário" na sessão HttpSession */
		if (autenticacaoService.registrarUsuarioSessao()) {
			return "/dashboard";
		}
		
		if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
			map.addAttribute("msg", "Usuário ou senha inválidos!");
		}
		
		return "/login";
	}
	
	@GetMapping("logof")
	public String logof() {
		autenticacaoService.encerrarSessao();
		return "redirect:/pontolite";
	}

	@PreAuthorize("hasRole('ROLE_ACESSAR_REGISTRO_PONTO')")
	@GetMapping("registrar")
	public String registrar() {
		return "/ponto/registroPonto";
	}
	
	@PreAuthorize("hasRole('ROLE_APROVAR_AJUSTE_PONTO')")
	@GetMapping("teste")
	public String teste() {
		System.out.println("teste de role!!!");
		return "/mensagem2";
	}

	@GetMapping("access-denied")
	public String acessoImpedido() {
		System.out.println("Sem acesso!!!");
		return "/access-denied";
	}
	
	@GetMapping("error")
	public String error() {
		System.out.println("Sem acesso!!!");
		return "/access-denied";
	}
	
/*
	@PostMapping("acessar")
	public ModelAndView acessar(@Valid LoginDto loginDto, BindingResult result, HttpSession session) {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", new Usuario());
		
		if (result.hasErrors()) {
			mv.setViewName("login");
		}
//
//		Usuario usuario = loginDto.toUsuario();
//		usuario = autenticacaoService.logar(usuario);
//
//		if (usuario != null && usuario.getCodigoUsuario() != null) {
//			session.setAttribute("usuarioLogado", usuario);
//			mv.setViewName("dashboard");
//		} else {
//			mv.addObject("mensagem", "Não foi possível efetuar o login!");
//			mv.setViewName("login");
//		}

		//return "redirect:/pontolite";
		mv.addObject("mensagem", "Não foi possível efetuar o login!");
		mv.setViewName("dashboard");
		return mv;
	}*/
	
}
