package com.octadata.pontolite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.Perfil;

@Service
public interface PerfilService {

	Perfil porNome(String nome);

	List<Perfil> findAll();

	Perfil porId(Long codigoPerfil);

}
