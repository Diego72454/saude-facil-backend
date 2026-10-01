package com.saudefacil.saude_facil.service;

import com.saudefacil.saude_facil.entity.Unidade;
import com.saudefacil.saude_facil.repository.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    public List<Unidade> listarAtivas() {
        return unidadeRepository.findByAtivaTrue();
    }

    public Unidade buscarPorId(String id) {
        return unidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada!"));
    }

    public Unidade criar(Unidade unidade) {
        unidade.setAtiva(true);
        return unidadeRepository.save(unidade);
    }

    public Unidade editar(String id, Unidade dados) {
        Unidade unidade = buscarPorId(id);
        unidade.setNome(dados.getNome());
        unidade.setEndereco(dados.getEndereco());
        unidade.setTelefone(dados.getTelefone());
        unidade.setHorarioAbertura(dados.getHorarioAbertura());
        unidade.setHorarioFechamento(dados.getHorarioFechamento());
        return unidadeRepository.save(unidade);
    }

    public void ativarDesativar(String id) {
        Unidade unidade = buscarPorId(id);
        unidade.setAtiva(!unidade.getAtiva());
        unidadeRepository.save(unidade);
    }
}