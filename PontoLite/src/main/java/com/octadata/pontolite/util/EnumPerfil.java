package com.octadata.pontolite.util;

public enum EnumPerfil {
    ADMINISTRADOR(1),
    GERENTE(2),
    COLABORADOR(3);

    private final int valor;

    EnumPerfil(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
