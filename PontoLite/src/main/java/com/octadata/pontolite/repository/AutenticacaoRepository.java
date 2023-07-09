package com.octadata.pontolite.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.octadata.pontolite.model.Usuario;

@Repository
public interface AutenticacaoRepository extends JpaRepository<Usuario, Long> {

}
