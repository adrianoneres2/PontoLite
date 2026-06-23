package com.octadata.pontolite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.TipoEscala;
import com.octadata.pontolite.repository.TipoEscalaRepository;

@Service
public class TipoEscalaServiceImpl implements TipoEscalaService {

    TipoEscalaRepository tipoEscalaRepository;

    public TipoEscalaServiceImpl(TipoEscalaRepository tipoEscalaRepository) {
        this.tipoEscalaRepository = tipoEscalaRepository;
    }

    @Override
    public TipoEscala buscarPorId(Long codigo) {
        return tipoEscalaRepository.findByCodigoTipoEscala(codigo);
    }

    @Override
    public List<TipoEscala> listarTodos() {
        return tipoEscalaRepository.findAll();
    }

}
