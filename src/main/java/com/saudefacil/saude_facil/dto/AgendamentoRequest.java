package com.saudefacil.saude_facil.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AgendamentoRequest {
    private String profissionalId;
    private LocalDate data;
    private LocalTime horario;
    private String observacao;
}