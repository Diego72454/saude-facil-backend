package com.saudefacil.saude_facil.service;

import com.saudefacil.saude_facil.entity.Profissional;
import com.saudefacil.saude_facil.entity.Unidade;
import com.saudefacil.saude_facil.repository.ProfissionalRepository;
import com.saudefacil.saude_facil.repository.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    private final UnidadeRepository unidadeRepository;

    public List<Profissional> listarPorUnidade(String unidadeId) {
        return profissionalRepository.findByUnidadeIdAndAtivoTrue(unidadeId);
    }

    public Profissional buscarPorId(String id) {
        return profissionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado!"));
    }

    public Profissional criar(Profissional profissional, String unidadeId) {
        Unidade unidade = unidadeRepository.findById(unidadeId)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada!"));
        profissional.setUnidade(unidade);
        profissional.setAtivo(true);
        profissional.setFichasEmitidasHoje(0);
        return profissionalRepository.save(profissional);
    }

    public Profissional editar(String id, Profissional dados) {
        Profissional profissional = buscarPorId(id);
        profissional.setNome(dados.getNome());
        profissional.setEspecialidade(dados.getEspecialidade());
        profissional.setRegistro(dados.getRegistro());
        profissional.setPrefixoFicha(dados.getPrefixoFicha());
        profissional.setLimiteFichasDia(dados.getLimiteFichasDia());
        profissional.setHorarioInicio(dados.getHorarioInicio());
        profissional.setHorarioFim(dados.getHorarioFim());
        profissional.setDiasAtendimento(dados.getDiasAtendimento());
        return profissionalRepository.save(profissional);
    }

    public void ativarDesativar(String id) {
        Profissional profissional = buscarPorId(id);
        profissional.setAtivo(!profissional.getAtivo());
        profissionalRepository.save(profissional);
    }
}