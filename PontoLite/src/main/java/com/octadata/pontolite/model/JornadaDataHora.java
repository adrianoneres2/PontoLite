package com.octadata.pontolite.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_jornada_data_hora", schema = "pontolite")
public class JornadaDataHora implements Serializable {

	private static final long serialVersionUID = 1L;

	public JornadaDataHora() {
	};

	@Id
	@SequenceGenerator(name = "sq_jornada_data_hora", sequenceName = "pontolite.sq_jornada_data_hora", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_jornada_data_hora")
	@Column(name = "id_jornada_data_hora", nullable = false)
	private Long codigoJornadaDataHora;

	@Column(name = "dt_jornada", nullable = false)
	private LocalDateTime dataJornadaDataHora;

	@Column(name = "id_dia", nullable = false)
	private Long codigoDia;

	@Column(name = "id_tipo_registro", nullable = false)
	private Long codigoTipoRegistro;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_jornada", insertable = true, updatable = true)
	private Jornada jornada;

	public LocalDateTime getDataJornadaDataHora() {
		return dataJornadaDataHora;
	}

	public void setDataJornadaDataHora(LocalDateTime dataJornadaDataHora) {
		this.dataJornadaDataHora = dataJornadaDataHora;
	}

	public Jornada getJornada() {
		return jornada;
	}

	public void setJornada(Jornada jornada) {
		this.jornada = jornada;
	}

	public Long getCodigoDia() {
		return codigoDia;
	}

	public void setCodigoDia(Long codigoDia) {
		this.codigoDia = codigoDia;
	}

	public Long getCodigoTipoRegistro() {
		return codigoTipoRegistro;
	}

	public void setCodigoTipoRegistro(Long codigoTipoRegistro) {
		this.codigoTipoRegistro = codigoTipoRegistro;
	}

	public Long getCodigoJornadaDataHora() {
		return codigoJornadaDataHora;
	}

	public void setCodigoJornadaDataHora(Long codigoJornadaDataHora) {
		this.codigoJornadaDataHora = codigoJornadaDataHora;
	}
}
