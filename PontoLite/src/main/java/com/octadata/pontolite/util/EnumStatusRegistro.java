package com.octadata.pontolite.util;

public enum EnumStatusRegistro {
    ATIVO(1),
    AGUARDANDO_APROVACAO(2),
    INATIVO_POR_AJUSTE(-1);

    private final int valor;

    EnumStatusRegistro(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public boolean isAtivo() {
        return this.valor == 1;
    }
}
