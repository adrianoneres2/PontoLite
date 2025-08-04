package com.octadata.pontolite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octadata.pontolite.base.Message;
import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.service.ClienteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("pontolite/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
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
        Message message = new Message();
        message =  clienteService.salvar(cliente);
    	model.addAttribute("codeMessage", message.getCode());
    	model.addAttribute("msg", message.getMessage());
        return "/cliente/cadastro-cliente";
    }
	
}
