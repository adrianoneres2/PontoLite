package com.octadata.pontolite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.service.AutenticacaoService;
import com.octadata.pontolite.service.ClienteService;
import com.octadata.pontolite.util.DefaultConstant;
import com.octadata.pontolite.util.EnumMessage;
import com.octadata.pontolite.util.ModelMessage;

import jakarta.validation.Valid;

@Controller
@RequestMapping("pontolite/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @GetMapping("/formulario")
    public String acessarFormularioCadastro(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "/cliente/cadastro-cliente";
    }

    @PostMapping("/formulario")
    public String processarFormularioCadastro(@ModelAttribute @Valid Cliente cliente, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.cliente", bindingResult);
            return "/cliente/cadastro-cliente"; // Return to the form with errors
        }
        try {
            /* Usuário que efetua o cadastro */
            cliente.setUsuarioCadastro(autenticacaoService.getUsuarioAutenticado());
            clienteService.salvar(cliente);
            ModelMessage.setAttribute(model, EnumMessage.SUCCESS.toString(), "Cadastrado com sucesso!!");
        } catch (NegocioException e) {
            ModelMessage.setAttribute(model, EnumMessage.ERROR.toString(), e.getMessage());
        }
        return "/cliente/cadastro-cliente";
    }

    @GetMapping("/listar-por-nome")
    public String acessarListagemPorNome(Model model, @PageableDefault(size = 8) Pageable pageable,
            @RequestParam(name = "nomeCliente") String nomeCliente) {

        Page<Cliente> clientesPage = Page.empty();
        if (!nomeCliente.isBlank()) {
            clientesPage = clienteService.listarPorNomePaginado(pageable.getPageNumber(),
                    pageable.getPageSize(), nomeCliente);
        } else {
            clientesPage = clienteService.listarTodosPaginado(pageable.getPageNumber(),
                    pageable.getPageSize());
        }

        model.addAttribute("clientesPage", clientesPage);
        model.addAttribute("clientes", clientesPage.getContent());
        /// Para manter o parametro de busca no campo input search no formulario
        model.addAttribute("nomeCliente", nomeCliente);
        return "/cliente/listagem-cliente";
    }

    @GetMapping("/listar")
    public String acessarListagem(Model model,
            @RequestParam(value = "page", defaultValue = DefaultConstant.TAMANHO_PAGINA_PADRAO) int page,
            @RequestParam(value = "size", defaultValue = DefaultConstant.REGISTROS_POR_PAGINA_PADRAO) int size) {
        Page<Cliente> clientesPage = Page.empty();

        model.addAttribute("clientesPage", clientesPage);
        model.addAttribute("clientes", clientesPage.getContent());

        return "/cliente/listagem-cliente";
    }

    @GetMapping("/alterar-status")
    public String alterarStatus(@RequestParam(name = "codCliente", required = true) Long codigoCliente,
            @RequestParam(value = "page", defaultValue = DefaultConstant.TAMANHO_PAGINA_PADRAO) int page,
            @RequestParam(value = "size", defaultValue = DefaultConstant.REGISTROS_POR_PAGINA_PADRAO) int size,
            Model model) {
        clienteService.alterarStatus(clienteService.porId(codigoCliente));
        return "/cliente/listagem-cliente";
    }

    @GetMapping("/alterar")
    public String alterar(@RequestParam(name = "codCliente", required = true) Long codigoCliente, Model model) {
        Cliente cliente = clienteService.porId(codigoCliente);
        model.addAttribute("cliente", cliente);
        return "/cliente/alterar-cliente";
    }

    @PostMapping("/alterar")
    public String processarFormularioAlteracao(@ModelAttribute @Valid Cliente cliente, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.cliente", bindingResult);
            return "/cliente/alterar-cliente"; // Return to the form with errors
        }
        try {
            /* Usuário que efetua o cadastro */
            // cliente.setUsuarioCadastro(autenticacaoService.getUsuarioAutenticado());
            clienteService.alterar(cliente);
            ModelMessage.setAttribute(model, EnumMessage.SUCCESS.toString(), "Alterado com sucesso!!");
        } catch (NegocioException e) {
            ModelMessage.setAttribute(model, EnumMessage.ERROR.toString(), e.getMessage());
        }
        return "/cliente/alterar-cliente";
    }
}
