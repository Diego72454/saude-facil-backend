package com.saudefacil.saude_facil.controller;

import com.saudefacil.saude_facil.entity.Profissional;
import com.saudefacil.saude_facil.service.ProfissionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profissionais")
@RequiredArgsConstructor
public class ProfissionalController {

    private final ProfissionalService profissionalService;

    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<Profissional>> listarPorUnidade(@PathVariable String unidadeId) {
        return ResponseEntity.ok(profissionalService.listarPorUnidade(unidadeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profissional> buscar(@PathVariable String id) {
        return ResponseEntity.ok(profissionalService.buscarPorId(id));
    }

    @PostMapping("/unidade/{unidadeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Profissional> criar(@RequestBody Profissional profissional,
                                              @PathVariable String unidadeId) {
        return ResponseEntity.ok(profissionalService.criar(profissional, unidadeId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Profissional> editar(@PathVariable String id,
                                               @RequestBody Profissional profissional) {
        return ResponseEntity.ok(profissionalService.editar(id, profissional));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> ativarDesativar(@PathVariable String id) {
        profissionalService.ativarDesativar(id);
        return ResponseEntity.ok().build();
    }
}