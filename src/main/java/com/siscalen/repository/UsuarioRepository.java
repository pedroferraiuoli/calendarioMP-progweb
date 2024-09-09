package com.siscalen.repository;

import com.siscalen.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para encontrar usuário por email
    Usuario findByEmail(String email);
}
