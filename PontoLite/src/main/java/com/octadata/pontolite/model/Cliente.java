package com.octadata.pontolite.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "tb_cliente", schema = "pontolite")
public class Cliente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public Cliente() {}
	
	@Id
	@SequenceGenerator(name="sq_cliente", sequenceName="pontolite.sq_cliente", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sq_cliente")
	@Column(name="id_cliente", nullable=false)
	private long codigoCliente;

	@Column(name="nm_cliente", nullable=false)
	private String nomeCliente;
	
	@Column(name="nr_cnpj", nullable=false)
	private String numeroCnpj;
	
	@Column(name="dt_cadastro", nullable=false)
	private Date dataCadastro;
	
	@Column(name="st_cliente", nullable=false)
	private Long situacaoCliente;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_usuario_cadastro", insertable = false, updatable = false)
	private Usuario usuarioCadastro;

	public long getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNumeroCnpj() {
		return numeroCnpj;
	}

	public void setNumeroCnpj(String numeroCnpj) {
		this.numeroCnpj = numeroCnpj;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getSituacaoCliente() {
		return situacaoCliente;
	}

	public void setSituacaoCliente(Long situacaoCliente) {
		this.situacaoCliente = situacaoCliente;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}
	
}
