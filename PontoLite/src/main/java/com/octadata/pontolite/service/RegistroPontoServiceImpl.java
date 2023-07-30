package com.octadata.pontolite.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
			registroPonto.setDataRegistroPonto(LocalDateTime.now()); 
			registroPonto.setUsuario(usuarioSessao);
			registroPonto.setSituacaoRegistroPonto(1);
			registroPonto.setTipoRegistro(1);
			registroPontoRepository.save(registroPonto);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<RegistroPonto> listarPorUsuario() {
		 return registroPontoRepository.findByUsuario((Usuario) session.getAttribute("usuarioLogado"));
	}
	@Override
	public List<RegistroPonto> listarPorDataRegistroPonto() {
		 /*Ajustar*/ 
		 LocalDateTime now = LocalDateTime.now();
		 return registroPontoRepository.findByDataRegistroPonto(now);
	}
	
	@Override
	public List<RegistroPonto> listarPorDataRegistroPontoHoje() {
		 LocalDateTime now = LocalDateTime.now();
		 LocalDateTime dataHoraFinal = LocalDateTime.parse(now.toString());
		 LocalDateTime dataHoraIncial = dataHoraFinal.toLocalDate().atTime(LocalTime.MIN);
		 
		 //return registroPontoRepository.findByDataRegistroPontoBetween(dataHoraIncial, dataHoraFinal);
		 return registroPontoRepository.findByDataRegistroPontoBetween(dataHoraIncial, dataHoraFinal);
	}
}
