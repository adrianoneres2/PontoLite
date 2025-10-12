package com.octadata.pontolite.service;
import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.base.DefaultConstant;
import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository usuarioRepository;
    private final PerfilServiceImpl perfilServiceImpl;
    
    ///@Autowired
   // ClienteService clienteService;

    UsuarioServiceImpl(UsuarioRepository usuarioRepository, PerfilServiceImpl perfilServiceImpl) {
        this.usuarioRepository = usuarioRepository;
        this.perfilServiceImpl = perfilServiceImpl;
    }
	
    @Override
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
    
	/*
	 * Método para criar automaticamente o usuário administrador default do cliente.
	 * @param: cliente. Objeto do cliente associado ao novo usuário.
	 * @return: usuarioPadrao. Objeto de usuário.
	 * */
    @Override
	public Usuario criarUsuarioPadrao(Cliente cliente) {
		Usuario usuarioPadrao = new Usuario();
		usuarioPadrao.setUsername(cliente.getNomeEmail());
		usuarioPadrao.setEmail(cliente.getNomeEmail());
		usuarioPadrao.setPassword(new BCryptPasswordEncoder().encode("12345"));
		usuarioPadrao.setStatus(DefaultConstant.ATIVO);
		usuarioPadrao.setDataCadastro(new Date());
		usuarioPadrao.setUsuarioCadastro(cliente.getUsuarioCadastro());
		usuarioPadrao.setPerfil(perfilServiceImpl.porNome("Administrador"));
		usuarioPadrao.setCliente(cliente);
		return usuarioPadrao;
	}
     
    @Override
    public List<Usuario> findAllByCliente(Cliente cliente){
    	return usuarioRepository.findAllByCliente(cliente);
    }
    
    @Override
    public Usuario getById(Long codigoUsurio) {
    	return usuarioRepository.getReferenceById(codigoUsurio);
    }
    
    @Override
    public Usuario updateStatus(Usuario usuario) {
    	usuario.setStatus(usuario.getStatus().equals(DefaultConstant.ATIVO) ? DefaultConstant.INATIVO : DefaultConstant.ATIVO);
    	usuarioRepository.save(usuario);
    	return usuario;
    }
}
