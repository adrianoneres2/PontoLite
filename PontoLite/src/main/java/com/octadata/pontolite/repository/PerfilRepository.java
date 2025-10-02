package com.octadata.pontolite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.octadata.pontolite.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
	Optional<Perfil> findByNomePerfil(String nomePerfil);
}
