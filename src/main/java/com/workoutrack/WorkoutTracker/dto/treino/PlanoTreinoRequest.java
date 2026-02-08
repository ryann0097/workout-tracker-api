package com.workoutrack.WorkoutTracker.dto.treino;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para criar ou atualizar um plano de treino")
public record PlanoTreinoRequest(
        @Schema(description = "Nome do plano de treino", example = "Hipertrofia 12 Semanas")
        String nome,
        @Schema(description = "Duração total do plano em semanas", example = "12")
        Integer semanasDuracao
) {}
