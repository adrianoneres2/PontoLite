package com.octadata.pontolite.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.octadata.pontolite.dto.JornadaResponseDTO;
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
import com.octadata.pontolite.util.DefaultConstant;
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
		List<Jornada> jornadas = listarJornadaPorCliente(
				autenticacaoService.getUsuarioAutenticado().getCliente().getCodigoCliente());
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
			@RequestParam(value = "page", defaultValue = DefaultConstant.TAMANHO_PAGINA_PADRAO) int page,
			@RequestParam(value = "size", defaultValue = DefaultConstant.REGISTROS_POR_PAGINA_PADRAO) int size,
			Model model) {
		codigoCliente = ClienteHelper.getCodigoClienteSelecionado(codigoCliente, autenticacaoService);
		atualizarListaUsuarioPorCliente(model, codigoCliente, page, size);
		return "/usuario/listagem-usuario";
	}

	@GetMapping("/alterar-status")
	public String alterarStatus(@RequestParam(name = "codUsuario", required = true) Long codigoUsuario,
			@RequestParam(value = "page", defaultValue = DefaultConstant.TAMANHO_PAGINA_PADRAO) int page,
			@RequestParam(value = "size", defaultValue = DefaultConstant.REGISTROS_POR_PAGINA_PADRAO) int size,
			Model model) {
		Usuario usuario = usuarioService.porId(codigoUsuario);
		usuarioService.updateStatus(usuario);
		atualizarListaUsuarioPorCliente(model, usuario.getCliente().getCodigoCliente(), page, size);
		return "/usuario/listagem-usuario";
	}

	protected void atualizarListaUsuarioPorCliente(Model model, Long codigoCliente, int page, int size) {
		Page<Usuario> usuariosPage = usuarioService.findAllByClientePaged(clienteService.porId(codigoCliente), page,
				size);
		model.addAttribute("usuariosPage", usuariosPage);
		model.addAttribute("usuarios", usuariosPage.getContent());
		model.addAttribute("codCliente", codigoCliente);
	}

	@GetMapping("/alterar")
	public String alterar(@RequestParam(name = "codUsuario", required = true) Long codigoUsuario, Model model) {
		Usuario usuario = usuarioService.porId(codigoUsuario);
		List<Perfil> perfis = perfilService.findAll();
		List<Jornada> jornadas = listarJornadaPorCliente(usuario.getCliente().getCodigoCliente());
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

	@GetMapping("/formulario-alterar-senha")
	public String formularioAlterarSenha(@RequestParam(name = "codigoUsuario", required = true) Long codigoUsuario,
			Model model) {
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

	public List<Jornada> listarJornadaPorCliente(
			@RequestParam(name = "codigoCliente", required = true) Long codigoCliente) {
		return jornadaService.porCliente(clienteService.porId(codigoCliente));
	}

	@GetMapping("/api/listar-jornada-cliente")
	@ResponseBody
	public List<JornadaResponseDTO> listarJornadaPorClienteApi(
			@RequestParam(name = "codigoCliente", required = true) Long codigoCliente) {
		List<Jornada> jornadas = jornadaService.porCliente(clienteService.porId(codigoCliente));
		return jornadas.stream().map(j -> new JornadaResponseDTO(j.getCodigoJornada(), j.getNomeJornada()))
				.collect(Collectors.toList());
	}
}
