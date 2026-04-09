package com.octadata.pontolite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.octadata.pontolite.exception.NegocioException;
import com.octadata.pontolite.model.Jornada;
import com.octadata.pontolite.model.Perfil;
import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.service.AutenticacaoService;
import com.octadata.pontolite.service.ClienteService;
import com.octadata.pontolite.service.JornadaService;
import com.octadata.pontolite.service.PerfilService;
import com.octadata.pontolite.service.UsuarioService;
import com.octadata.pontolite.util.ClienteHelper;
import com.octadata.pontolite.util.EnumMessage;
import com.octadata.pontolite.util.ModelMessage;

import jakarta.validation.Valid;

@Controller
@RequestMapping("pontolite/usuario")
public class UsuarioContoller {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	ClienteService clienteService;

	@Autowired
	AutenticacaoService autenticacaoService;

	@Autowired
	PerfilService perfilService;

	@Autowired
	JornadaService jornadaService;

	@GetMapping("/formulario")
	public String acessarFormularioCadastroUsuario(Model model) {
		List<Perfil> perfis = perfilService.findAll();
		List<Jornada> jornadas = jornadaService.porCliente(autenticacaoService.getUsuarioAutenticado().getCliente());
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("perfis", perfis);
		model.addAttribute("clientes", clienteService.listarTodos());
		model.addAttribute("jornadas", jornadas);

		return "/usuario/cadastro-usuario";
	}

	@PostMapping("/formulario")
	public String processarFormularioCadastro(@ModelAttribute @Valid Usuario usuario, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("org.springframework.validation.BindingResult.usuario", bindingResult);
			return "/usuario/cadastro-usuario"; // Return to the form with errors
		}
		try {
			/* Usuário que efetua o cadastro */
			usuario.setUsuarioCadastro(autenticacaoService.getUsuarioAutenticado());
			usuario.setCliente(ClienteHelper.getClienteSelecionado(usuario.getCliente(), autenticacaoService));
			usuarioService.salvar(usuario);
			ModelMessage.setAttribute(model, EnumMessage.SUCCESS.toString(), "Cadastrado com sucesso!!");
		} catch (NegocioException e) {
			ModelMessage.setAttribute(model, EnumMessage.ERROR.toString(), e.getMessage());
		}
		return "/usuario/cadastro-usuario";
	}

	@GetMapping("/listar")
	public String listar(@RequestParam(name = "codCliente", required = true, defaultValue = "0") Long codigoCliente,
			Model model) {
		codigoCliente = ClienteHelper.getCodigoClienteSelecionado(codigoCliente, autenticacaoService);
		atualizarListaUsuarioPorCliente(model, codigoCliente);
		return "/usuario/listagem-usuario";
	}

	@PostMapping("/alterar-status/{codigoUsuario}")
	public String alterarStatus(@PathVariable Long codigoUsuario, Model model) {
		Usuario usuario = usuarioService.porId(codigoUsuario);
		usuarioService.updateStatus(usuario);
		atualizarListaUsuarioPorCliente(model, usuario.getCliente().getCodigoCliente());
		return "/usuario/listagem-usuario";
	}

	protected void atualizarListaUsuarioPorCliente(Model model, Long codigoCliente) {
		List<Usuario> usuarios = usuarioService.findAllByCliente(clienteService.porId(codigoCliente));
		model.addAttribute("usuarios", usuarios);
	}

	@GetMapping("/alterar")
	public String alterar(@RequestParam(name = "codUsuario", required = true) Long codigoUsuario, Model model) {
		Usuario usuario = usuarioService.porId(codigoUsuario);
		List<Perfil> perfis = perfilService.findAll();
		List<Jornada> jornadas = jornadaService.porCliente(autenticacaoService.getUsuarioAutenticado().getCliente());
		model.addAttribute("perfis", perfis);
		model.addAttribute("clientes", clienteService.listarTodos());
		model.addAttribute("jornadas", jornadas);
		model.addAttribute("usuario", usuario);
		return "/usuario/alterar-usuario";
	}

	@PostMapping("/alterar")
	public String processarFormularioAlteracao(@ModelAttribute @Valid Usuario usuario, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("org.springframework.validation.BindingResult.usuario", bindingResult);
			return "/usuario/alterar-usuario"; // Return to the form with errors
		}
		try {
			/* Usuário que efetua o cadastro */
			// cliente.setUsuarioCadastro(autenticacaoService.getUsuarioAutenticado());
			usuarioService.alterar(usuario);
			ModelMessage.setAttribute(model, EnumMessage.SUCCESS.toString(), "Alterado com sucesso!!");
		} catch (NegocioException e) {
			ModelMessage.setAttribute(model, EnumMessage.ERROR.toString(), e.getMessage());
		}
		return "/usuario/alterar-usuario";
	}

	@PostMapping("/formulario-alterar-senha/{codigoUsuario}")
	public String formularioAlterarSenha(@PathVariable Long codigoUsuario, Model model) {
		Usuario usuario = usuarioService.porId(codigoUsuario);
		usuario.setPassword(null);
		model.addAttribute("usuario", usuario);
		return "/usuario/alterar-senha";
	}

	@PostMapping("/alterar-senha")
	public String alterarSenha(@ModelAttribute @Valid Usuario usuario, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("org.springframework.validation.BindingResult.usuario", bindingResult);
			return "/usuario/alterar-senha"; // Return to the form with errors
		}
		try {
			usuarioService.alterarSenha(usuario);
			ModelMessage.setAttribute(model, EnumMessage.SUCCESS.toString(), "Alterado com sucesso!!");
		} catch (NegocioException e) {
			ModelMessage.setAttribute(model, EnumMessage.ERROR.toString(), e.getMessage());
		}
		return "/usuario/alterar-senha";
	}

}
