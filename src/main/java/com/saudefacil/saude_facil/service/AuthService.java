package com.saudefacil.saude_facil.service;

import com.saudefacil.saude_facil.dto.AuthResponse;
import com.saudefacil.saude_facil.dto.LoginRequest;
import com.saudefacil.saude_facil.dto.RegisterRequest;
import com.saudefacil.saude_facil.entity.Usuario;
import com.saudefacil.saude_facil.enums.Perfil;
import com.saudefacil.saude_facil.repository.UsuarioRepository;
import com.saudefacil.saude_facil.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse registrar(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado!");
        }
        if (usuarioRepository.existsByCpf(request.getCpf())) {
            throw new RuntimeException("CPF já cadastrado!");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setCpf(request.getCpf());
        usuario.setEmail(request.getEmail());
        usuario.setSenhaHash(passwordEncoder.encode(request.getSenha()));
        usuario.setTelefone(request.getTelefone());
        usuario.setNumeroProntuario(request.getNumeroProntuario());
        usuario.setDataNascimento(request.getDataNascimento());
        usuario.setPerfil(Perfil.PACIENTE);

        usuarioRepository.save(usuario);

        String token = jwtUtil.gerarToken(usuario.getEmail(), usuario.getPerfil().name());
        return new AuthResponse(token, usuario.getNome(), usuario.getEmail(), usuario.getPerfil().name());
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha inválidos!"));

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenhaHash())) {
            throw new RuntimeException("E-mail ou senha inválidos!");
        }

        if (!usuario.getAtivo()) {
            throw new RuntimeException("Usuário inativo. Entre em contato com o posto.");
        }

        String token = jwtUtil.gerarToken(usuario.getEmail(), usuario.getPerfil().name());
        return new AuthResponse(token, usuario.getNome(), usuario.getEmail(), usuario.getPerfil().name());
    }
}