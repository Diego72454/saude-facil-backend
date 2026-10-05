package com.saudefacil.saude_facil.service;

import com.saudefacil.saude_facil.entity.Agendamento;
import com.saudefacil.saude_facil.entity.Profissional;
import com.saudefacil.saude_facil.entity.Usuario;
import com.saudefacil.saude_facil.enums.StatusAgendamento;
import com.saudefacil.saude_facil.repository.AgendamentoRepository;
import com.saudefacil.saude_facil.repository.ProfissionalRepository;
import com.saudefacil.saude_facil.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ProfissionalRepository profissionalRepository;
    private final UsuarioRepository usuarioRepository;

    public Agendamento criar(String usuarioEmail, String profissionalId,
                             LocalDate data, LocalTime horario, String observacao) {

        Usuario usuario = usuarioRepository.findByEmail(usuarioEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado!"));

        // Verifica se a data não é no passado
        if (data.isBefore(LocalDate.now())) {
            throw new RuntimeException("Não é possível agendar para uma data passada!");
        }

        // Verifica se o horário já está ocupado
        boolean horarioOcupado = agendamentoRepository
                .existsByProfissionalIdAndDataConsultaAndHorario(profissionalId, data, horario);
        if (horarioOcupado) {
            throw new RuntimeException("Este horário já está ocupado!");
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setUsuario(usuario);
        agendamento.setProfissional(profissional);
        agendamento.setUnidade(profissional.getUnidade());
        agendamento.setDataConsulta(data);
        agendamento.setHorario(horario);
        agendamento.setObservacao(observacao);
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        return agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> meusAgendamentos(String usuarioEmail) {
        Usuario usuario = usuarioRepository.findByEmail(usuarioEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        return agendamentoRepository.findByUsuarioId(usuario.getId());
    }

    public List<Agendamento> agendamentosDoDia(String profissionalId, LocalDate data) {
        return agendamentoRepository.findByProfissionalIdAndDataConsulta(profissionalId, data);
    }

    public Agendamento confirmar(String agendamentoId) {
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado!"));
        agendamento.setStatus(StatusAgendamento.CONFIRMADO);
        agendamento.setConfirmadoEm(LocalDateTime.now());
        return agendamentoRepository.save(agendamento);
    }

    public Agendamento concluir(String agendamentoId) {
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado!"));
        agendamento.setStatus(StatusAgendamento.CONCLUIDO);
        return agendamentoRepository.save(agendamento);
    }

    public void cancelar(String agendamentoId, String usuarioEmail) {
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado!"));

        // Verifica se o agendamento pertence ao usuário
        if (!agendamento.getUsuario().getEmail().equals(usuarioEmail)) {
            throw new RuntimeException("Você não tem permissão para cancelar este agendamento!");
        }

        // Verifica regra dos 30 minutos
        LocalDateTime limite = agendamento.getDataConsulta()
                .atTime(agendamento.getHorario())
                .minusMinutes(30);

        if (LocalDateTime.now().isAfter(limite)) {
            throw new RuntimeException("Não é possível cancelar com menos de 30 minutos de antecedência!");
        }

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        agendamentoRepository.save(agendamento);
    }
}