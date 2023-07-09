package com.octadata.pontolite.model;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="tb_perfil_funcionalidade", schema = "pontolite")
public class PerfilFuncionalidade implements GrantedAuthority, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public PerfilFuncionalidade(){}
	
	@Id
	@Column(name="id_perfil_funcionalidade", nullable=false)
	private Long codigoPerfilFuncionalidade;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_perfil", insertable = false, updatable = false)
	private Perfil perfil;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_funcionalidade", insertable = false, updatable = false)
	private Funcionalidade funcionalidade;
	
	@Override
	public String getAuthority() {
		return funcionalidade.getRole();
	}
	
	
	public Long getCodigoPerfilFuncionalidade() {
		return codigoPerfilFuncionalidade;
	}

	public void setCodigoPerfilFuncionalidade(Long codigoPerfilFuncionalidade) {
		this.codigoPerfilFuncionalidade = codigoPerfilFuncionalidade;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Funcionalidade getFuncionalidade() {
		return funcionalidade;
	}

	public void setFuncionalidade(Funcionalidade funcionalidade) {
		this.funcionalidade = funcionalidade;
	}
	
}
