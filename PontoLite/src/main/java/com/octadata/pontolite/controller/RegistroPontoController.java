package com.octadata.pontolite.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.octadata.pontolite.model.RegistroPonto;
import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.repository.RegistroPontoRepository;
import com.octadata.pontolite.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("ponto")
public class RegistroPontoController {
	
	@Autowired
	private RegistroPontoRepository registroPontoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private HttpSession session;
	
	
	@RequestMapping(value = "/mensagem", method = RequestMethod.POST)
	public String mensagem() {
		return "Mensagem";
	}
	
	@RequestMapping("registrar")
	public String RegistrarPonto() {
		
		ModelAndView mv = new ModelAndView(); 
		Usuario usuarioSessao = (Usuario)session.getAttribute("usuarioLogado");  
		
		//Optional<Usuario> usuario2 = usuarioRepository.findById(usuarioSessao.getCodigoUsuario());
		
		RegistroPonto rp = new RegistroPonto();
		rp.setDataRegistroPonto(new Date());
		//rp.setUsuario(usuario2.get());
		rp.setUsuario(usuarioSessao);
		rp.setSituacaoRegistroPonto(1);
		rp.setTipoRegistro(1);
				
		///List<RegistroPonto> pontos = registroPontoRepository.findAll();
		
		registroPontoRepository.save(rp);
		
		///System.out.println(pontos.isEmpty());

		mv.addObject("mensagem2", "Ponto registrado com suceso!");
		
		return "/mensagem2";
		
	}
}
