package com.saudefacil.saude_facil.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "unidades")
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    private String telefone;

    @Column(name = "horario_abertura")
    private LocalTime horarioAbertura;

    @Column(name = "horario_fechamento")
    private LocalTime horarioFechamento;

    @Column(nullable = false)
    private Boolean ativa = true;
}