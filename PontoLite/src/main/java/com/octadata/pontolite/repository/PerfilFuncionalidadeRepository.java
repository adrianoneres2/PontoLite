package com.octadata.pontolite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.model.Perfil;
import com.octadata.pontolite.model.PerfilFuncionalidade;

@Repository
public interface PerfilFuncionalidadeRepository extends JpaRepository<PerfilFuncionalidade, Long> {
    List<PerfilFuncionalidade> findByCliente(Cliente cliente);

    @Modifying
    @Query("DELETE FROM PerfilFuncionalidade pf WHERE pf.cliente = :cliente")
    void deleteByCliente(@Param("cliente") Cliente cliente);

    List<PerfilFuncionalidade> findByClienteAndPerfil(Cliente cliente, Perfil perfil);
}
