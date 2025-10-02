package com.octadata.pontolite.service;

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
	
}
