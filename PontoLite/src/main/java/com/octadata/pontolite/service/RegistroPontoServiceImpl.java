package com.octadata.pontolite.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.exception.NegocioException;
import com.octadata.pontolite.handler.NegocioExceptionHandler;
import com.octadata.pontolite.model.RegistroPonto;
import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.repository.RegistroPontoRepository;
import com.octadata.pontolite.util.EnumMessage;
import com.octadata.pontolite.util.EnumStatusRegistro;
import com.octadata.pontolite.util.EnumTipoRegistroPonto;

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
		registroPonto = validarInclusao(registroPonto);
		List<RegistroPonto> registrosPonto = listarPeriodoPorUsuario(registroPonto.getUsuario(),
				LocalDateTime.now().toLocalDate().atStartOfDay(),
				LocalDateTime.now().toLocalDate().atTime(LocalTime.MAX));
		registroPonto.setTempoRegistro(calcularTempoPorTipoRegistro(registroPonto, registrosPonto));
		registroPontoRepository.save(registroPonto);
		return registroPonto;
	}

	@Override
	public List<RegistroPonto> listarPorUsuario() {
		return registroPontoRepository.findByUsuario((Usuario) session.getAttribute("usuarioLogado")).stream()
				.filter(r -> r.getSituacaoRegistroPonto() == EnumStatusRegistro.ATIVO.getValor())
				.collect(Collectors.toList());
	}

	@Override
	public List<RegistroPonto> listarPorDataRegistroPonto() {
		LocalDateTime now = LocalDateTime.now();
		return registroPontoRepository.findByDataRegistroPonto(now).stream()
				.filter(r -> r.getSituacaoRegistroPonto() == EnumStatusRegistro.ATIVO.getValor())
				.collect(Collectors.toList());
	}

	@Override
	public List<RegistroPonto> listarPorDataRegistroPontoHoje() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime dataHoraFinal = LocalDateTime.parse(now.toString());
		LocalDateTime dataHoraIncial = dataHoraFinal.toLocalDate().atTime(LocalTime.MIN);
		return registroPontoRepository.findByDataRegistroPontoBetween(dataHoraIncial, dataHoraFinal).stream()
				.filter(r -> r.getSituacaoRegistroPonto() == EnumStatusRegistro.ATIVO.getValor())
				.collect(Collectors.toList());
	}

	@Override
	public List<RegistroPonto> listarPeriodoPorUsuario(Usuario usuario, LocalDateTime dataHoraIncial,
			LocalDateTime dataHoraFinal) {
		return registroPontoRepository.findByPeriodoPorUsuario(usuario.getCodigoUsuario(), dataHoraIncial,
				dataHoraFinal).stream()
				.filter(r -> r.getSituacaoRegistroPonto() == EnumStatusRegistro.ATIVO.getValor())
				.collect(Collectors.toList());
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

	@Override
	public Long calcularTempoRegistro(LocalDateTime dataRegistroPontoInicio,
			LocalDateTime dataRegistroPontoFim) {
		return ChronoUnit.SECONDS.between(dataRegistroPontoInicio, dataRegistroPontoFim);
	}

	@Override
	public Long calcularTempoTotalRegistro(List<RegistroPonto> registrosPonto) {
		Long tempoTotal = 1L;
		for (int i = 0; i < registrosPonto.size() - 1; i++) {
			tempoTotal += calcularTempoRegistro(registrosPonto.get(i).getDataRegistroPonto(),
					registrosPonto.get(i + 1).getDataRegistroPonto());
		}
		return tempoTotal;
	}

	@Override
	public Long calcularTempoPorTipoRegistro(RegistroPonto registroPonto, List<RegistroPonto> registrosPonto) {

		Long tempoTotal = 0L;

		for (int i = 0; i < registrosPonto.size(); i++) {
			if (registroPonto.getTipoRegistro().getCodigoTipoRegistro() == EnumTipoRegistroPonto.INTERVALO.getValor()
					&& i + 1 == EnumTipoRegistroPonto.ENTRADA.getValor()) {
				tempoTotal += calcularTempoRegistro(registrosPonto.get(i).getDataRegistroPonto(),
						registroPonto.getDataRegistroPonto());
			} else if (registroPonto.getTipoRegistro()
					.getCodigoTipoRegistro() == EnumTipoRegistroPonto.RETORNO_INTERVALO.getValor()
					&& i + 1 == EnumTipoRegistroPonto.INTERVALO.getValor()) {
				tempoTotal += calcularTempoRegistro(registrosPonto.get(i).getDataRegistroPonto(),
						registroPonto.getDataRegistroPonto());
			} else if (registroPonto.getTipoRegistro()
					.getCodigoTipoRegistro() == EnumTipoRegistroPonto.SAIDA.getValor()
					&& i + 1 == EnumTipoRegistroPonto.RETORNO_INTERVALO.getValor()) {
				tempoTotal += calcularTempoRegistro(registrosPonto.get(i).getDataRegistroPonto(),
						registroPonto.getDataRegistroPonto());
			}
		}
		return tempoTotal;
	}

}
