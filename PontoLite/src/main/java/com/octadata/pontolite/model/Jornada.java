package com.octadata.pontolite.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_jornada", schema = "pontolite")
public class Jornada implements Serializable {

	private static final long serialVersionUID = 1L;

	public Jornada() {
	};

	@Id
	@SequenceGenerator(name = "sq_jornada", sequenceName = "pontolite.sq_jornada", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_jornada")
	@Column(name = "id_jornada", nullable = false)
	private Long codigoJornada;

	@Column(name = "nm_jornada", nullable = false)
	private String nomeJornada;

	@Column(name = "dt_criacao", nullable = false)
	private Date dataCriacao;

	@Column(name = "st_jornada", nullable = false)
	private Long situacaoJornada;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cliente", insertable = true, updatable = false)
	private Cliente cliente;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_cadastro", insertable = true, updatable = false)
	private Usuario usuarioCadastro;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jornada", cascade = CascadeType.REFRESH)
	private List<JornadaDataHora> listaJornadaDataHora;

	public Long getCodigoJornada() {
		return codigoJornada;
	}

	public void setCodigoJornada(Long codigoJornada) {
		this.codigoJornada = codigoJornada;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Long getSituacaoJornada() {
		return situacaoJornada;
	}

	public void setSituacaoJornada(Long situacaoJornada) {
		this.situacaoJornada = situacaoJornada;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public List<JornadaDataHora> getListaJornadaDataHora() {
		return listaJornadaDataHora;
	}

	public void setListaJornadaDataHora(List<JornadaDataHora> listaJornadaDataHora) {
		this.listaJornadaDataHora = listaJornadaDataHora;
	}

	public String getNomeJornada() {
		return nomeJornada;
	}

	public void setNomeJornada(String nomeJornada) {
		this.nomeJornada = nomeJornada;
	}

	public String getDescricaoSituacaoJornada() {
		return situacaoJornada == 1 ? "Ativo" : "Inativo";
	}
}
