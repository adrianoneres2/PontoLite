package com.octadata.pontolite.util;

public enum EnumTipoRegistroPonto {
    ENTRADA(1),
    INTERVALO(2),
    RETORNO_INTERVALO(3),
    SAIDA(4);

    private final int valor;

    EnumTipoRegistroPonto(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
