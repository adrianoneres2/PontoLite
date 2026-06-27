package com.octadata.pontolite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.octadata.pontolite.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	Optional<Cliente> findByNomeCliente(String nomeCliente);

	Optional<Cliente> findById(Long codigoCliente);

	Optional<Cliente> findByNumeroCnpj(String numeroCnpj);

	Optional<Cliente> findByNomeEmail(String nomeEmail);

	Optional<Cliente> findByRazaoSocial(String razaoSocial);
}