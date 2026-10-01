package com.saudefacil.saude_facil.controller;

import com.saudefacil.saude_facil.dto.AgendamentoRequest;
import com.saudefacil.saude_facil.entity.Agendamento;
import com.saudefacil.saude_facil.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<Agendamento> criar(@RequestBody AgendamentoRequest request,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(agendamentoService.criar(
                userDetails.getUsername(),
                request.getProfissionalId(),
                request.getData(),
                request.getHorario(),
                request.getObservacao()
        ));
    }

    @GetMapping("/meus")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<List<Agendamento>> meusAgendamentos(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(agendamentoService.meusAgendamentos(userDetails.getUsername()));
    }

    @GetMapping("/profissional/{profissionalId}")
    @PreAuthorize("hasAnyRole('ATENDENTE', 'ADMIN')")
    public ResponseEntity<List<Agendamento>> agendamentosDoDia(
            @PathVariable String profissionalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return ResponseEntity.ok(agendamentoService.agendamentosDoDia(profissionalId, data));
    }

    @PatchMapping("/{id}/confirmar")
    @PreAuthorize("hasAnyRole('ATENDENTE', 'ADMIN')")
    public ResponseEntity<Agendamento> confirmar(@PathVariable String id) {
        return ResponseEntity.ok(agendamentoService.confirmar(id));
    }

    @PatchMapping("/{id}/concluir")
    @PreAuthorize("hasAnyRole('ATENDENTE', 'ADMIN')")
    public ResponseEntity<Agendamento> concluir(@PathVariable String id) {
        return ResponseEntity.ok(agendamentoService.concluir(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<Void> cancelar(@PathVariable String id,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        agendamentoService.cancelar(id, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}