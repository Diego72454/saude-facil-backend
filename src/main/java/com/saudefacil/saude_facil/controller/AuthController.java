package com.saudefacil.saude_facil.controller;

import com.saudefacil.saude_facil.dto.AuthResponse;
import com.saudefacil.saude_facil.dto.LoginRequest;
import com.saudefacil.saude_facil.dto.RegisterRequest;
import com.saudefacil.saude_facil.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registrar(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registrar(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}