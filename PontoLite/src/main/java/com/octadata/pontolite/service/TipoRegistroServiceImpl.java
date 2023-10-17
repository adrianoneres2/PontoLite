package com.octadata.pontolite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.TipoRegistro;
import com.octadata.pontolite.repository.TipoRegistroRepository;

@Service
public class TipoRegistroServiceImpl implements TipoRegistroService {

	@Autowired
	TipoRegistroRepository tipoRegistroRepository;

	@Override
	public TipoRegistro porCodigoTipoRegistro(Long codigoTipoRegistro) { 
		return tipoRegistroRepository.findByCodigoTipoRegistro(codigoTipoRegistro);
	}
}
