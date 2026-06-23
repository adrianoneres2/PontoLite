package com.octadata.pontolite.service;

import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.TipoRegistro;
import com.octadata.pontolite.repository.TipoRegistroRepository;

@Service
public class TipoRegistroServiceImpl implements TipoRegistroService {

	private final TipoRegistroRepository tipoRegistroRepository;

	public TipoRegistroServiceImpl(TipoRegistroRepository tipoRegistroRepository) {
		this.tipoRegistroRepository = tipoRegistroRepository;
	}

	@Override
	public TipoRegistro porCodigoTipoRegistro(Long codigoTipoRegistro) {
		return tipoRegistroRepository.findByCodigoTipoRegistro(codigoTipoRegistro);
	}
}
