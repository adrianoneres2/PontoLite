package com.octadata.pontolite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.model.Jornada;
import com.octadata.pontolite.repository.JornadaRepository;

@Service
public class JornadaServiceImpl implements JornadaService {
	
	@Autowired
	JornadaRepository jornadaRepository;

	@Override
	public Jornada salvar(Jornada jornada) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Jornada> porCliente(Cliente cliente){
		return jornadaRepository.findByCliente(cliente);
	}

}
