package com.octadata.pontolite.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.exception.NegocioException;
import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.model.Usuario;

@Service
public interface UsuarioService {

	/// Usuario getById(Long codigoUsuario);
	Usuario salvar(Usuario usuario);

	Usuario criarUsuarioPadrao(Cliente cliente);

	List<Usuario> findAllByCliente(Cliente cliente);

	Usuario updateStatus(Usuario usuario);

	Usuario alterar(Usuario usuario) throws NegocioException;

	Usuario porId(Long codigoUsuario);

	Usuario alterarSenha(Usuario usuario) throws NegocioException;

	Page<Usuario> findAllByClientePaged(Cliente cliente, int pagina, int tamanho);

}
