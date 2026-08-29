package com.octadata.pontolite.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.octadata.pontolite.dto.RelatorioPontoDiaDTO;
import com.octadata.pontolite.exception.NegocioException;
import com.octadata.pontolite.model.RegistroPonto;
import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.service.AutenticacaoService;
import com.octadata.pontolite.service.RegistroPontoService;
import com.octadata.pontolite.service.UsuarioService;
import com.octadata.pontolite.util.EnumMessage;
import com.octadata.pontolite.util.ModelMessage;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("pontolite/ponto")
public class RegistroPontoController {

	private final AutenticacaoService autenticacaoService;

	@Autowired
	private RegistroPontoService registroPontoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private HttpSession session;

	RegistroPontoController(AutenticacaoService autenticacaoService) {
		this.autenticacaoService = autenticacaoService;
	}

	@GetMapping("registrarPonto")
	public String registrarPonto(Model model) {
		RegistroPonto registroPonto = new RegistroPonto();
		registroPonto.setUsuario(autenticacaoService.getUsuarioAutenticado());
		try {
			registroPontoService.salvar(registroPonto);
			ModelMessage.setAttribute(model, EnumMessage.SUCCESS.toString(), "Ponto registrado com sucesso!");
		} catch (NegocioException e) {
			ModelMessage.setAttribute(model, EnumMessage.ERROR.toString(), e.getMessage());
		}
		return "/ponto/registro-ponto";
	}

	@GetMapping("listar")
	public String listarPeriodoPorUsuario(Model model) {
		LocalDateTime hoje = LocalDateTime.now();
		LocalDateTime dataHoraFinal = LocalDateTime.parse(hoje.toString());
		LocalDateTime dataHoraIncial = dataHoraFinal.toLocalDate().atTime(LocalTime.MIN);
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		List<RegistroPonto> registros = registroPontoService.listarPeriodoPorUsuario(usuarioLogado, dataHoraIncial,
				dataHoraFinal);
		model.addAttribute("registros", registros);
		return "/ponto/listagem-ponto";
	}

	@GetMapping("listar-periodo-usuario")
	public String listarPeriodoPorUsuario(Model model,
			@RequestParam(name = "dataHoraInicial", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHoraInicial,
			@RequestParam(name = "dataHoraFinal", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHoraFinal,
			@RequestParam(name = "codigoUsuario", required = false) Long codigoUsuario) {

		if (dataHoraInicial == null) {
			dataHoraInicial = LocalDate.now().withDayOfMonth(1).atStartOfDay();
		}
		if (dataHoraFinal == null) {
			dataHoraFinal = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(LocalTime.MAX);
		}

		Usuario usuario = null;
		if (codigoUsuario != null) {
			usuario = usuarioService.porId(codigoUsuario);
		} else {
			usuario = (Usuario) session.getAttribute("usuarioLogado");
		}

		List<RegistroPonto> registros = registroPontoService.listarPeriodoPorUsuario(usuario, dataHoraInicial,
				dataHoraFinal);
		List<RelatorioPontoDiaDTO> relatorioDias = registroPontoService.montarRelatorioMensal(usuario, dataHoraInicial,
				dataHoraFinal);

		String mesAno = dataHoraInicial.format(DateTimeFormatter.ofPattern("MM/yyyy"));

		model.addAttribute("registros", registros);
		model.addAttribute("relatorioDias", relatorioDias);
		model.addAttribute("dataHoraInicial", dataHoraInicial);
		model.addAttribute("dataHoraFinal", dataHoraFinal);
		model.addAttribute("mesAnoAtual", mesAno);
		model.addAttribute("usuarioRelatorio", usuario);

		return "/ponto/listagem-ponto-periodo";
	}

	@PostMapping("alterar")
	public String alterarPonto(Model model,
			@RequestParam(name = "codigoRegistroPonto", required = true) Long codigoRegistroPonto,
			@RequestParam(name = "dataHora", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHora,
			@RequestParam(name = "dataHoraInicial", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHoraInicial,
			@RequestParam(name = "dataHoraFinal", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHoraFinal,
			@RequestParam(name = "codigoUsuario", required = false) Long codigoUsuario) {

		try {
			registroPontoService.alterarHorario(codigoRegistroPonto, dataHora);
			ModelMessage.setAttribute(model, EnumMessage.SUCCESS.toString(), "Horário de ponto alterado com sucesso!");
		} catch (NegocioException e) {
			ModelMessage.setAttribute(model, EnumMessage.ERROR.toString(), e.getMessage());
		} catch (Exception e) {
			ModelMessage.setAttribute(model, EnumMessage.ERROR.toString(), "Erro ao alterar registro de ponto: " + e.getMessage());
		}

		return listarPeriodoPorUsuario(model, dataHoraInicial, dataHoraFinal, codigoUsuario);
	}
}

