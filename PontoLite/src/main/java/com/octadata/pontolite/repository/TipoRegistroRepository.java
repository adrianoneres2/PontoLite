package com.octadata.pontolite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.octadata.pontolite.model.TipoRegistro;

@Repository
public interface TipoRegistroRepository extends JpaRepository<TipoRegistro, Long> {
	
    @Query("select rp from TipoRegistro rp where rp.codigoTipoRegistro = :codigoTipoRegistro")
	TipoRegistro findByCodigoTipoRegistro(@Param("codigoTipoRegistro")Long codigoTipoRegistro);
}
