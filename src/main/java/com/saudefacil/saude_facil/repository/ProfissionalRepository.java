package com.saudefacil.saude_facil.repository;

import com.saudefacil.saude_facil.entity.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProfissionalRepository extends JpaRepository<Profissional, String> {
    List<Profissional> findByUnidadeIdAndAtivoTrue(String unidadeId);
}