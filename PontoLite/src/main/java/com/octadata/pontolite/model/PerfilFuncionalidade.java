package com.octadata.pontolite.model;

import org.springframework.security.core.GrantedAuthority;

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
@Table(name = "tb_perfil_funcionalidade", schema = "pontolite")
public class PerfilFuncionalidade implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	public PerfilFuncionalidade() {
	}

	@Id
	@SequenceGenerator(name = "seq_perfil_funcionalidade", sequenceName = "pontolite.seq_perfil_funcionalidade", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_perfil_funcionalidade")
	@Column(name = "id_perfil_funcionalidade", nullable = false)
	private Long codigoPerfilFuncionalidade;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_perfil")
	private Perfil perfil;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_funcionalidade")
	private Funcionalidade funcionalidade;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@Override
	public String getAuthority() {
		return getFuncionalidade().getRole();
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
