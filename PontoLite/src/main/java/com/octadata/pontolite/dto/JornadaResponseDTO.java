package com.octadata.pontolite.dto;

import jakarta.validation.constraints.NotNull;

public record JornadaResponseDTO(
        @NotNull Long codigoJornada,
        @NotNull String nomeJornada) {

    public JornadaResponseDTO() {
        this(null, null);
    }
}
