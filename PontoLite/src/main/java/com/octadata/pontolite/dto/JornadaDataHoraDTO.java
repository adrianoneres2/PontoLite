package com.octadata.pontolite.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.octadata.pontolite.model.JornadaDataHora;
import com.octadata.pontolite.util.EnumDiaSemana;

import jakarta.validation.constraints.NotNull;

public record JornadaDataHoraDTO(
        Long codigoJornadaDataHora,
        @NotNull LocalTime hora,
        LocalDateTime dataJornadaDataHora,
        Long codigoJornada,
        @NotNull Long codigoDia,
        @NotNull Long codigoTipoRegistro,
        @NotNull String nomeDiaSemana) {

    public List<JornadaDataHora> toJornadaDataHora(List<JornadaDataHoraDTO> listaJornadaDataHoraDTO) {
        List<JornadaDataHora> jornadaDataHoraList = new ArrayList<>();
        for (JornadaDataHoraDTO jornadaDataHoraDTO : listaJornadaDataHoraDTO) {
            JornadaDataHora jornadaDataHora = new JornadaDataHora();
            jornadaDataHora.setCodigoDia(jornadaDataHoraDTO.codigoDia());
            jornadaDataHora.setDataJornadaDataHora(
                    jornadaDataHoraDTO.toLocalDateTime(jornadaDataHoraDTO.hora()));
            jornadaDataHora.setCodigoTipoRegistro(jornadaDataHoraDTO.codigoTipoRegistro());
            jornadaDataHora.setCodigoJornadaDataHora(jornadaDataHoraDTO.codigoJornadaDataHora());
            jornadaDataHoraList.add(jornadaDataHora);
        }
        jornadaDataHoraList.sort(
                Comparator.comparing(JornadaDataHora::getCodigoDia)
                        .thenComparing(JornadaDataHora::getCodigoTipoRegistro));
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
                jornadaDataHora.getCodigoTipoRegistro(),
                EnumDiaSemana.values()[jornadaDataHora.getCodigoDia().intValue() - 1].getNomeDiaSemana());
    }

}
