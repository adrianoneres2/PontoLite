package com.octadata.pontolite.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_tipo_registro", schema = "pontolite")
public class TipoRegistro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public TipoRegistro() {};
	
	public TipoRegistro(Long codigoTipoRegistro, String nomeTipoRegistro) {
		this.codigoTipoRegistro = codigoTipoRegistro;
		this.nomeTipoRegistro = nomeTipoRegistro;
	};
	
	@Id
	@Column(name = "id_tipo_registro")
	private long codigoTipoRegistro;
	
	@Column(name = "nm_tipo_registro")
	private String nomeTipoRegistro;

/*	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_registro_ponto")
	private RegistroPonto registroPonto;
	*/
	
	public long getCodigoTipoRegistro() {
		return codigoTipoRegistro;
	}

	public void setCodigoTipoRegistro(long codigoTipoRegistro) {
		this.codigoTipoRegistro = codigoTipoRegistro;
	}

	public String getNomeTipoRegistro() {
		return nomeTipoRegistro;
	}

	public void setNomeTipoRegistro(String nomeTipoRegistro) {
		this.nomeTipoRegistro = nomeTipoRegistro;
	}	
	
}
