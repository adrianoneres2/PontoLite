package com.octadata.pontolite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.octadata.pontolite.model.RegistroPonto;
import com.octadata.pontolite.model.Usuario;

@Repository
public interface RegistroPontoRepository extends JpaRepository<RegistroPonto, Long>{

	List<RegistroPonto> findByUsuario(Usuario usuario);
}
