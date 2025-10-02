package com.octadata.pontolite.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;


@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
	@Autowired
	HttpSession session;
	
	@Override
	public boolean autenticado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
			return false;
		}
		return authentication.isAuthenticated();
	}

	
	@Override
	public void encerrarSessao() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (autenticado()) {
			session.removeAttribute("usuarioLogado");
			authentication.setAuthenticated(false);
		}
	}
	
	@Override
	public void registrarUsuarioSessao() {
		/*Verifica se já houve autenticação via spring, caso sim, ele registra o usuário na sessão. */
		if (getUsuarioAutenticado() == null) {
			Optional<Usuario> user = usuarioRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute("usuarioLogado", user.get());
		};
	}
	
	@Override
	public Usuario getUsuarioAutenticado() {  
		return (Usuario)session.getAttribute("usuarioLogado");
	}

}
