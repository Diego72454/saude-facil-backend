package com.saudefacil.saude_facil.controller;

import com.saudefacil.saude_facil.entity.Unidade;
import com.saudefacil.saude_facil.service.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades")
@RequiredArgsConstructor
public class UnidadeController {

    private final UnidadeService unidadeService;

    @GetMapping
    public ResponseEntity<List<Unidade>> listar() {
        return ResponseEntity.ok(unidadeService.listarAtivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Unidade> buscar(@PathVariable String id) {
        return ResponseEntity.ok(unidadeService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Unidade> criar(@RequestBody Unidade unidade) {
        return ResponseEntity.ok(unidadeService.criar(unidade));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Unidade> editar(@PathVariable String id, @RequestBody Unidade unidade) {
        return ResponseEntity.ok(unidadeService.editar(id, unidade));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> ativarDesativar(@PathVariable String id) {
        unidadeService.ativarDesativar(id);
        return ResponseEntity.ok().build();
    }
}