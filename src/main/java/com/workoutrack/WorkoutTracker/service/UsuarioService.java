package com.workoutrack.WorkoutTracker.service;

import com.fasterxml.jackson.core.Base64Variant;
import com.workoutrack.WorkoutTracker.domain.perfil.PerfilTreino;
import com.workoutrack.WorkoutTracker.domain.usuario.Usuario;
import com.workoutrack.WorkoutTracker.dto.usuario.UsuarioRegisterRequest;
import com.workoutrack.WorkoutTracker.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findById(UUID id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public void deleteById(UUID id) {
        usuarioRepository.deleteById(id);
    }

    public void registrar(UsuarioRegisterRequest dto){
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.email());

        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        PerfilTreino perfil = new PerfilTreino();
        perfil.setNome(dto.nome());
        perfil.setDataNascimento(dto.dataNascimento());
        perfil.setPeso(dto.peso());
        perfil.setAltura(dto.altura());
        perfil.setIMC(dto.peso() / (dto.altura() * dto.altura()));

        perfil.setUsuario(usuario);
        usuario.setPerfilTreino(perfil);

        usuarioRepository.save(usuario);
    }
}