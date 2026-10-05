package com.saudefacil.saude_facil.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String telefone;
    private String numeroProntuario;
    private LocalDate dataNascimento;
}