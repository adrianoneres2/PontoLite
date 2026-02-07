package com.octadata.pontolite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.octadata.pontolite.model.Jornada;
import com.octadata.pontolite.model.JornadaDataHora;

@Repository
public interface JornadaDataHoraRepository extends JpaRepository<JornadaDataHora, Long> {
    List<JornadaDataHora> findByJornada(Jornada jornada);
}
