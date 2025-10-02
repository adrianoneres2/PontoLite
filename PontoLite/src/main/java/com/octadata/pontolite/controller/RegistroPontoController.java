package com.octadata.pontolite.controller;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octadata.pontolite.base.EnumMessage;
import com.octadata.pontolite.base.ModelMessage;
import com.octadata.pontolite.exception.NegocioException;
import com.octadata.pontolite.model.RegistroPonto;
import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.service.AutenticacaoService;
import com.octadata.pontolite.service.RegistroPontoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("pontolite/ponto")
public class RegistroPontoController {

    private final AutenticacaoService autenticacaoService;
	
	@Autowired
	private RegistroPontoService registroPontoService;
	
	@Autowired
	private HttpSession session;
	
	//@Autowired
	//private RegistroPonto registroPonto;

    RegistroPontoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }
	
	@GetMapping("registrarPonto")
	public String registrarPonto(Model model) {
		RegistroPonto registroPonto = new RegistroPonto();
		registroPonto.setUsuario(autenticacaoService.getUsuarioAutenticado());
		try {
			registroPontoService.salvar(registroPonto);
        	ModelMessage.setAttribute(model, EnumMessage.SUCCESS.toString(), "Ponto registrado com sucesso!");
		} catch (NegocioException e) {
			ModelMessage.setAttribute(model, EnumMessage.ERROR.toString(), e.getMessage());
		}
		return "/ponto/registro-ponto";
	}
	
	@GetMapping("listar")
	public String listarPeriodoPorUsuario(Model model) { 
		LocalDateTime hoje = LocalDateTime.now();
		LocalDateTime dataHoraFinal = LocalDateTime.parse(hoje.toString());
		LocalDateTime dataHoraIncial = dataHoraFinal.toLocalDate().atTime(LocalTime.MIN);
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		List<RegistroPonto> registros = registroPontoService.listarPeriodoPorUsuario(usuarioLogado, dataHoraIncial, dataHoraFinal);
		model.addAttribute("registros", registros);
		return "/ponto/listagem-ponto";
	}
}
