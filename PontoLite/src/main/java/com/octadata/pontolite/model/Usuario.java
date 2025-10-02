package com.octadata.pontolite.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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


@Entity
///@Data
@Table(name = "tb_usuario", schema = "pontolite")
public class Usuario implements UserDetails, Serializable{
	
		private static final long serialVersionUID = 1L;

		public Usuario(){}
		
		@Id
		@SequenceGenerator(name="sq_idusuario", sequenceName="pontolite.sq_idusuario", allocationSize = 1, initialValue = 1)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sq_idusuario")
		@Column(name = "id_usuario", nullable = false)
		private Long  codigoUsuario;
		
		@Column(name = "nm_usuario", nullable = false)
		private String   username;
		
		@Column(name = "nm_senha", nullable = false)
		private String   password;
		
		@Column(name = "dt_cadastro", nullable = true)
		private Date dataCadastro;
				
		@Column(name = "nm_email", nullable = false)
		private String   email;
		
		@Column(name = "st_usuario", nullable = true)
		private Long   situacaoUsuario;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "id_perfil", insertable = true, updatable = false)
		private Perfil perfil;	
		
		//@Column(name = "id_usuario_cadastro	", nullable = false)
		//private Long  codigoUsuarioCadastro;

		@OneToOne(fetch = FetchType.EAGER)
		@JoinColumn(name="id_usuario_cadastro", insertable = true, updatable = false)
		private Usuario usuarioCadastro;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name="id_jornada", insertable = true, updatable = false)
		private Jornada jornada;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name="id_cliente", insertable = true, updatable = false)
		private Cliente cliente;
		
		/*
		 * Referência de cadastro com o Cliente
		 * Usuário que cadastra o cliente na Classe "Cliente"
		 * Faz o papel de chave primária (id_usuario)
		 * */
		@OneToMany(fetch = FetchType.EAGER, 
				   mappedBy = "usuarioCadastro", 
				   cascade = CascadeType.ALL)
		private List<Cliente> clientes;
		
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
		
		public Long getSituacaoUsuario() {
			return situacaoUsuario;
		}
		public void setSituacaoUsuario(Long situacaoUsuario) {
			this.situacaoUsuario = situacaoUsuario;
		}		
		/*
		public Long getCodigoUsuarioCadastro() {
			return codigoUsuarioCadastro;
		}
		public void setCodigoUsuarioCadastro(Long codigoUsuarioCadastro) {
			this.codigoUsuarioCadastro = codigoUsuarioCadastro;
		}
		*/
				
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
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			
			/*
			for(PerfilFuncionalidade obj : this.getPerfil().getPerfilFuncionalidades()) {
				System.out.println(obj.getAuthority());
			}
			*/
			return this.getPerfil().getPerfilFuncionalidades();
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
