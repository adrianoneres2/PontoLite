package com.octadata.pontolite.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import com.octadata.pontolite.model.RegistroPonto;

public class RelatorioPontoDiaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDate data;
	private String dataFormatada;
	private String diaSemana;

	private RegistroPonto entrada;
	private RegistroPonto saidaIntervalo;
	private RegistroPonto retornoIntervalo;
	private RegistroPonto saida;

	private RegistroPonto entradaHoraExtra;
	private RegistroPonto saidaHoraExtra;

	private String valorHoraExtra = "00:00";
	private String totalTrabalhado = "00:00";

	private Boolean hasSolicitacaoEntrada = false;
	private Boolean hasSolicitacaoSaidaIntervalo = false;
	private Boolean hasSolicitacaoRetornoIntervalo = false;
	private Boolean hasSolicitacaoSaida = false;
	private Boolean hasSolicitacaoEntradaHoraExtra = false;
	private Boolean hasSolicitacaoSaidaHoraExtra = false;

	private String observacaoSolicitacaoEntrada;
	private String observacaoSolicitacaoSaidaIntervalo;
	private String observacaoSolicitacaoRetornoIntervalo;
	private String observacaoSolicitacaoSaida;
	private String observacaoSolicitacaoEntradaHoraExtra;
	private String observacaoSolicitacaoSaidaHoraExtra;

	public RelatorioPontoDiaDTO() {
	}

	public RelatorioPontoDiaDTO(LocalDate data) {
		this.data = data;
		if (data != null) {
			this.dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			String nomeDia = data.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pt-BR"));
			this.diaSemana = nomeDia.substring(0, 1).toUpperCase() + nomeDia.substring(1);
		}
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
		if (data != null) {
			this.dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			String nomeDia = data.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pt-BR"));
			this.diaSemana = nomeDia.substring(0, 1).toUpperCase() + nomeDia.substring(1);
		}
	}

	public String getDataFormatada() {
		return dataFormatada;
	}

	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public RegistroPonto getEntrada() {
		return entrada;
	}

	public void setEntrada(RegistroPonto entrada) {
		this.entrada = entrada;
	}

	public RegistroPonto getSaidaIntervalo() {
		return saidaIntervalo;
	}

	public void setSaidaIntervalo(RegistroPonto saidaIntervalo) {
		this.saidaIntervalo = saidaIntervalo;
	}

	public RegistroPonto getRetornoIntervalo() {
		return retornoIntervalo;
	}

	public void setRetornoIntervalo(RegistroPonto retornoIntervalo) {
		this.retornoIntervalo = retornoIntervalo;
	}

	public RegistroPonto getSaida() {
		return saida;
	}

	public void setSaida(RegistroPonto saida) {
		this.saida = saida;
	}

	public RegistroPonto getEntradaHoraExtra() {
		return entradaHoraExtra;
	}

	public void setEntradaHoraExtra(RegistroPonto entradaHoraExtra) {
		this.entradaHoraExtra = entradaHoraExtra;
	}

	public RegistroPonto getSaidaHoraExtra() {
		return saidaHoraExtra;
	}

	public void setSaidaHoraExtra(RegistroPonto saidaHoraExtra) {
		this.saidaHoraExtra = saidaHoraExtra;
	}

	public String getValorHoraExtra() {
		return valorHoraExtra;
	}

	public void setValorHoraExtra(String valorHoraExtra) {
		this.valorHoraExtra = valorHoraExtra;
	}

	public String getTotalTrabalhado() {
		return totalTrabalhado;
	}

	public void setTotalTrabalhado(String totalTrabalhado) {
		this.totalTrabalhado = totalTrabalhado;
	}

	public Boolean getHasSolicitacaoEntrada() {
		return hasSolicitacaoEntrada;
	}

	public void setHasSolicitacaoEntrada(Boolean hasSolicitacaoEntrada) {
		this.hasSolicitacaoEntrada = hasSolicitacaoEntrada;
	}

	public Boolean getHasSolicitacaoSaidaIntervalo() {
		return hasSolicitacaoSaidaIntervalo;
	}

	public void setHasSolicitacaoSaidaIntervalo(Boolean hasSolicitacaoSaidaIntervalo) {
		this.hasSolicitacaoSaidaIntervalo = hasSolicitacaoSaidaIntervalo;
	}

	public Boolean getHasSolicitacaoRetornoIntervalo() {
		return hasSolicitacaoRetornoIntervalo;
	}

	public void setHasSolicitacaoRetornoIntervalo(Boolean hasSolicitacaoRetornoIntervalo) {
		this.hasSolicitacaoRetornoIntervalo = hasSolicitacaoRetornoIntervalo;
	}

	public Boolean getHasSolicitacaoSaida() {
		return hasSolicitacaoSaida;
	}

	public void setHasSolicitacaoSaida(Boolean hasSolicitacaoSaida) {
		this.hasSolicitacaoSaida = hasSolicitacaoSaida;
	}

	public Boolean getHasSolicitacaoEntradaHoraExtra() {
		return hasSolicitacaoEntradaHoraExtra;
	}

	public void setHasSolicitacaoEntradaHoraExtra(Boolean hasSolicitacaoEntradaHoraExtra) {
		this.hasSolicitacaoEntradaHoraExtra = hasSolicitacaoEntradaHoraExtra;
	}

	public Boolean getHasSolicitacaoSaidaHoraExtra() {
		return hasSolicitacaoSaidaHoraExtra;
	}

	public void setHasSolicitacaoSaidaHoraExtra(Boolean hasSolicitacaoSaidaHoraExtra) {
		this.hasSolicitacaoSaidaHoraExtra = hasSolicitacaoSaidaHoraExtra;
	}

	public String getObservacaoSolicitacaoEntrada() {
		return observacaoSolicitacaoEntrada;
	}

	public void setObservacaoSolicitacaoEntrada(String observacaoSolicitacaoEntrada) {
		this.observacaoSolicitacaoEntrada = observacaoSolicitacaoEntrada;
	}

	public String getObservacaoSolicitacaoSaidaIntervalo() {
		return observacaoSolicitacaoSaidaIntervalo;
	}

	public void setObservacaoSolicitacaoSaidaIntervalo(String observacaoSolicitacaoSaidaIntervalo) {
		this.observacaoSolicitacaoSaidaIntervalo = observacaoSolicitacaoSaidaIntervalo;
	}

	public String getObservacaoSolicitacaoRetornoIntervalo() {
		return observacaoSolicitacaoRetornoIntervalo;
	}

	public void setObservacaoSolicitacaoRetornoIntervalo(String observacaoSolicitacaoRetornoIntervalo) {
		this.observacaoSolicitacaoRetornoIntervalo = observacaoSolicitacaoRetornoIntervalo;
	}

	public String getObservacaoSolicitacaoSaida() {
		return observacaoSolicitacaoSaida;
	}

	public void setObservacaoSolicitacaoSaida(String observacaoSolicitacaoSaida) {
		this.observacaoSolicitacaoSaida = observacaoSolicitacaoSaida;
	}

	public String getObservacaoSolicitacaoEntradaHoraExtra() {
		return observacaoSolicitacaoEntradaHoraExtra;
	}

	public void setObservacaoSolicitacaoEntradaHoraExtra(String observacaoSolicitacaoEntradaHoraExtra) {
		this.observacaoSolicitacaoEntradaHoraExtra = observacaoSolicitacaoEntradaHoraExtra;
	}

	public String getObservacaoSolicitacaoSaidaHoraExtra() {
		return observacaoSolicitacaoSaidaHoraExtra;
	}

	public void setObservacaoSolicitacaoSaidaHoraExtra(String observacaoSolicitacaoSaidaHoraExtra) {
		this.observacaoSolicitacaoSaidaHoraExtra = observacaoSolicitacaoSaidaHoraExtra;
	}

}
