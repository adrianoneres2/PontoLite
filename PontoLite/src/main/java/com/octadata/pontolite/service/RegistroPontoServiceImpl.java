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
		registroPonto.setSituacaoRegistroPonto(EnumStatusRegistro.ATIVO.getValor());
		registroPonto = validarInclusao(registroPonto);
		registroPonto.setTempoRegistro(calcularTempoPorTipoRegistro(registroPonto, buscarListaDePontos(registroPonto)));
		registroPontoRepository.save(registroPonto);
		return registroPonto;
	}

	protected List<RegistroPonto> buscarListaDePontos(RegistroPonto registroPonto) {

		LocalDateTime dataHoraIncial = registroPonto.getDataRegistroPonto().toLocalDate().atStartOfDay();
		LocalDateTime dataHoraFinal = registroPonto.getDataRegistroPonto().toLocalDate().atTime(LocalTime.MAX);

		if (dataHoraIncial == null && dataHoraFinal == null) {
			dataHoraIncial = LocalDateTime.now().toLocalDate().atStartOfDay();
			dataHoraFinal = LocalDateTime.now().toLocalDate().atTime(LocalTime.MAX);
		}

		List<RegistroPonto> registrosPonto = listarPeriodoPorUsuario(registroPonto.getUsuario(), dataHoraIncial,
				dataHoraFinal);

		return registrosPonto;
	}

	@Override
	public RegistroPonto solicitacaoAlteracao(Long codigoRegistroPonto,
			java.time.LocalDateTime novaDataHora, String observacao) {

		RegistroPonto registroPonto = porId(codigoRegistroPonto);
		RegistroPonto registroPontoSolicitado = new RegistroPonto();

		registroPontoSolicitado.setUsuario(registroPonto.getUsuario());
		registroPontoSolicitado.setDataRegistroPonto(novaDataHora);
		registroPontoSolicitado.setTipoRegistro(registroPonto.getTipoRegistro());
		registroPontoSolicitado.setRegistroPontoAjustado(registroPonto);
		registroPontoSolicitado.setObservacao(observacao);
		registroPontoSolicitado.setSituacaoRegistroPonto(EnumStatusRegistro.AGUARDANDO_APROVACAO.getValor());
		registroPontoSolicitado
				.setTempoRegistro(calcularTempoPorTipoRegistro(registroPonto, buscarListaDePontos(registroPonto)));
		registroPontoRepository.save(registroPontoSolicitado);
		return registroPontoSolicitado;
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
			registroValidacao.setTipoRegistro(
					tipoRegistroService.porCodigoTipoRegistro((long) EnumStatusRegistro.ATIVO.getValor()));
			return registroValidacao;
		}

		/*
		 * Se o dia atual não for o mesmo dia do último ponto, registra como o primeiro
		 * ponto do dia
		 */
		if (ultimoRegistroPonto == null
				|| ultimoRegistroPonto.getDataRegistroPonto().getDayOfMonth() != LocalDateTime.now().getDayOfMonth()) {
			registroValidacao.setTipoRegistro(
					tipoRegistroService.porCodigoTipoRegistro((long) EnumStatusRegistro.ATIVO.getValor()));
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
		Long tempoTotal = (long) EnumStatusRegistro.ATIVO.getValor();
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

	@Override
	public RegistroPonto porId(Long codigoRegistroPonto) {
		return registroPontoRepository.findById(codigoRegistroPonto)
				.orElseThrow(() -> new NegocioException(EnumMessage.ERROR.toString(),
						"Registro de ponto não encontrado!", "Não encontrado"));
	}

	@Override
	public RegistroPonto alterarHorario(Long codigoRegistroPonto, java.time.LocalDateTime novaDataHora) {
		RegistroPonto ponto = porId(codigoRegistroPonto);
		ponto.setDataRegistroPonto(novaDataHora);
		return registroPontoRepository.save(ponto);
	}

	@Override
	public java.util.List<com.octadata.pontolite.dto.RelatorioPontoDiaDTO> montarRelatorioMensal(Usuario usuario,
			java.time.LocalDateTime dataHoraInicial, java.time.LocalDateTime dataHoraFinal) {

		java.util.List<RegistroPonto> registros = listarPeriodoPorUsuario(usuario, dataHoraInicial, dataHoraFinal);

		java.util.Map<java.time.LocalDate, java.util.List<RegistroPonto>> porDia = registros.stream()
				.filter(r -> r.getDataRegistroPonto() != null)
				.collect(java.util.stream.Collectors.groupingBy(r -> r.getDataRegistroPonto().toLocalDate()));

		java.util.List<com.octadata.pontolite.dto.RelatorioPontoDiaDTO> relatorio = new java.util.ArrayList<>();

		java.time.LocalDate inicio = dataHoraInicial.toLocalDate();
		java.time.LocalDate fim = dataHoraFinal.toLocalDate();

		for (java.time.LocalDate data = inicio; !data.isAfter(fim); data = data.plusDays(1)) {
			com.octadata.pontolite.dto.RelatorioPontoDiaDTO dto = new com.octadata.pontolite.dto.RelatorioPontoDiaDTO(
					data);
			java.util.List<RegistroPonto> pontosDoDia = porDia.get(data);

			if (pontosDoDia != null && !pontosDoDia.isEmpty()) {
				pontosDoDia.sort(java.util.Comparator.comparing(RegistroPonto::getDataRegistroPonto));

				for (RegistroPonto r : pontosDoDia) {
					if (r.getTipoRegistro() == null)
						continue;
					long codTipo = r.getTipoRegistro().getCodigoTipoRegistro();
					String nomeTipo = r.getTipoRegistro().getNomeTipoRegistro() != null
							? r.getTipoRegistro().getNomeTipoRegistro().toUpperCase()
							: "";

					if (nomeTipo.contains("EXTRA") || nomeTipo.contains("HE")) {
						if (nomeTipo.contains("ENTRADA") || codTipo == 5) {
							dto.setEntradaHoraExtra(r);
						} else {
							dto.setSaidaHoraExtra(r);
						}
					} else if (codTipo == EnumTipoRegistroPonto.ENTRADA.getValor() || nomeTipo.contains("ENTRADA")) {
						if (dto.getEntrada() == null) {
							dto.setEntrada(r);
						} else if (dto.getEntradaHoraExtra() == null) {
							dto.setEntradaHoraExtra(r);
						}
					} else if (codTipo == EnumTipoRegistroPonto.INTERVALO.getValor()
							|| (nomeTipo.contains("INTERVALO") && !nomeTipo.contains("RETORNO"))) {
						dto.setSaidaIntervalo(r);
					} else if (codTipo == EnumTipoRegistroPonto.RETORNO_INTERVALO.getValor()
							|| nomeTipo.contains("RETORNO")) {
						dto.setRetornoIntervalo(r);
					} else if (codTipo == EnumTipoRegistroPonto.SAIDA.getValor() || nomeTipo.contains("SAÍDA")
							|| nomeTipo.contains("SAIDA")) {
						if (dto.getSaida() == null) {
							dto.setSaida(r);
						} else if (dto.getSaidaHoraExtra() == null) {
							dto.setSaidaHoraExtra(r);
						}
					}
				}

				long minutosHE = 0;
				if (dto.getEntradaHoraExtra() != null && dto.getSaidaHoraExtra() != null) {
					minutosHE = java.time.Duration.between(dto.getEntradaHoraExtra().getDataRegistroPonto(),
							dto.getSaidaHoraExtra().getDataRegistroPonto()).toMinutes();
				}
				if (minutosHE > 0) {
					long h = minutosHE / 60;
					long m = minutosHE % 60;
					dto.setValorHoraExtra(String.format("%02d:%02d", h, m));
				}
			}
			relatorio.add(dto);
		}

		return relatorio;
	}

}
