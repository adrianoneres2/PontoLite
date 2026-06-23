package com.octadata.pontolite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.TipoEscala;

@Service
public interface TipoEscalaService {

    TipoEscala buscarPorId(Long codigo);

    List<TipoEscala> listarTodos();

}
