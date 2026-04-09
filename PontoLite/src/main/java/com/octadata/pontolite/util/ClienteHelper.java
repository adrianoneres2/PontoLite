package com.octadata.pontolite.util;

import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.service.AutenticacaoService;

public class ClienteHelper {

    public static Cliente getClienteSelecionado(Cliente clienteSelecionado, AutenticacaoService autenticacaoService) {
        return clienteSelecionado == null ? autenticacaoService.getUsuarioAutenticado().getCliente()
                : clienteSelecionado;
    }

    public static Long getCodigoClienteSelecionado(Long codigoCliente, AutenticacaoService autenticacaoService) {
        return codigoCliente == 0 ? autenticacaoService.getUsuarioAutenticado().getCliente().getCodigoCliente()
                : codigoCliente;
    }

}
