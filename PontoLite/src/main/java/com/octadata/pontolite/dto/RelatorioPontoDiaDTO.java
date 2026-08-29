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
}
