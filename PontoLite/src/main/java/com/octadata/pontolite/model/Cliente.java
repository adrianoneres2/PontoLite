
package com.octadata.pontolite.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.octadata.pontolite.util.DefaultConstant;

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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_cliente", schema = "pontolite")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	public Cliente() {
	}

	@Id
	@SequenceGenerator(name = "sq_cliente", sequenceName = "pontolite.sq_cliente", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_cliente")
	@Column(name = "id_cliente", nullable = false)
	private long codigoCliente;

	@NotNull
	@Size(max = 65)
	@Column(name = "nm_cliente", nullable = false)
	private String nomeCliente;

	@NotNull
	@Size(max = 19)
	@Column(name = "nr_cnpj", nullable = false)
	private String numeroCnpj;

	@Column(name = "dt_cadastro", nullable = false)
	private Date dataCadastro = new Date();

	@NotNull
	@Size(max = 60)
	@Column(name = "nm_razao_social", nullable = false)
	private String razaoSocial;

	@NotNull
	@Size(max = 20)
	@Column(name = "nr_telefone", nullable = false)
	private String numeroTelefone;

	@NotNull
	@Size(max = 200)
	@Email
	@Column(name = "nm_email", nullable = false)
	private String nomeEmail;

	@Column(name = "st_cliente", nullable = false)
	private Long situacaoCliente = 1L;

	/*
	 * Referencia do usuário que cadastrou o cliente.
	 * Foreing key id_usuario_cadastro vinda da entidade cliente a partir da
	 * entidade de usuário.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_cadastro", insertable = true, updatable = false, nullable = true, referencedColumnName = "id_usuario")
	private Usuario usuarioCadastro;

	/*
	 * Lista de usuários pertencentes a um cliente.
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.PERSIST)
	private Set<Usuario> usuarios = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cliente", cascade = CascadeType.REFRESH)
	private List<PerfilFuncionalidade> perfilFuncionalidades;

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

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNumeroTelefone() {
		return numeroTelefone;
	}

	public void setNumeroTelefone(String numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
	}

	public String getNomeEmail() {
		return nomeEmail;
	}

	public void setNomeEmail(String nomeEmail) {
		this.nomeEmail = nomeEmail;
	}

	public String getDescricaoSituacaoCliente() {
		return this.getSituacaoCliente().equals(DefaultConstant.ATIVO) ? "Ativo" : "Inativo";
	}

	public List<PerfilFuncionalidade> getPerfilFuncionalidades() {
		return perfilFuncionalidades;
	}

	public void setPerfilFuncionalidades(List<PerfilFuncionalidade> perfilFuncionalidades) {
		this.perfilFuncionalidades = perfilFuncionalidades;
	}
}
