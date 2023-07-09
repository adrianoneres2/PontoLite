package com.octadata.pontolite.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
		@JoinColumn(name = "id_perfil", insertable = false, updatable = false)
		private Perfil perfil;	
		
		@Column(name = "id_usuario_cadastro	", nullable = false)
		private Long  codigoUsuarioCadastro;
		
		
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
		
		public Long getCodigoUsuarioCadastro() {
			return codigoUsuarioCadastro;
		}
		public void setCodigoUsuarioCadastro(Long codigoUsuarioCadastro) {
			this.codigoUsuarioCadastro = codigoUsuarioCadastro;
		}
				
		public Perfil getPerfil() {
			return perfil;
		}
		public void setPerfil(Perfil perfil) {
			this.perfil = perfil;
		}
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
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
