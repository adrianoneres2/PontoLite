package com.octadata.pontolite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.base.DefaultConstant;
import com.octadata.pontolite.base.EnumMessage;
import com.octadata.pontolite.exception.NegocioException;
import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.repository.ClienteRepository;

import jakarta.transaction.Transactional;


@Service
public class ClienteServiceImpl implements ClienteService {

    private final UsuarioService usuarioService;

	@Autowired
	private ClienteRepository clienteRepository;

    ClienteServiceImpl(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
	@Override
	@Transactional
	public Cliente salvar(Cliente cliente) throws NegocioException{
		validar(cliente);
		cliente = clienteRepository.save(cliente);
		usuarioService.salvar(usuarioService.criarUsuarioPadrao(cliente));
		return cliente;
	}
	
	@Override
	public Cliente alterar(Cliente cliente) throws NegocioException{
		validar(cliente);
		cliente = clienteRepository.save(cliente);
		return cliente;
	}
	
	protected void validar(Cliente cliente) {
		Cliente clienteValidacao = clienteRepository.findByNomeCliente(cliente.getNomeCliente()).orElse(new Cliente());
		
		if(clienteValidacao.getCodigoCliente() != 0 && clienteValidacao.getCodigoCliente()!=cliente.getCodigoCliente()) {
			throw new NegocioException(EnumMessage.ERROR.toString(), "Cliente já existe na base!", "");
		}
		
		clienteValidacao = clienteRepository.findByNumeroCnpj(cliente.getNumeroCnpj()).orElse(new Cliente());
		if(clienteValidacao.getCodigoCliente() != 0 && clienteValidacao.getCodigoCliente()!=cliente.getCodigoCliente()) {
			throw new NegocioException(EnumMessage.ERROR.toString(), "Número de CNPJ já cadastrado na base!", "");	
		}
		
		clienteValidacao = clienteRepository.findByNomeEmail(cliente.getNomeEmail()).orElse(new Cliente());
		if(clienteValidacao.getCodigoCliente() != 0 && clienteValidacao.getCodigoCliente()!=cliente.getCodigoCliente()) {
			throw new NegocioException(EnumMessage.ERROR.toString(), "E-mail já cadastrado na base!", "");
		} 
		
		clienteValidacao = clienteRepository.findByRazaoSocial(cliente.getRazaoSocial()).orElse(new Cliente());
		if(clienteValidacao.getCodigoCliente() != 0 && clienteValidacao.getCodigoCliente()!=cliente.getCodigoCliente()) {
			throw new NegocioException(EnumMessage.ERROR.toString(), "Razão Social já cadastrada na base!", "");
		}
	}
	
	@Override
	public List<Cliente> listarTodos(){ 
		Sort sortByCodCliente = Sort.by("codigoCliente").descending();
		return clienteRepository.findAll(sortByCodCliente);
	}
	
	@Override
	public Cliente alterarStatus(Cliente cliente) {
		cliente.setSituacaoCliente(cliente.getSituacaoCliente().equals(DefaultConstant.ATIVO) ? DefaultConstant.INATIVO : DefaultConstant.ATIVO);
		clienteRepository.save(cliente);
		return cliente;
	}
	
	@Override
	public Cliente porId(Long codigoCliente) {
		return clienteRepository.getReferenceById(codigoCliente);
	}
	
}
