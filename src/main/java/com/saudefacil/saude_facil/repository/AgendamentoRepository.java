package com.saudefacil.saude_facil.repository;

import com.saudefacil.saude_facil.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, String> {
    List<Agendamento> findByUsuarioId(String usuarioId);
    List<Agendamento> findByProfissionalIdAndDataConsulta(String profissionalId, LocalDate data);
    boolean existsByProfissionalIdAndDataConsultaAndHorario(String profissionalId, LocalDate data, java.time.LocalTime horario);
}