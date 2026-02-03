package com.workoutrack.WorkoutTracker.dto.usuario;

public record LoginRequest(
        String email,
        String senha
) {
    public static record UsuarioLoginResponse() {

    }
}

