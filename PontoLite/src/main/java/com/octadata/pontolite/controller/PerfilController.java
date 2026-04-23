package com.octadata.pontolite.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.octadata.pontolite.dto.PerfilFuncionalidadeDTO;
import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.model.PerfilFuncionalidade;
import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.repository.PerfilFuncionalidadeRepository;
import com.octadata.pontolite.service.AutenticacaoService;
import com.octadata.pontolite.service.ClienteService;
import com.octadata.pontolite.service.FuncionalidadeService;
import com.octadata.pontolite.service.PerfilFuncionalidadeService;
import com.octadata.pontolite.service.PerfilService;
import com.octadata.pontolite.util.ClienteHelper;

@Controller
@RequestMapping("pontolite/perfil")
public class PerfilController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private FuncionalidadeService funcionalidadeService;

    @Autowired
    private PerfilFuncionalidadeRepository perfilFuncionalidadeRepository;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private PerfilFuncionalidadeService perfilFuncionalidadeService;

    @GetMapping("/acessar-formulario")
    public String cadastroPerfil(Model model) {

        Usuario usuarioAutenticado = autenticacaoService.getUsuarioAutenticado();

        if (perfilFuncionalidadeService.isAdministradorOctadata(usuarioAutenticado)) {
            model.addAttribute("clientes", clienteService.listarTodos());
        } else {
            model.addAttribute("clientes", clienteService.listarTodos().stream()
                    .filter(c -> c.getCodigoCliente() == usuarioAutenticado.getCliente()
                            .getCodigoCliente())
                    .collect(Collectors.toList()));
        }
        model.addAttribute("perfis", perfilService.findAll());
        model.addAttribute("funcionalidades", funcionalidadeService.findAll());
        return "/perfil/cadastro-perfil";
    }

    @GetMapping("/api/listar-configuracoes")
    @ResponseBody
    public List<PerfilFuncionalidadeDTO> listarConfiguracoes(
            @RequestParam(name = "codCliente", required = false, defaultValue = "0") Long codigoCliente) {
        codigoCliente = ClienteHelper.getCodigoClienteSelecionado(codigoCliente, autenticacaoService);
        Cliente cliente = clienteService.porId(codigoCliente);

        List<PerfilFuncionalidade> list = perfilFuncionalidadeRepository.findByCliente(cliente);
        return list.stream().map(pf -> new PerfilFuncionalidadeDTO(
                pf.getPerfil().getCodigoPerfil(),
                pf.getPerfil().getNomePerfil(),
                pf.getFuncionalidade().getCodigoFuncionalidade(),
                pf.getFuncionalidade().getNomeFuncionalidade())).collect(Collectors.toList());
    }

    @PostMapping("/api/salvar-configuracao")
    @ResponseBody
    public ResponseEntity<?> salvarConfiguracao(
            @RequestParam(name = "codCliente", required = false, defaultValue = "0") Long codigoCliente,
            @RequestBody List<PerfilFuncionalidadeDTO> dtos) {
        codigoCliente = ClienteHelper.getCodigoClienteSelecionado(codigoCliente, autenticacaoService);
        Cliente cliente = clienteService.porId(codigoCliente);

        List<PerfilFuncionalidade> perfilFuncionalidades = new PerfilFuncionalidadeDTO().toPerfilFuncionalidade(dtos,
                cliente);
        perfilFuncionalidadeService.salvar(perfilFuncionalidades, codigoCliente);

        return ResponseEntity.ok().build();
    }

}
