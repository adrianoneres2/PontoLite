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

import com.octadata.pontolite.base.EnumMessage;
import com.octadata.pontolite.base.ModelMessage;
import com.octadata.pontolite.exception.NegocioException;
import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.service.AutenticacaoService;
import com.octadata.pontolite.service.ClienteService;

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
    public String processarFormularioCadastro(@ModelAttribute @Valid Cliente cliente, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
        	model.addAttribute("org.springframework.validation.BindingResult.cliente", bindingResult);
            return "/cliente/cadastro-cliente"; // Return to the form with errors
        }
        try{
        	/*Usuário que efetua o cadastro*/
			cliente.setUsuarioCadastro(autenticacaoService.getUsuarioAutenticado());
        	clienteService.salvar(cliente);
        	ModelMessage.setAttribute(model, EnumMessage.SUCCESS.toString(), "Cadastrado com sucesso!!");
        }catch (NegocioException e) {
        	ModelMessage.setAttribute(model, EnumMessage.ERROR.toString(),  e.getMessage());
		}
        return "/cliente/cadastro-cliente";
    }
  
    
    @GetMapping("/listar")
    public String acessarListagem(Model model) {
    	List<Cliente> clientes = clienteService.listarTodos();
    	 model.addAttribute("clientes", clientes);
    	return "/cliente/listagem-cliente";
    }
    
    @PostMapping("/alterar-status/{codigoCliente}")
    public String alterarStatus(@PathVariable Long codigoCliente) {
    	clienteService.alterarStatus(clienteService.porId(codigoCliente));
    	return "redirect:/pontolite/cliente/listar";
    }
    
    @GetMapping("/alterar")
    public String alterar(@RequestParam(name = "codCliente", required = true) Long codigoCliente, Model model) {
    	Cliente cliente = clienteService.porId(codigoCliente);
        model.addAttribute("cliente", cliente);
    	return "/cliente/alterar-cliente";
    }
    
    @PostMapping("/alterar")
    public String processarFormularioAlteracao(@ModelAttribute @Valid Cliente cliente, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
        	model.addAttribute("org.springframework.validation.BindingResult.cliente", bindingResult);
            return "/cliente/alterar-cliente"; // Return to the form with errors
        }
        try{
        	/*Usuário que efetua o cadastro*/
			//cliente.setUsuarioCadastro(autenticacaoService.getUsuarioAutenticado());
        	clienteService.alterar(cliente);
        	ModelMessage.setAttribute(model, EnumMessage.SUCCESS.toString(), "Alterado com sucesso!!");
        }catch (NegocioException e) {
        	ModelMessage.setAttribute(model, EnumMessage.ERROR.toString(),  e.getMessage());
		}
        return "/cliente/alterar-cliente";
    }
}
