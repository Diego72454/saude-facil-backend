package com.saudefacil.saude_facil.controller;

import com.saudefacil.saude_facil.entity.Ficha;
import com.saudefacil.saude_facil.enums.StatusFicha;
import com.saudefacil.saude_facil.service.FichaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fichas")
@RequiredArgsConstructor
public class FichaController {

    private final FichaService fichaService;

    @PostMapping("/profissional/{profissionalId}")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<Ficha> pegarFicha(@PathVariable String profissionalId,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(fichaService.pegarFicha(userDetails.getUsername(), profissionalId));
    }

    @GetMapping("/minhas")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<List<Ficha>> minhasFichas(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(fichaService.minhasFichas(userDetails.getUsername()));
    }

    @GetMapping("/hoje/{profissionalId}")
    @PreAuthorize("hasAnyRole('ATENDENTE', 'ADMIN')")
    public ResponseEntity<List<Ficha>> fichasDoDia(@PathVariable String profissionalId) {
        return ResponseEntity.ok(fichaService.fichasDoDia(profissionalId));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ATENDENTE', 'ADMIN')")
    public ResponseEntity<Ficha> atualizarStatus(@PathVariable String id,
                                                 @RequestBody Map<String, String> body) {
        StatusFicha status = StatusFicha.valueOf(body.get("status"));
        String motivo = body.get("motivo");
        return ResponseEntity.ok(fichaService.atualizarStatus(id, status, motivo));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<Void> cancelarFicha(@PathVariable String id,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        fichaService.cancelarFicha(id, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}