package com.workoutrack.WorkoutTracker.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de login do usuário")
public record LoginRequest(
        @Schema(description = "Email do usuário", example = "usuario@exemplo.com")
        String email,
        @Schema(description = "Senha do usuário", example = "senha123")
        String senha
) {
    public static record UsuarioLoginResponse() {

    }
}

