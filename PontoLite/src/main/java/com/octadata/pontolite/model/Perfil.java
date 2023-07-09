package com.octadata.pontolite.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="tb_perfil", schema = "pontolite")
public class Perfil implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_perfil", nullable=false)
	private Long codigoPerfil;
	
	@Column(name="nm_perfil", nullable=false)
	private String nomePerfil;
	
	@Column(name="st_perfil", nullable=false)
	private Long situacaoPerfil;
		
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "perfil", cascade = CascadeType.REFRESH)
	private List<PerfilFuncionalidade> perfilFuncionalidades;
	
	
	public Long getCodigoPerfil() {
		return codigoPerfil;
	}
	public void setCodigoPerfil(Long codigoPerfil) {
		this.codigoPerfil = codigoPerfil;
	}
	public String getNomePerfil() {
		return nomePerfil;
	}
	public void setNomePerfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
	}
	
	public Long getSituacaoPerfil() {
		return situacaoPerfil;
	}
	public void setSituacaoPerfil(Long situacaoPerfil) {
		this.situacaoPerfil = situacaoPerfil;
	}
	public List<PerfilFuncionalidade> getPerfilFuncionalidades() {
		return perfilFuncionalidades;
	}
	public void setPerfilFuncionalidades(List<PerfilFuncionalidade> perfilFuncionalidades) {
		this.perfilFuncionalidades = perfilFuncionalidades;
	}
	
}
