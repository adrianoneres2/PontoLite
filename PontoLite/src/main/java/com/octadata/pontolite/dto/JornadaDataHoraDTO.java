package com.octadata.pontolite.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.octadata.pontolite.model.JornadaDataHora;

import jakarta.validation.constraints.NotNull;

public record JornadaDataHoraDTO(
        Long codigoJornadaDataHora,
        @NotNull LocalTime hora,
        LocalDateTime dataJornadaDataHora,
        Long codigoJornada,
        @NotNull Long codigoDia,
        @NotNull Long codigoTipoRegistro) {

    public List<JornadaDataHora> toJornadaDataHora(List<JornadaDataHoraDTO> listaJornadaDataHoraDTO) {
        List<JornadaDataHora> jornadaDataHoraList = new ArrayList<>();
        for (JornadaDataHoraDTO jornadaDataHoraDTO : listaJornadaDataHoraDTO) {
            JornadaDataHora jornadaDataHora = new JornadaDataHora();
            jornadaDataHora.setCodigoDia(jornadaDataHoraDTO.codigoDia());
            jornadaDataHora.setDataJornadaDataHora(
                    jornadaDataHoraDTO.toLocalDateTime(jornadaDataHoraDTO.hora));
            jornadaDataHora.setCodigoTipoRegistro(jornadaDataHoraDTO.codigoTipoRegistro());
            jornadaDataHoraList.add(jornadaDataHora);
        }
        return jornadaDataHoraList;
    }

    public LocalDateTime toLocalDateTime(LocalTime localTime) {
        LocalDate localDate = LocalDate.now();
        return localDate.atTime(localTime);
    }

    public static JornadaDataHoraDTO fromJornadaDataHora(JornadaDataHora jornadaDataHora) {
        return new JornadaDataHoraDTO(
                jornadaDataHora.getCodigoJornadaDataHora(),
                jornadaDataHora.getDataJornadaDataHora().toLocalTime(),
                jornadaDataHora.getDataJornadaDataHora(),
                jornadaDataHora.getJornada().getCodigoJornada(),
                jornadaDataHora.getCodigoDia(),
                jornadaDataHora.getCodigoTipoRegistro());
    }

}
