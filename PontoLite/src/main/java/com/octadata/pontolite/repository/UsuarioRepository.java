package com.octadata.pontolite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.octadata.pontolite.model.Cliente;
import com.octadata.pontolite.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByUsername(String username);

	List<Usuario> findAllByCliente(Cliente cliente);

	Optional<Usuario> findByEmail(String email);

	Page<Usuario> findAllByCliente(Cliente cliente, Pageable pageable);

	@Query("SELECT u FROM Usuario u WHERE u.cliente = :cliente AND lower(u.username) LIKE %:nomeUsuario%")
	Page<Usuario> findAllByClienteAndUsernamePaged(Cliente cliente, String nomeUsuario, Pageable pageable);
}
