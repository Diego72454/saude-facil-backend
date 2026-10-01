package com.saudefacil.saude_facil.repository;

import com.saudefacil.saude_facil.entity.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UnidadeRepository extends JpaRepository<Unidade, String> {
    List<Unidade> findByAtivaTrue();
}