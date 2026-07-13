package com.octadata.pontolite.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.octadata.pontolite.dto.JornadaDTO;
import com.octadata.pontolite.model.Jornada;
import com.octadata.pontolite.service.AutenticacaoService;
import com.octadata.pontolite.service.ClienteService;
import com.octadata.pontolite.service.JornadaService;
import com.octadata.pontolite.service.TipoEscalaService;
import com.octadata.pontolite.util.ClienteHelper;
import com.octadata.pontolite.util.EnumMessage;
import com.octadata.pontolite.util.ModelMessage;
import com.octadata.pontolite.util.DefaultConstant;
import org.springframework.data.domain.PageRequest;

@Controller
@RequestMapping("pontolite/jornada")
public class JornadaController {

	@Autowired
	JornadaService jornadaService;

	@Autowired
	ClienteService clienteService;

	@Autowired
	AutenticacaoService autenticacaoService;

	@Autowired
	TipoEscalaService tipoEscalaService;

	@GetMapping("/formulario")
	public String acessarFormularioJornada(Model model) {

		JornadaDTO jornadaDTO = new JornadaDTO(null, null, null, null, null, null, null);
		model.addAttribute("jornadaDTO", jornadaDTO);
		model.addAttribute("clientes", clienteService.listarTodos());
		model.addAttribute("tiposEscala", tipoEscalaService.listarTodos());
		return "/jornada/cadastro-jornada";
	}

	@PostMapping("/formulario")
	public String salvarJornada(JornadaDTO jornadaDTO, Model model) {
		Jornada jornada = new Jornada();
		jornada = jornadaDTO.toJornada(jornadaDTO);
		jornada.setUsuarioCadastro(autenticacaoService.getUsuarioAutenticado());
		jornada.setCliente(ClienteHelper.getClienteSelecionado(jornada.getCliente(), autenticacaoService));
		jornada.setTipoEscala(tipoEscalaService.buscarPorId(jornadaDTO.tipoEscala().getCodigoTipoEscala()));
		jornadaService.salvar(jornada);
		ModelMessage.setAttribute(model, EnumMessage.SUCCESS.toString(), "Processado com sucesso!!");
		return "/jornada/cadastro-jornada";
	}

	@GetMapping("/listar")
	public String listarJornadas(
			@RequestParam(value = "page", defaultValue = DefaultConstant.TAMANHO_PAGINA_PADRAO) int page,
			@RequestParam(value = "size", defaultValue = DefaultConstant.REGISTROS_POR_PAGINA_PADRAO) int size,
			Model model) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Jornada> jornadasPage = jornadaService.porClientePaginado(autenticacaoService.getUsuarioAutenticado().getCliente(),
				pageRequest);
		model.addAttribute("jornadasPage", jornadasPage);
		model.addAttribute("jornadas", jornadasPage.getContent());
		return "/jornada/listagem-jornada";
	}

	@GetMapping("/quadro-horarios")
	public String acessarQuadroHorarios(Model model) {
		return "/jornada/quadro-horarios";
	}

	@GetMapping("/alterar-status")
	public String alterarStatusJornada(@RequestParam(name = "codJornada", required = true) Long codJornada,
			Model model) {
		Jornada jornada = jornadaService.buscarPorId(codJornada);
		jornadaService.alterarStatus(jornada);
		return "redirect:/pontolite/jornada/listar";
	}

	@GetMapping("/alterar")
	public String acessarFormularioAlteracaoJornada(@RequestParam(name = "codJornada", required = true) Long codJornada,
			Model model) {

		Jornada jornada = jornadaService.buscarPorId(codJornada);
		JornadaDTO jornadaDTO = new JornadaDTO();
		jornadaDTO = jornadaDTO.fromJornada(jornada);
		model.addAttribute("jornadaDTO", jornadaDTO);
		model.addAttribute("clientes", clienteService.listarTodos());
		model.addAttribute("tiposEscala", tipoEscalaService.listarTodos());
		return "/jornada/alteracao-jornada";
	}

}
