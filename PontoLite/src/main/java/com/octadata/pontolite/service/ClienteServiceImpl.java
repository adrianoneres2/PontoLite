package com.octadata.pontolite.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.base.EnumMessage;
import com.octadata.pontolite.exception.NegocioException;
import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.repository.ClienteRepository;

import jakarta.transaction.Transactional;


@Service
public class ClienteServiceImpl implements ClienteService {

   // private final UsuarioRepository usuarioRepository;

    private final PerfilServiceImpl perfilServiceImpl;

    private final UsuarioService usuarioService;

	@Autowired
	private ClienteRepository clienteRepository;

    ClienteServiceImpl(UsuarioService usuarioService, PerfilServiceImpl perfilServiceImpl /*, UsuarioRepository usuarioRepository*/) {
        this.usuarioService = usuarioService;
        this.perfilServiceImpl = perfilServiceImpl;
        ///this.usuarioRepository = usuarioRepository;
    }
    
	@Override
	@Transactional
	public Cliente salvar(Cliente cliente) throws NegocioException{
		validar(cliente);
		cliente.setUsuarioCadastro(usuarioPadrao(cliente));
		cliente = clienteRepository.save(cliente);
		///usuarioService.salvar(usuarioPadrao(cliente));
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
		cliente.setSituacaoCliente(cliente.getSituacaoCliente().equals(1L) ? 2L : 1L);
		clienteRepository.save(cliente);
		return cliente;
	}
	
	@Override
	public Cliente porId(Long codigoCliente) {
		return clienteRepository.getReferenceById(codigoCliente);
	}
	
	/*
	 * Método para criar automaticamente o usuário administrador default do cliente.
	 * @param: cliente. Objeto do cliente associado ao novo usuário.
	 * @return: usuarioPadrao. Objeto de usuário.
	 * */
	protected Usuario usuarioPadrao(Cliente cliente) {
		Usuario usuarioPadrao = new Usuario();
		usuarioPadrao.setUsername(cliente.getNomeEmail());
		usuarioPadrao.setEmail(cliente.getNomeEmail());
		usuarioPadrao.setPassword(new BCryptPasswordEncoder().encode("12345"));
		usuarioPadrao.setSituacaoUsuario(1L);
		usuarioPadrao.setDataCadastro(new Date());
		usuarioPadrao.setUsuarioCadastro(cliente.getUsuarioCadastro());
		usuarioPadrao.setPerfil(perfilServiceImpl.porNome("Administrador"));
		usuarioPadrao.setCliente(cliente);
		return usuarioPadrao;
	}
	
}
