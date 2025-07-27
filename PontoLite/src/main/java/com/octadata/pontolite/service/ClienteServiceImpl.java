package com.octadata.pontolite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.repository.ClienteRepository;


@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private AutenticacaoService autenticacaoService;
		
	public static final String SUCESSO = "Sucesso";
	public static final String ERRO = "Erro";
	
	@Override
	public String salvar(Cliente cliente) {
		try {
			cliente.setUsuarioCadastro(autenticacaoService.getUsuarioAutenticado());
			clienteRepository.save(cliente);
			return SUCESSO;	
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return ERRO;
		}
	}
}
