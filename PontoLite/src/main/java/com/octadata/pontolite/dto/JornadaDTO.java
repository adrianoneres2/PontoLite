package com.octadata.pontolite.dto;

import java.sql.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public record JornadaDTO(
        @NotNull String nomeJornada,
        Long situacaoJornada,
        @NotNull Long codigoJornada,
        Date dataCriacao,
        @NotNull Long codigoCliente,
        List<JornadaDataHoraDTO> listaJornadaDataHora) {
}
