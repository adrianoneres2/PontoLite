package com.octadata.pontolite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.octadata.pontolite.model.TipoEscala;

@Repository
public interface TipoEscalaRepository extends JpaRepository<TipoEscala, Long> {

    TipoEscala findByCodigoTipoEscala(Long codigoTipoEscala);

    List<TipoEscala> findAll();

}