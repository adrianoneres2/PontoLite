package com.octadata.pontolite.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_funcionalidade", schema = "pontolite")
public class Funcionalidade implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_funcionalidade", nullable=false)
	private Long codigoFuncionalidade;
	
	@Column(name="nm_funcionalidade", nullable=false)
	private String nomeFuncionalidade;

	@Column(name = "cod_funcionalidade")
	private String role;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "funcionalidade", cascade = CascadeType.REFRESH)
	private List<PerfilFuncionalidade> perfilFuncionalidades;
	
	public Long getCodigoFuncionalidade() {
		return codigoFuncionalidade;
	}

	public void setCodigoFuncionalidade(Long codigoFuncionalidade) {
		this.codigoFuncionalidade = codigoFuncionalidade;
	}

	public String getNomeFuncionalidade() {
		return nomeFuncionalidade;
	}

	public void setNomeFuncionalidade(String nomeFuncionalidade) {
		this.nomeFuncionalidade = nomeFuncionalidade;
	}

	public String getRole() {
		return role;
	}

	public List<PerfilFuncionalidade> getPerfilFuncionalidades() {
		return perfilFuncionalidades;
	}

	public void setPerfilFuncionalidades(List<PerfilFuncionalidade> perfilFuncionalidades) {
		this.perfilFuncionalidades = perfilFuncionalidades;
	}
	
}
