package com.octadata.pontolite.dto;

import java.util.ArrayList;
import java.util.List;

import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.model.Funcionalidade;
import com.octadata.pontolite.model.Perfil;
import com.octadata.pontolite.model.PerfilFuncionalidade;

public class PerfilFuncionalidadeDTO {

    private Long idPerfil;
    private String nomePerfil;
    private Long idFuncionalidade;
    private String nomeFuncionalidade;

    public PerfilFuncionalidadeDTO() {
    }

    public PerfilFuncionalidadeDTO(Long idPerfil, String nomePerfil, Long idFuncionalidade, String nomeFuncionalidade) {
        super();
        this.idPerfil = idPerfil;
        this.nomePerfil = nomePerfil;
        this.idFuncionalidade = idFuncionalidade;
        this.nomeFuncionalidade = nomeFuncionalidade;
    }

    public List<PerfilFuncionalidade> toPerfilFuncionalidade(List<PerfilFuncionalidadeDTO> dtos, Cliente cliente) {
        List<PerfilFuncionalidade> perfilFuncionalidades = new ArrayList<>();
        for (PerfilFuncionalidadeDTO dto : dtos) {
            PerfilFuncionalidade pf = new PerfilFuncionalidade();
            pf.setCliente(cliente);

            Perfil p = new Perfil();
            p.setCodigoPerfil(dto.getIdPerfil());
            pf.setPerfil(p);

            Funcionalidade f = new Funcionalidade();
            f.setCodigoFuncionalidade(dto.getIdFuncionalidade());
            pf.setFuncionalidade(f);

            perfilFuncionalidades.add(pf);
        }
        return perfilFuncionalidades;
    }

    public Long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNomePerfil() {
        return nomePerfil;
    }

    public void setNomePerfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }

    public Long getIdFuncionalidade() {
        return idFuncionalidade;
    }

    public void setIdFuncionalidade(Long idFuncionalidade) {
        this.idFuncionalidade = idFuncionalidade;
    }

    public String getNomeFuncionalidade() {
        return nomeFuncionalidade;
    }

    public void setNomeFuncionalidade(String nomeFuncionalidade) {
        this.nomeFuncionalidade = nomeFuncionalidade;
    }

}
