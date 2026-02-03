package com.workoutrack.WorkoutTracker.dto.usuario;

import java.time.LocalDateTime;

public record UsuarioRegisterRequest(
        String email,
        String senha,
        String nome,
        LocalDateTime dataNascimento,
        Double peso,
        Double altura
) {}