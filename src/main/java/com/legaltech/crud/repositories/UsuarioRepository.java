package com.legaltech.crud.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.legaltech.crud.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
