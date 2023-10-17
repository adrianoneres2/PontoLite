package com.octadata.pontolite.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.octadata.pontolite.model.RegistroPonto;
import com.octadata.pontolite.model.Usuario;

@Service
public interface RegistroPontoService {

	RegistroPonto salvar(RegistroPonto registroPonto);

	List<RegistroPonto> listarPorUsuario();

	List<RegistroPonto> listarPorDataRegistroPonto();

	List<RegistroPonto> listarPorDataRegistroPontoHoje();

	List<RegistroPonto> listarPeriodoPorUsuario(Usuario usuario, LocalDateTime dataHoraIncial,
			LocalDateTime dataHoraFinal);

}
