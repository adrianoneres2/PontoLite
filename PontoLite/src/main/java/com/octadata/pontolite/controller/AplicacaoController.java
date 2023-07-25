package com.octadata.pontolite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.octadata.pontolite.dto.LoginDto;
import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.service.AutenticacaoService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("pontolite")
public class AplicacaoController {
	
	@Autowired
	private AutenticacaoService autenticacaoService;

	@GetMapping
	@RequestMapping()
	public String login() {
		/* Método observa se a autenticação foi feita com sucesso e registra o objeto "Usuário" na sessão HttpSession */
		if (autenticacaoService.registrarUsuarioSessao()) {
			
			ModelAndView mv = new ModelAndView();
			mv.addObject("page", "base");
			mv.addObject("fragmento", "botaoRegistrarPonto");
			mv.addObject("mensagem", "teste");
			
			return "/dashboard";
		}
		return "/login";
	}
	
	@RequestMapping("logof")
	public String logof(Model model, HttpSession session) {
		autenticacaoService.encerrarSessao();
		return "redirect:/pontolite";
	}

	@PreAuthorize("hasRole('ROLE_ACESSAR_REGISTRO_PONTO')")
	@GetMapping("registrar")
	public String registrar() {
		//System.out.println("teste de registrar ponto!!!");
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
		return "/access-denied";
	}
	
	@PostMapping("acessar")
	public ModelAndView acessar(@Valid LoginDto loginDto, BindingResult result, HttpSession session) {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", new Usuario());
		
		if (result.hasErrors()) {
			mv.setViewName("login");
		}
/*
		Usuario usuario = loginDto.toUsuario();
		usuario = autenticacaoService.logar(usuario);

		if (usuario != null && usuario.getCodigoUsuario() != null) {
			session.setAttribute("usuarioLogado", usuario);
			mv.setViewName("dashboard");
		} else {
			mv.addObject("mensagem", "Não foi possível efetuar o login!");
			mv.setViewName("login");
		}
		*/
		//return "redirect:/pontolite";
		mv.addObject("mensagem", "Não foi possível efetuar o login!");
		mv.setViewName("dashboard");
		return mv;
	}
	
}
