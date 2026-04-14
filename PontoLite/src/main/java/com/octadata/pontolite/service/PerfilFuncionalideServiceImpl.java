package com.octadata.pontolite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.model.Perfil;
import com.octadata.pontolite.model.PerfilFuncionalidade;
import com.octadata.pontolite.model.Usuario;
import com.octadata.pontolite.repository.PerfilFuncionalidadeRepository;

import jakarta.transaction.Transactional;

@Service
public class PerfilFuncionalideServiceImpl implements PerfilFuncionalidadeService {

    @Autowired
    private PerfilFuncionalidadeRepository perfilFuncionalidadeRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PerfilService perfilService;

    @Override
    @Transactional
    public void salvar(List<PerfilFuncionalidade> perfilFuncionalidades, Long codigoCliente) {
        Cliente cliente = clienteService.porId(codigoCliente);

        perfilFuncionalidadeRepository.deleteByCliente(cliente);
        perfilFuncionalidadeRepository.saveAll(perfilFuncionalidades);
    }

    @Override
    public List<PerfilFuncionalidade> buscarPorClienteAndPerfil(Long codigoCliente, Long codigoPerfil) {
        Cliente cliente = clienteService.porId(codigoCliente);
        Perfil perfil = perfilService.porId(codigoPerfil);
        return perfilFuncionalidadeRepository.findByClienteAndPerfil(cliente, perfil);
    }

    @Override
    public List<PerfilFuncionalidade> buscarPorCliente(Long codigoCliente) {
        Cliente cliente = clienteService.porId(codigoCliente);
        return perfilFuncionalidadeRepository.findByCliente(cliente);
    }

    @Override
    public boolean isAdministradorOctadata(Usuario usuario) {
        return this.buscarPorClienteAndPerfil(usuario.getCliente().getCodigoCliente(),
                usuario.getPerfil().getCodigoPerfil()).stream()
                .filter(pf -> pf.getFuncionalidade().getCodigoFuncionalidade() == 0).findFirst().isPresent();
    }

}