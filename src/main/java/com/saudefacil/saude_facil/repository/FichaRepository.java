package com.saudefacil.saude_facil.repository;

import com.saudefacil.saude_facil.entity.Ficha;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface FichaRepository extends JpaRepository<Ficha, String> {
    List<Ficha> findByUsuarioIdAndDataEmissao(String usuarioId, LocalDate data);
    List<Ficha> findByProfissionalIdAndDataEmissao(String profissionalId, LocalDate data);
    boolean existsByUsuarioIdAndProfissionalIdAndDataEmissao(String usuarioId, String profissionalId, LocalDate data);
}