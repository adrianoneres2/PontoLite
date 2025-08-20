package com.octadata.pontolite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.base.EnumMessage;
import com.octadata.pontolite.exception.NegocioException;
import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.repository.ClienteRepository;


@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Override
	public void salvar(Cliente cliente) {
			cliente.setUsuarioCadastro(autenticacaoService.getUsuarioAutenticado());
			validar(cliente);
			clienteRepository.save(cliente);
	}
	
	protected void validar(Cliente cliente) {
		if(!clienteRepository.findByNomeCliente(cliente.getNomeCliente()).isEmpty()) throw new NegocioException(EnumMessage.ERROR.toString(), "Cliente já existe na base!", "");
		if(!clienteRepository.findByNumeroCnpj(cliente.getNumeroCnpj()).isEmpty()) throw new NegocioException(EnumMessage.ERROR.toString(), "Número de CNPJ já cadastrado na base!", "");
		if(!clienteRepository.findByNomeEmail(cliente.getNomeEmail()).isEmpty()) throw new NegocioException(EnumMessage.ERROR.toString(), "E-mail já cadastrado na base!", "");
		if(!clienteRepository.findByRazaoSocial(cliente.getRazaoSocial()).isEmpty()) throw new NegocioException(EnumMessage.ERROR.toString(), "Razão Social já cadastrada na base!", "");
	}
}
