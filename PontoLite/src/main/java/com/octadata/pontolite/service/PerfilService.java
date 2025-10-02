package com.octadata.pontolite.service;

import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.Perfil;

@Service
public interface PerfilService {

	Perfil porNome(String nome);
	
}
