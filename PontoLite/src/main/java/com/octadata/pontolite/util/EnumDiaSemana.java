package com.octadata.pontolite.util;

public enum EnumDiaSemana {
    DOMINGO(7),
    SEGUNDA(1),
    TERCA(2),
    QUARTA(3),
    QUINTA(4),
    SEXTA(5),
    SABADO(6);

    private final int valor;

    EnumDiaSemana(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public String getNomeDiaSemana() {
        return switch (this.valor) {
            case 1 -> "Segunda-feira";
            case 2 -> "Terça-feira";
            case 3 -> "Quarta-feira";
            case 4 -> "Quinta-feira";
            case 5 -> "Sexta-feira";
            case 6 -> "Sábado";
            case 7 -> "Domingo";
            default -> "Dia inválido";
        };
    }
}
