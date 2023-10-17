package com.octadata.pontolite.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.octadata.pontolite.model.RegistroPonto;
import com.octadata.pontolite.model.Usuario;

@Repository
public interface RegistroPontoRepository extends JpaRepository<RegistroPonto, Long>{

	List<RegistroPonto> findByUsuario(Usuario usuario);
	List<RegistroPonto> findByDataRegistroPonto(LocalDateTime dataRegistroPonto);
	List<RegistroPonto> findByDataRegistroPontoBetween(LocalDateTime dataRegistroPontoInicial, LocalDateTime dataRegistroPontoFinal);
	
	@Query("select r from RegistroPonto r join r.usuario u where u.codigoUsuario = :codigoUsuario and r.dataRegistroPonto >= :dataInicial and r.dataRegistroPonto <= :dataFinal")
	List<RegistroPonto> findByPeriodoPorUsuario(@Param("codigoUsuario")Long codigoUsuario, @Param("dataInicial") LocalDateTime dataInicial, @Param("dataFinal") LocalDateTime dataFinal);
	
	@Query(nativeQuery = true, 
			value="select * \n"
					+ "  from pontolite.tb_registro_ponto trp\n"
					+ " where (id_usuario, dt_registro_ponto) \n"
					+ "    in (select ID_USUARIO, MAX(dt_registro_ponto) \n"
					+ "          from pontolite.tb_registro_ponto trp\n"
					+ "         where ID_USUARIO = :codigoUsuario\n"
					+ "         group by ID_USUARIO)")
	RegistroPonto findMaiorRegistroPorCodigoUsuario(Long codigoUsuario);
	
}
