package com.octadata.pontolite.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_jornada_data_hora", schema = "pontolite")
public class JornadaDataHora implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public JornadaDataHora(){};
	
    //@Id
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    //private Long id;
	
	@Column(name="dt_jornada", nullable = false)
	private Date dataJornada;
	
	@Column(name="nr_ordem", nullable = false)
	private Long numeroOrdem;

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_jornada", insertable = false, updatable = false)
	private Jornada jornada;

	public Date getDataJornada() {
		return dataJornada;
	}

	public void setDataJornada(Date dataJornada) {
		this.dataJornada = dataJornada;
	}

	public Long getNumeroOrdem() {
		return numeroOrdem;
	}

	public void setNumeroOrdem(Long numeroOrdem) {
		this.numeroOrdem = numeroOrdem;
	}

	public Jornada getJornada() {
		return jornada;
	}

	public void setJornada(Jornada jornada) {
		this.jornada = jornada;
	}
	
}
