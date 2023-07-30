package com.octadata.pontolite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.RegistroPonto;

@Service
public interface RegistroPontoService {

	void salvar(RegistroPonto registroPonto);

	List<RegistroPonto> listarPorUsuario();

	List<RegistroPonto> listarPorDataRegistroPonto();

	List<RegistroPonto> listarPorDataRegistroPontoHoje();

}
