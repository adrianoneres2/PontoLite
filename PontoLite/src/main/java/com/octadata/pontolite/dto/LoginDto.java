package com.octadata.pontolite.dto;

import com.octadata.pontolite.model.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

public class LoginDto {

		@Column(name="username")
		@Enumerated(EnumType.STRING)
		@NotBlank(message = "Nome do usuário é obrigatório!")
		private String username;
		
		@Column(name="password")
		@Enumerated(EnumType.STRING)
		@NotBlank(message = "O campo password é obrigatório!")
		private String password;
		
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
		
		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Usuario toUsuario() {
			Usuario usuario = new Usuario();
			usuario.setUsername(username);
			usuario.setPassword(password);
			return usuario;
		}

}
