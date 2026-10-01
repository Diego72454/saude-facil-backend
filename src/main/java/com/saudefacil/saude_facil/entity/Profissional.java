package com.saudefacil.saude_facil.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "profissionais")
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String especialidade;

    private String registro;

    @Column(name = "prefixo_ficha", length = 3)
    private String prefixoFicha;

    @Column(name = "limite_fichas_dia", nullable = false)
    private Integer limiteFichasDia;

    @Column(name = "fichas_emitidas_hoje", nullable = false)
    private Integer fichasEmitidasHoje = 0;

    @Column(name = "horario_inicio")
    private LocalTime horarioInicio;

    @Column(name = "horario_fim")
    private LocalTime horarioFim;

    @Column(name = "dias_atendimento")
    private String diasAtendimento;

    @Column(nullable = false)
    private Boolean ativo = true;
}