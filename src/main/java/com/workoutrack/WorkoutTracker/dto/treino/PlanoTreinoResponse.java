package com.workoutrack.WorkoutTracker.dto.treino;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dados completos de um plano de treino retornados pela API")
public record PlanoTreinoResponse(
        @Schema(description = "ID único do plano de treino", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,
        @Schema(description = "Nome do plano de treino", example = "Hipertrofia 12 Semanas")
        String nome,
        @Schema(description = "Duração total do plano em semanas", example = "12")
        Integer semanasDuracao
) {}
