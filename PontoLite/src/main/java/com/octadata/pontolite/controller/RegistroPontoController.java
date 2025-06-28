package com.octadata.pontolite.controller;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.octadata.pontolite.model.RegistroPonto;
import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.service.RegistroPontoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("pontolite/ponto")
public class RegistroPontoController {
	
	@Autowired
	private RegistroPontoService registroPontoService;
	
	@Autowired
	private HttpSession session;
	
	@PostMapping("/mensagem")
	public String mensagem() {
		return "Mensagem";
	}
	
	///@PreAuthorize("hasRole('ROLE_REGISTRAR_PONTO')")
	@GetMapping("registrarPonto")
	public ModelAndView registrarPonto() {
		ModelAndView mv = new ModelAndView("/mensagem2");
		
		RegistroPonto registroPonto = new RegistroPonto();			
		if(registroPontoService.salvar(registroPonto).getCodigoRegistroPonto() == null) {
			mv.addObject("msg", "Você já registrou ponto a poucos minutos atrás!");
			return mv;
		};
		
		mv.addObject("msg", "Ponto registrado com suceso!");
		return mv;
	}
	
	//@PreAuthorize("hasRole('LISTAR_REGISTRO_PONTO')")
	@GetMapping("listar")
	public String listarPeriodoPorUsuario(Model model) { 
		LocalDateTime hoje = LocalDateTime.now();
		LocalDateTime dataHoraFinal = LocalDateTime.parse(hoje.toString());
		LocalDateTime dataHoraIncial = dataHoraFinal.toLocalDate().atTime(LocalTime.MIN);
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		List<RegistroPonto> registros = registroPontoService.listarPeriodoPorUsuario(usuarioLogado, dataHoraIncial, dataHoraFinal);
		model.addAttribute("registros", registros);
		return "/ponto/listagemPonto";
	}
}
