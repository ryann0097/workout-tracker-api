package com.workoutrack.WorkoutTracker.repository;

import com.workoutrack.WorkoutTracker.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario>findByEmail(String email);
}
