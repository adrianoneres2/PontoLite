package com.octadata.pontolite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.PerfilFuncionalidade;
import com.octadata.pontolite.model.Usuario;

@Service
public interface PerfilFuncionalidadeService {
    void salvar(List<PerfilFuncionalidade> perfilFuncionalidades, Long codigoCliente);

    List<PerfilFuncionalidade> buscarPorClienteAndPerfil(Long codigoCliente, Long codigoPerfil);

    List<PerfilFuncionalidade> buscarPorCliente(Long codigoCliente);

    boolean isAdministradorOctadata(Usuario usuario);
}
