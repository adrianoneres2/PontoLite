package com.octadata.pontolite.dto;

import java.sql.Date;

import jakarta.validation.constraints.NotNull;

public record JornadaDataHoraDTO(
                @NotNull Date dataJornadaHora,
                Long codigoJornada,
                @NotNull Long codigoDia,
                @NotNull Long codigoTipoRegistro) {
}
