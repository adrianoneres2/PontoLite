package com.octadata.pontolite.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.octadata.pontolite.exception.NegocioException;
import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.model.Jornada;
import com.octadata.pontolite.model.JornadaDataHora;
import com.octadata.pontolite.repository.JornadaDataHoraRepository;
import com.octadata.pontolite.repository.JornadaRepository;
import com.octadata.pontolite.util.DefaultConstant;
import com.octadata.pontolite.util.EnumMessage;

@Service
public class JornadaServiceImpl implements JornadaService {

	@Autowired
	JornadaRepository jornadaRepository;

	@Autowired
	JornadaDataHoraRepository jornadaDataHoraRepository;

	@Override
	@Transactional
	public Jornada salvar(Jornada jornada) {
		validar(jornada);
		Jornada jornadaSalva = jornadaRepository.save(jornada);
		jornadaSalva.getListaJornadaDataHora().forEach(jornadaDataHora -> {
			jornadaDataHora.setJornada(jornadaSalva);
			jornadaDataHoraRepository.save(jornadaDataHora);
		});
		return jornadaSalva;
	}

	@Override
	public List<Jornada> porCliente(Cliente cliente) {
		return jornadaRepository.findByCliente(cliente);
	}

	public void validar(Jornada jornada) {

		if (jornada.getCliente() == null) {
			jornada.setCliente(jornada.getUsuarioCadastro().getCliente());
		}

		if (jornada.getCodigoJornada() == null) {
			jornada.setDataCriacao(new Date(System.currentTimeMillis()));
			jornada.setSituacaoJornada(DefaultConstant.ATIVO);
		}
		if (jornada.getNomeJornada() == null || jornada.getNomeJornada().isEmpty()) {
			throw new NegocioException(EnumMessage.ERROR.toString(), "Nome da jornada é obrigatório!", "");
		}

		if (jornada.getCliente() == null) {
			throw new NegocioException(EnumMessage.ERROR.toString(), "Cliente é obrigatório!", "");
		}

		if (jornada.getListaJornadaDataHora() == null || jornada.getListaJornadaDataHora().isEmpty()) {
			throw new NegocioException(EnumMessage.ERROR.toString(), "Lista de horários é obrigatória!", "");
		}

		for (JornadaDataHora jornadaDataHora : jornada.getListaJornadaDataHora()) {
			if (jornadaDataHora.getCodigoDia() == null) {
				throw new NegocioException(EnumMessage.ERROR.toString(), "Dia da semana é obrigatório!", "");
			}

			if (jornadaDataHora.getCodigoTipoRegistro() == null) {
				throw new NegocioException(EnumMessage.ERROR.toString(), "Código do tipo de registro é obrigatório!",
						"");
			}
		}
	}

}
