package com.octadata.pontolite.service;

import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.Usuario;

@Service
public interface UsuarioService {

	Usuario salvar(Usuario usuario);

}
