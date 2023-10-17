package com.octadata.pontolite.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name= "tb_registro_ponto", schema = "pontolite")
public class RegistroPonto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id	
	@GeneratedValue(generator = "sq_registro_ponto", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name="sq_registro_ponto", sequenceName = "pontolite.sq_registro_ponto", allocationSize=1)
	@Column(name = "id_registro_ponto", nullable = false)
	private Long codigoRegistroPonto;
	
	@Column(name="dt_registro_ponto", nullable = false)
	private LocalDateTime dataRegistroPonto;
	
	@Column(name = "st_registro_ponto", nullable = false)
	private Integer situacaoRegistroPonto;

	@ManyToOne
	@JoinColumn(name = "id_tipo_registro")
	private TipoRegistro tipoRegistro;
	
	@Column(name= "dt_aprovacao", nullable = true)
	private Date dataAprovacao; 
	
	@ManyToOne
	@JoinColumn(name="id_usuario", insertable = true, updatable = true, nullable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="id_usuario_aprovacao", insertable = true, updatable = true, nullable = true)
	private Usuario usuarioAprovacao;
	
	@ManyToOne
	@JoinColumn(name="id_registro_ponto_ajustado", insertable = true, updatable = true, nullable = true)
	private RegistroPonto registroPontoAjustado;
	
	/*
	@Override
	public Long getId() {
		return codigoRegistroPonto;
	}
	*/
	
	public Long getCodigoRegistroPonto() {
		return codigoRegistroPonto;
	}
	public void setCodigoRegistroPonto(Long codigoRegistroPonto) {
		this.codigoRegistroPonto = codigoRegistroPonto;
	}
	public LocalDateTime getDataRegistroPonto() {
		return dataRegistroPonto;
	}
	public void setDataRegistroPonto(LocalDateTime date) {
		this.dataRegistroPonto = date;
	}
	public Integer getSituacaoRegistroPonto() {
		return situacaoRegistroPonto;
	}
	public void setSituacaoRegistroPonto(Integer situacaoRegistroPonto) {
		this.situacaoRegistroPonto = situacaoRegistroPonto;
	}
	public Date getDataAprovacao() {
		return dataAprovacao;
	}
	public void setDataAprovacao(Date dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Usuario getUsuarioAprovacao() {
		return usuarioAprovacao;
	}
	public void setUsuarioAprovacao(Usuario usuarioAprovacao) {
		this.usuarioAprovacao = usuarioAprovacao;
	}
	public RegistroPonto getRegistroPontoAjustado() {
		return registroPontoAjustado;
	}
	public void setRegistroPontoAjustado(RegistroPonto registroPontoAjustado) {
		this.registroPontoAjustado = registroPontoAjustado;
	}
	
	public TipoRegistro getTipoRegistro() {
		return tipoRegistro;
	}
	public void setTipoRegistro(TipoRegistro tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(codigoRegistroPonto, dataAprovacao, dataRegistroPonto, registroPontoAjustado,
				situacaoRegistroPonto, usuario, usuarioAprovacao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistroPonto other = (RegistroPonto) obj;
		return Objects.equals(codigoRegistroPonto, other.codigoRegistroPonto)
				&& Objects.equals(dataAprovacao, other.dataAprovacao)
				&& Objects.equals(dataRegistroPonto, other.dataRegistroPonto)
				&& Objects.equals(registroPontoAjustado, other.registroPontoAjustado)
				&& Objects.equals(situacaoRegistroPonto, other.situacaoRegistroPonto)
				&& Objects.equals(usuario, other.usuario) && Objects.equals(usuarioAprovacao, other.usuarioAprovacao);
	}
	
}
