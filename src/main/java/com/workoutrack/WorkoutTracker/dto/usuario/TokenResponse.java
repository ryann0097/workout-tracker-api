package com.workoutrack.WorkoutTracker.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta contendo apenas o token de autenticação")
public record TokenResponse(
        @Schema(description = "Token JWT de autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token
) {}
