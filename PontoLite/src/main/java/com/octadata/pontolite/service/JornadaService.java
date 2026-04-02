package com.octadata.pontolite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.model.Jornada;

@Service
public interface JornadaService {

	Jornada salvar(Jornada jornada);

	List<Jornada> porCliente(Cliente cliente);

	Jornada buscarPorId(Long id);

	void alterarStatus(Jornada jornada);

}
