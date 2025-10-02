package com.octadata.pontolite.service;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository usuarioRepository;

    UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
	
    @Override
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

}
