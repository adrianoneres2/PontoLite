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
	@Autowired
	private TipoRegistroService tipoRegistroService;

	@Override
	public RegistroPonto salvar(RegistroPonto registroPonto) {
		try {
			Usuario usuarioSessao = (Usuario) session.getAttribute("usuarioLogado");
			registroPonto.setDataRegistroPonto(LocalDateTime.now()); 
			registroPonto.setUsuario(usuarioSessao);
			registroPonto.setSituacaoRegistroPonto(1);
			
			RegistroPonto ultimoRegistroPonto = registroPontoRepository.findMaiorRegistroPorCodigoUsuario(usuarioSessao.getCodigoUsuario());
			
			if(ultimoRegistroPonto == null) {
				ultimoRegistroPonto = new RegistroPonto();
				/*Obter o primeiro tipo de registro*/
				registroPonto.setTipoRegistro(tipoRegistroService.porCodigoTipoRegistro(1L));
			}else {
				/*Se o dia atual não for o mesmo dia do último ponto, registra como o primeiro ponto do dia*/
				if(ultimoRegistroPonto.getDataRegistroPonto().getDayOfMonth() != LocalDateTime.now().getDayOfMonth()) {
					registroPonto.setTipoRegistro(tipoRegistroService.porCodigoTipoRegistro(1L));
				}else {
					/*Obtem o próximo tipo de registro*/ 
					registroPonto.setTipoRegistro((tipoRegistroService.porCodigoTipoRegistro(ultimoRegistroPonto.getTipoRegistro().getCodigoTipoRegistro()+1)));	
				}				
			}

			/*Se o último ponto for maior que 1 hora permite registrar novo ponto*/
			if(ultimoRegistroPonto.getCodigoRegistroPonto() == null || ultimoRegistroPonto.getDataRegistroPonto().plusHours(1).isBefore(LocalDateTime.now())) {
				registroPontoRepository.save(registroPonto);
				return registroPonto;
			}
			
			return new RegistroPonto();
			
		} catch (Exception e) {
			return new RegistroPonto();
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

	@Override
	public List<RegistroPonto> listarPeriodoPorUsuario(Usuario usuario, LocalDateTime dataHoraIncial, LocalDateTime dataHoraFinal) {
		 return registroPontoRepository.findByPeriodoPorUsuario(usuario.getCodigoUsuario(), dataHoraIncial, dataHoraFinal);
	}
	
}
