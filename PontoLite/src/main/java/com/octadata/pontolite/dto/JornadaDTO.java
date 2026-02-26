package com.octadata.pontolite.dto;

import java.sql.Date;
import java.util.List;

import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.model.Jornada;
import com.octadata.pontolite.model.JornadaDataHora;
import jakarta.validation.constraints.NotNull;

public record JornadaDTO(
        @NotNull String nomeJornada,
        Long situacaoJornada,
        @NotNull Long codigoJornada,
        Date dataCriacao,
        @NotNull Cliente cliente,
        @NotNull List<JornadaDataHoraDTO> listaJornadaDataHora) {

    public JornadaDTO() {
        this(null, null, null, null, null, null);
    }

    public JornadaDTO fromJornada(Jornada jornada) {
        return new JornadaDTO(
                jornada.getNomeJornada(),
                jornada.getSituacaoJornada(),
                jornada.getCodigoJornada(),
                jornada.getDataCriacao(),
                jornada.getCliente(),
                jornada.getListaJornadaDataHora().stream()
                        .map(JornadaDataHoraDTO::fromJornadaDataHora).toList());
    }

    public Jornada toJornada(JornadaDTO jornadaDTO) {
        Jornada jornada = new Jornada();
        jornada.setNomeJornada(jornadaDTO.nomeJornada);
        jornada.setSituacaoJornada(jornadaDTO.situacaoJornada);
        jornada.setCodigoJornada(jornadaDTO.codigoJornada);
        jornada.setDataCriacao(jornadaDTO.dataCriacao);
        jornada.setCliente(jornadaDTO.cliente);
        jornada.setListaJornadaDataHora(toJornadaDataHora(jornadaDTO.listaJornadaDataHora));
        return jornada;
    }

    public List<JornadaDataHora> toJornadaDataHora(List<JornadaDataHoraDTO> listaJornadaDataHoraDTO) {
        JornadaDataHoraDTO jornadaDataHoraDTO = new JornadaDataHoraDTO(null, null, null, null, null, null);
        return jornadaDataHoraDTO.toJornadaDataHora(listaJornadaDataHoraDTO);
    }
}
