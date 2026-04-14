package com.octadata.pontolite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.Perfil;
import com.octadata.pontolite.repository.PerfilRepository;

@Service
public class PerfilServiceImpl implements PerfilService {

	@Autowired
	private PerfilRepository perfilRepostory;

	@Override
	public Perfil porNome(String nome) {
		return perfilRepostory.findByNomePerfil(nome).get();
	}

	@Override
	public List<Perfil> findAll() {
		return perfilRepostory.findAll();
	}

	@Override
	public Perfil porId(Long codigoPerfil) {
		return perfilRepostory.findByCodigoPerfil(codigoPerfil).get();
	}

}
