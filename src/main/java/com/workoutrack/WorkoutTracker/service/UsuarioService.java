package com.workoutrack.WorkoutTracker.service;

import com.workoutrack.WorkoutTracker.domain.usuario.Usuario;
import com.workoutrack.WorkoutTracker.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

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
}