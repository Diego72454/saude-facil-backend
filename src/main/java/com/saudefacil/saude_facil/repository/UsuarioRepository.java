package com.saudefacil.saude_facil.repository;

import com.saudefacil.saude_facil.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}