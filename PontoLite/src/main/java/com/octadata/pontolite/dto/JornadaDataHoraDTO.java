package com.octadata.pontolite.dto;

import java.sql.Date;

import jakarta.validation.constraints.NotNull;

public record JornadaDataHoraDTO(
        @NotNull Date dataJornadaDataHora,
        Long codigoJornada,
        @NotNull Long codigoDia,
        @NotNull Long codigoTipoRegistro) {
}
