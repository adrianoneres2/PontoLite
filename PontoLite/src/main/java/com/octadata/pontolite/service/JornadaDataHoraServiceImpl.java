package com.octadata.pontolite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.JornadaDataHora;
import com.octadata.pontolite.repository.JornadaDataHoraRepository;

@Service
public class JornadaDataHoraServiceImpl implements JornadaDataHoraService {

    @Autowired
    JornadaDataHoraRepository jornadaDataHoraRepository;

    @Override
    public JornadaDataHora salvar(JornadaDataHora jornadaDataHora) {
        return jornadaDataHoraRepository.save(jornadaDataHora);
    }

    @Override
    public List<JornadaDataHora> salvarListaDataHora(List<JornadaDataHora> listaJornadaDataHora) {
        return jornadaDataHoraRepository.saveAll(listaJornadaDataHora);
    }

}
