package com.saudefacil.saude_facil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TesteSenha {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String novoHash = encoder.encode("diego13633");
        System.out.println("Novo hash: " + novoHash);
    }
}
