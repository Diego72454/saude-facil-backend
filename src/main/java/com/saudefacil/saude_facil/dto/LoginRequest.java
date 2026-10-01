package com.saudefacil.saude_facil.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String senha;
}
