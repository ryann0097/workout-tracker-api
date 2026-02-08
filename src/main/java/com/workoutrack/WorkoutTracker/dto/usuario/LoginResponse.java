package com.workoutrack.WorkoutTracker.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de login com token de autenticação")
public record LoginResponse(
        @Schema(description = "Token JWT de autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token,
        @Schema(description = "ID do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
        java.util.UUID id,
        @Schema(description = "Nome de usuário", example = "usuario@exemplo.com")
        String username
) {}
