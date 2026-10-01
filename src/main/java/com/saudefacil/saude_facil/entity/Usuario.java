package com.saudefacil.saude_facil.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.saudefacil.saude_facil.enums.Perfil;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    private String telefone;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Perfil perfil = Perfil.PACIENTE;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();
}