package com.octadata.pontolite.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import com.octadata.pontolite.model.JornadaDataHora;
import com.octadata.pontolite.util.EnumDiaSemana;

public record JornadaDataHoraDTOResumo(
        Long codigoJornadaDataHora,
        @NotNull LocalTime horaEntrada,
        @NotNull LocalTime horaIntervalo,
        @NotNull LocalTime horaRetorno,
        @NotNull LocalTime horaSaida,
        LocalDateTime dataJornadaDataHora,
        Long codigoJornada,
        @NotNull Long codigoDia,
        @NotNull Long codigoTipoRegistro,
        @NotNull String nomeDiaSemana) {

    public static List<JornadaDataHoraDTOResumo> toListaJornadaDataHoraDTOResumo(
            List<JornadaDataHora> listaJornadaDataHora) {

        List<JornadaDataHoraDTOResumo> listaJornadaDataHoraDTOResumo = new ArrayList<>();
        LocalTime horaEntrada = null;
        LocalTime horaIntervalo = null;
        LocalTime horaRetorno = null;
        LocalTime horaSaida = null;

        for (JornadaDataHora jornadaDataHora : listaJornadaDataHora) {

            if (jornadaDataHora.getCodigoTipoRegistro() == 1) {
                horaEntrada = jornadaDataHora.getDataJornadaDataHora().toLocalTime();
            }
            if (jornadaDataHora.getCodigoTipoRegistro() == 2) {
                horaIntervalo = jornadaDataHora.getDataJornadaDataHora().toLocalTime();
            }
            if (jornadaDataHora.getCodigoTipoRegistro() == 3) {
                horaRetorno = jornadaDataHora.getDataJornadaDataHora().toLocalTime();
            }
            if (jornadaDataHora.getCodigoTipoRegistro() == 4) {
                horaSaida = jornadaDataHora.getDataJornadaDataHora().toLocalTime();
            }

            /*
             * Preenche a lista de jornadaDataHoraDTOResumo quando for a última ocorrencia
             * do loop para retornar uma lista com as horas de entrada, intervalo,
             * retorno e saída em uma linha distinta.
             */
            if (jornadaDataHora.getCodigoTipoRegistro() == 4) {
                listaJornadaDataHoraDTOResumo.add(new JornadaDataHoraDTOResumo(
                        jornadaDataHora.getCodigoJornadaDataHora(),
                        horaEntrada,
                        horaIntervalo,
                        horaRetorno,
                        horaSaida,
                        jornadaDataHora.getDataJornadaDataHora(),
                        jornadaDataHora.getJornada().getCodigoJornada(),
                        jornadaDataHora.getCodigoDia(),
                        jornadaDataHora.getCodigoTipoRegistro(),
                        EnumDiaSemana.values()[jornadaDataHora.getCodigoDia().intValue() - 1].getNomeDiaSemana()));
            }
        }
        return listaJornadaDataHoraDTOResumo;
    }
}
