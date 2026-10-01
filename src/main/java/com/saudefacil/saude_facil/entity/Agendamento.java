package com.saudefacil.saude_facil.entity;

import com.saudefacil.saude_facil.enums.StatusAgendamento;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;

    @ManyToOne
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    @Column(name = "data_consulta", nullable = false)
    private LocalDate dataConsulta;

    @Column(nullable = false)
    private LocalTime horario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status = StatusAgendamento.AGENDADO;

    private String observacao;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(name = "confirmado_em")
    private LocalDateTime confirmadoEm;
}