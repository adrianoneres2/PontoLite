package com.octadata.pontolite.service;

import org.springframework.stereotype.Service;

import com.octadata.pontolite.base.Message;
import com.octadata.pontolite.model.Cliente;

@Service
public interface ClienteService {

	Message salvar(Cliente cliente);

}
