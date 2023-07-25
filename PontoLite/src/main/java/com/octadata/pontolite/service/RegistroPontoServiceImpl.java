package com.octadata.pontolite.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.RegistroPonto;
import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.repository.RegistroPontoRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class RegistroPontoServiceImpl implements RegistroPontoService {

	@Autowired
	private RegistroPontoRepository registroPontoRepository;
	@Autowired
	private HttpSession session;

	@Override
	public void salvar(RegistroPonto registroPonto) {
		try {
			Usuario usuarioSessao = (Usuario) session.getAttribute("usuarioLogado");
			registroPonto.setDataRegistroPonto(new Date());
			registroPonto.setUsuario(usuarioSessao);
			registroPonto.setSituacaoRegistroPonto(1);
			registroPonto.setTipoRegistro(1);
			registroPontoRepository.save(registroPonto);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
