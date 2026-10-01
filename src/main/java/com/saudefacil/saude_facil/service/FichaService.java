package com.saudefacil.saude_facil.service;

import com.saudefacil.saude_facil.entity.Ficha;
import com.saudefacil.saude_facil.entity.Profissional;
import com.saudefacil.saude_facil.entity.Usuario;
import com.saudefacil.saude_facil.enums.StatusFicha;
import com.saudefacil.saude_facil.repository.FichaRepository;
import com.saudefacil.saude_facil.repository.ProfissionalRepository;
import com.saudefacil.saude_facil.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FichaService {

    private final FichaRepository fichaRepository;
    private final ProfissionalRepository profissionalRepository;
    private final UsuarioRepository usuarioRepository;

    public Ficha pegarFicha(String usuarioEmail, String profissionalId) {

        Usuario usuario = usuarioRepository.findByEmail(usuarioEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado!"));

        LocalTime agora = LocalTime.now();
        if (agora.isBefore(profissional.getHorarioInicio()) || agora.isAfter(profissional.getHorarioFim())) {
            throw new RuntimeException("Fora do horário de atendimento! Atendimento das "
                    + profissional.getHorarioInicio() + " às " + profissional.getHorarioFim());
        }

        if (profissional.getFichasEmitidasHoje() >= profissional.getLimiteFichasDia()) {
            throw new RuntimeException("As fichas para este profissional já foram encerradas hoje.");
        }

        boolean jaTemFicha = fichaRepository.existsByUsuarioIdAndProfissionalIdAndDataEmissao(
                usuario.getId(), profissionalId, LocalDate.now());
        if (jaTemFicha) {
            throw new RuntimeException("Você já possui uma ficha para este profissional hoje!");
        }

        int proximoNumero = profissional.getFichasEmitidasHoje() + 1;
        String numeroFicha = profissional.getPrefixoFicha() + "-" + String.format("%03d", proximoNumero);

        Ficha ficha = new Ficha();
        ficha.setUsuario(usuario);
        ficha.setProfissional(profissional);
        ficha.setUnidade(profissional.getUnidade());
        ficha.setNumeroFicha(numeroFicha);
        ficha.setStatus(StatusFicha.AGUARDANDO);
        ficha.setDataEmissao(LocalDate.now());

        profissional.setFichasEmitidasHoje(profissional.getFichasEmitidasHoje() + 1);
        profissionalRepository.save(profissional);

        return fichaRepository.save(ficha);
    }

    public List<Ficha> minhasFichas(String usuarioEmail) {
        Usuario usuario = usuarioRepository.findByEmail(usuarioEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        return fichaRepository.findByUsuarioIdAndDataEmissao(usuario.getId(), LocalDate.now());
    }

    public List<Ficha> fichasDoDia(String profissionalId) {
        return fichaRepository.findByProfissionalIdAndDataEmissao(profissionalId, LocalDate.now());
    }

    public Ficha atualizarStatus(String fichaId, StatusFicha novoStatus, String motivo) {
        Ficha ficha = fichaRepository.findById(fichaId)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada!"));
        ficha.setStatus(novoStatus);
        if (motivo != null) {
            ficha.setMotivoCancelamento(motivo);
        }
        if (novoStatus == StatusFicha.CONCLUIDO) {
            ficha.setAtendidoEm(java.time.LocalDateTime.now());
        }
        return fichaRepository.save(ficha);
    }

    public void cancelarFicha(String fichaId, String usuarioEmail) {
        Ficha ficha = fichaRepository.findById(fichaId)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada!"));

        if (!ficha.getUsuario().getEmail().equals(usuarioEmail)) {
            throw new RuntimeException("Você não tem permissão para cancelar esta ficha!");
        }

        if (ficha.getStatus() != StatusFicha.AGUARDANDO) {
            throw new RuntimeException("Só é possível cancelar fichas com status AGUARDANDO!");
        }

        ficha.setStatus(StatusFicha.CANCELADO);
        ficha.setMotivoCancelamento("Cancelado pelo paciente");
        fichaRepository.save(ficha);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void resetarFichasDiarias() {
        List<Profissional> profissionais = profissionalRepository.findAll();
        for (Profissional profissional : profissionais) {
            profissional.setFichasEmitidasHoje(0);
            profissionalRepository.save(profissional);
        }
        System.out.println("Fichas resetadas para o dia " + LocalDate.now());
    }
}