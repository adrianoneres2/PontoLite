package com.octadata.pontolite.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.exception.NegocioException;
import com.octadata.pontolite.model.Cliente;

@Service
public interface ClienteService {

	Cliente salvar(Cliente cliente);

	List<Cliente> listarTodos();

	Cliente alterarStatus(Cliente cliente);

	Cliente porId(Long codigoCliente);

	Cliente alterar(Cliente cliente) throws NegocioException;

	Page<Cliente> listarTodosPaginado(int pagina, int tamanho);

}
