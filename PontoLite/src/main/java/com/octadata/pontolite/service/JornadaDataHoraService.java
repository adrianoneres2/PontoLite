package com.octadata.pontolite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.JornadaDataHora;

@Service
public interface JornadaDataHoraService {

    JornadaDataHora salvar(JornadaDataHora jornadaDataHora);

    List<JornadaDataHora> salvarListaDataHora(List<JornadaDataHora> listaJornadaDataHora);

}
