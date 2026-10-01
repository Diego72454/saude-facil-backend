package com.saudefacil.saude_facil.entity;

import com.saudefacil.saude_facil.enums.StatusFicha;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "fichas")
public class Ficha {

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

    @Column(name = "numero_ficha", nullable = false)
    private String numeroFicha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusFicha status = StatusFicha.AGUARDANDO;

    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao = LocalDate.now();

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(name = "atendido_em")
    private LocalDateTime atendidoEm;

    @Column(name = "motivo_cancelamento")
    private String motivoCancelamento;
}