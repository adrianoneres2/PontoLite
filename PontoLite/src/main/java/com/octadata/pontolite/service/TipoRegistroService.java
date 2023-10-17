package com.octadata.pontolite.service;

import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.TipoRegistro;

@Service
public interface TipoRegistroService {

	TipoRegistro porCodigoTipoRegistro(Long codigoTipoRegistro);

}
