package com.octadata.pontolite.service;

import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.RegistroPonto;

@Service
public interface RegistroPontoService {

	void salvar(RegistroPonto registroPonto);

}
