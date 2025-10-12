package com.octadata.pontolite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.service.ClienteService;
import com.octadata.pontolite.service.UsuarioService;

@Controller
@RequestMapping("pontolite/usuario")
public class UsuarioContoller {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ClienteService clienteService;
	
	@GetMapping("/listar")
	public String listar(@RequestParam(name = "codCliente", required = true) Long codigoCliente, Model model) {
		atualizarListaUsuarioPorCliente(model, codigoCliente);
		return "/usuario/listagem-usuario";
	}
	
    @PostMapping("/alterar-status/{codigoUsuario}")
    public String alterarStatus(@PathVariable Long codigoUsuario, Model model) {
    	Usuario usuario = usuarioService.getById(codigoUsuario);
    	usuarioService.updateStatus(usuario);
    	atualizarListaUsuarioPorCliente(model, usuario.getCliente().getCodigoCliente());
    	return "/usuario/listagem-usuario";
    }
    
    protected void atualizarListaUsuarioPorCliente(Model model, Long codigoCliente) {
		List<Usuario> usuarios = usuarioService.findAllByCliente(clienteService.porId(codigoCliente));
		model.addAttribute("usuarios", usuarios);
    }
}
