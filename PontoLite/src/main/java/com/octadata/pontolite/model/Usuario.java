package com.octadata.pontolite.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
/// @Data
@Table(name = "tb_usuario", schema = "pontolite")
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	public Usuario() {
	}

	@Id
	@SequenceGenerator(name = "sq_idusuario", sequenceName = "pontolite.sq_idusuario", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_idusuario")
	@Column(name = "id_usuario", nullable = false)
	private Long codigoUsuario;

	@Column(name = "nm_usuario", nullable = false)
	private String username;

	@Column(name = "nm_senha", nullable = false)
	private String password;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") // Adjust pattern as needed
	@Column(name = "dt_cadastro", nullable = false)
	private Date dataCadastro;

	@Column(name = "nm_email", nullable = false)
	private String email;

	@Column(name = "st_usuario", nullable = true)
	private Long status;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_perfil", insertable = true, updatable = true, nullable = false)
	private Perfil perfil;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_cadastro", insertable = true, updatable = false, nullable = false)
	private Usuario usuarioCadastro;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_jornada", insertable = true, updatable = true, nullable = true)
	private Jornada jornada;

	/*
	 * Foreing key de cliente na entidade de usuário.
	 * Referencia de qual cliente esse usuário pertence.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cliente", insertable = true, updatable = true, nullable = false)
	private Cliente cliente;

	/*
	 * Referência de cadastro com o Cliente
	 * Usuário que cadastra o cliente na Classe "Cliente"
	 * Faz o papel de chave primária (id_usuario)
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "usuarioCadastro", cascade = CascadeType.ALL)
	private List<Cliente> clientes;

	@Transient
	private String passwordConfirmacao;

	public Long getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCodigoUsuario(Long codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Jornada getJornada() {
		return jornada;
	}

	public void setJornada(Jornada jornada) {
		this.jornada = jornada;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public String getPasswordConfirmacao() {
		return passwordConfirmacao;
	}

	public void setPasswordConfirmacao(String passwordConfirmacao) {
		this.passwordConfirmacao = passwordConfirmacao;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		/*
		 * for(PerfilFuncionalidade obj : this.getPerfil().getPerfilFuncionalidades()) {
		 * System.out.println(obj.getAuthority());
		 * }
		 */
		return this.getPerfil().getPerfilFuncionalidades();
	}

	public String getDescricaoStatus() {
		return this.getStatus().equals(DefaultConstant.ATIVO) ? "Ativo" : "Inativo";
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
