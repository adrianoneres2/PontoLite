package com.octadata.pontolite.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.exception.NegocioException;
import com.octadata.pontolite.handler.NegocioExceptionHandler;
import com.octadata.pontolite.model.RegistroPonto;
import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.repository.RegistroPontoRepository;
import com.octadata.pontolite.util.EnumMessage;

import jakarta.servlet.http.HttpSession;

@Service
public class RegistroPontoServiceImpl implements RegistroPontoService {

	@Autowired
	private RegistroPontoRepository registroPontoRepository;
	@Autowired
	private HttpSession session;
	@Autowired
	private TipoRegistroService tipoRegistroService;

	RegistroPontoServiceImpl(NegocioExceptionHandler negocioExceptionHandler) {
	}

	@Override
	public RegistroPonto salvar(RegistroPonto registroPonto) {
		registroPonto.setDataRegistroPonto(LocalDateTime.now());
		registroPonto.setSituacaoRegistroPonto(1);
		registroPontoRepository.save(validarInclusao(registroPonto));
		return registroPonto;
	}

	@Override
	public List<RegistroPonto> listarPorUsuario() {
		return registroPontoRepository.findByUsuario((Usuario) session.getAttribute("usuarioLogado"));
	}

	@Override
	public List<RegistroPonto> listarPorDataRegistroPonto() {
		/* Ajustar */
		LocalDateTime now = LocalDateTime.now();
		return registroPontoRepository.findByDataRegistroPonto(now);
	}

	@Override
	public List<RegistroPonto> listarPorDataRegistroPontoHoje() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime dataHoraFinal = LocalDateTime.parse(now.toString());
		LocalDateTime dataHoraIncial = dataHoraFinal.toLocalDate().atTime(LocalTime.MIN);
		return registroPontoRepository.findByDataRegistroPontoBetween(dataHoraIncial, dataHoraFinal);
	}

	@Override
	public List<RegistroPonto> listarPeriodoPorUsuario(Usuario usuario, LocalDateTime dataHoraIncial,
			LocalDateTime dataHoraFinal) {
		return registroPontoRepository.findByPeriodoPorUsuario(usuario.getCodigoUsuario(), dataHoraIncial,
				dataHoraFinal);
	}

	public RegistroPonto validarInclusao(RegistroPonto registroValidacao) {
		RegistroPonto ultimoRegistroPonto = registroPontoRepository
				.findMaiorRegistroPorCodigoUsuario(registroValidacao.getUsuario().getCodigoUsuario());

		if (ultimoRegistroPonto == null) {
			registroValidacao.setTipoRegistro(tipoRegistroService.porCodigoTipoRegistro(1L));
			return registroValidacao;
		}

		/*
		 * Se o dia atual não for o mesmo dia do último ponto, registra como o primeiro
		 * ponto do dia
		 */
		if (ultimoRegistroPonto == null
				|| ultimoRegistroPonto.getDataRegistroPonto().getDayOfMonth() != LocalDateTime.now().getDayOfMonth()) {
			registroValidacao.setTipoRegistro(tipoRegistroService.porCodigoTipoRegistro(1L));
		} else {
			/* Obtem o próximo tipo de registro */
			registroValidacao.setTipoRegistro((tipoRegistroService
					.porCodigoTipoRegistro(ultimoRegistroPonto.getTipoRegistro().getCodigoTipoRegistro() + 1)));
		}

		/* Se o último ponto for maior que 1 hora permite registrar novo ponto */
		if (!ultimoRegistroPonto.getDataRegistroPonto().plusHours(1).isBefore(LocalDateTime.now())) {
			throw new NegocioException(EnumMessage.ERROR.toString(), "Você já registrou ponto a poucos minutos atrás!",
					"Fora de hora");
		}
		return registroValidacao;
	}
}
