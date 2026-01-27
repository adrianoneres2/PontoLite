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
}
