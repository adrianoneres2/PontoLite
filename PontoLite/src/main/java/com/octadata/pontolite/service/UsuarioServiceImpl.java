package com.octadata.pontolite.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.ScrollPosition.Direction;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.exception.NegocioException;
import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.repository.UsuarioRepository;
import com.octadata.pontolite.util.DefaultConstant;
import com.octadata.pontolite.util.EnumMessage;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final PerfilServiceImpl perfilServiceImpl;

	UsuarioServiceImpl(UsuarioRepository usuarioRepository, PerfilServiceImpl perfilServiceImpl) {
		this.usuarioRepository = usuarioRepository;
		this.perfilServiceImpl = perfilServiceImpl;
	}

	@Override
	public Usuario salvar(Usuario usuario) {
		validar(usuario);
		return usuarioRepository.save(preparaCadastro(usuario));
	}

	/*
	 * Método para criar automaticamente o usuário administrador default do cliente.
	 * 
	 * @param: cliente. Objeto do cliente associado ao novo usuário.
	 * 
	 * @return: usuarioPadrao. Objeto de usuário.
	 */
	@Override
	public Usuario criarUsuarioPadrao(Cliente cliente) {
		Usuario usuarioPadrao = new Usuario();
		usuarioPadrao.setUsername(cliente.getNomeEmail());
		usuarioPadrao.setEmail(cliente.getNomeEmail());
		usuarioPadrao.setPassword(criptografarSenha("12345"));
		usuarioPadrao.setStatus(DefaultConstant.ATIVO);
		usuarioPadrao.setDataCadastro(new Date());
		usuarioPadrao.setUsuarioCadastro(cliente.getUsuarioCadastro());
		usuarioPadrao.setPerfil(perfilServiceImpl.porNome("Administrador"));
		usuarioPadrao.setCliente(cliente);
		return usuarioPadrao;
	}

	public Usuario preparaCadastro(Usuario usuario) {
		usuario.setStatus(DefaultConstant.ATIVO);
		usuario.setDataCadastro(new Date());
		usuario.setPassword(criptografarSenha(usuario.getPassword()));
		return usuario;
	}

	@Override
	public List<Usuario> findAllByCliente(Cliente cliente) {
		return usuarioRepository.findAllByCliente(cliente);
	}

	@Override
	public Page<Usuario> findAllByClientePaged(Cliente cliente, int pagina, int tamanho) {
		Pageable pageable = PageRequest.of(pagina, tamanho);
		return usuarioRepository.findAllByCliente(cliente, pageable);
	}

	@Override
	public Usuario porId(Long codigoUsurio) {
		return usuarioRepository.getReferenceById(codigoUsurio);
	}

	@Override
	public Usuario updateStatus(Usuario usuario) {
		usuario.setStatus(
				usuario.getStatus().equals(DefaultConstant.ATIVO) ? DefaultConstant.INATIVO : DefaultConstant.ATIVO);
		usuarioRepository.save(usuario);
		return usuario;
	}

	@Override
	public Usuario alterar(Usuario usuario) throws NegocioException {
		validar(usuario);
		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario alterarSenha(Usuario usuario) throws NegocioException {
		usuario.setPassword(criptografarSenha(usuario.getPassword()));
		return usuarioRepository.save(usuario);
	}

	protected void validar(Usuario usuario) {
		Usuario usuarioValidacao = usuarioRepository.findByUsername(usuario.getUsername()).orElse(new Usuario());
		if (usuarioValidacao.getCodigoUsuario() != null
				&& !usuarioValidacao.getCodigoUsuario().equals(usuario.getCodigoUsuario())) {
			throw new NegocioException(EnumMessage.ERROR.toString(), "Usuário já existe na base!", "");
		}

		usuarioValidacao = usuarioRepository.findByEmail(usuario.getEmail()).orElse(new Usuario());
		if (usuarioValidacao.getCodigoUsuario() != null
				&& !usuarioValidacao.getCodigoUsuario().equals(usuario.getCodigoUsuario())) {
			throw new NegocioException(EnumMessage.ERROR.toString(), "E-mail já cadastrado na base!", "");
		}
	}

	public String criptografarSenha(String senha) {
		return new BCryptPasswordEncoder().encode(senha);
	}

}
