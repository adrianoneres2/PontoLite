package com.octadata.pontolite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.model.Jornada;
import com.octadata.pontolite.model.JornadaDataHora;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Long> {
	List<Jornada> findByCliente(Cliente cliente);
	List<Jornada> findByCodigoJornada(Long codigoJornada);
	List<Jornada> findByJornadaDataHora(List<JornadaDataHora> jornadaDataHora);
}