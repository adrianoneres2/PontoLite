package com.octadata.pontolite.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public record JornadaDataHoraDTO(
                @NotNull LocalTime dataJornadaDataHora,
                Long codigoJornada,
                @NotNull Long codigoDia,
                @NotNull Long codigoTipoRegistro) {
}
