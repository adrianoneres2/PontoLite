package com.octadata.pontolite.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.RegistroPonto;
import com.octadata.pontolite.util.EnumStatusRegistro;

import com.octadata.pontolite.model.Usuario;

@Service
public interface RegistroPontoService {

	RegistroPonto salvar(RegistroPonto registroPonto);

	List<RegistroPonto> listarPorUsuario();

	List<RegistroPonto> listarPorDataRegistroPonto();

	List<RegistroPonto> listarPorDataRegistroPontoHoje();

	List<RegistroPonto> listarPeriodoPorUsuario(Usuario usuario, LocalDateTime dataHoraIncial,
			LocalDateTime dataHoraFinal);

	Long calcularTempoRegistro(LocalDateTime dataRegistroPontoInicio, LocalDateTime dataRegistroPontoFim);

	Long calcularTempoTotalRegistro(List<RegistroPonto> registrosPonto);

	Long calcularTempoPorTipoRegistro(RegistroPonto registroPonto, List<RegistroPonto> registrosPonto);

	RegistroPonto porId(Long codigoRegistroPonto);

	RegistroPonto alterarHorario(Long codigoRegistroPonto, LocalDateTime novaDataHora);

	List<com.octadata.pontolite.dto.RelatorioPontoDiaDTO> montarRelatorioMensal(Usuario usuario,
			LocalDateTime dataHoraInicial, LocalDateTime dataHoraFinal);

	RegistroPonto solicitacaoAlteracao(Long codigoRegistroPonto, java.time.LocalDateTime novaDataHora,
			String observacao);

}
