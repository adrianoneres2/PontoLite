package com.octadata.pontolite.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.model.Jornada;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Long> {
	List<Jornada> findByCliente(Cliente cliente);

	List<Jornada> findByCodigoJornada(Long codigoJornada);

	Page<Jornada> findByCliente(Cliente cliente, Pageable pageable);

	@Query("select j from Jornada j where j.cliente = :cliente and lower(j.nomeJornada) LIKE %:nomeJornada%")
	Page<Jornada> findByClienteAndNomeJornadaPaginado(Cliente cliente, String nomeJornada, Pageable pageable);
}