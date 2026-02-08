package com.workoutrack.WorkoutTracker.dto.treino;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dados do exercício executado em um registro de treino")
public record ExercicioExecutadoRequest(
        @Schema(description = "ID do exercício no treino", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID exercicioTreinoId,
        @Schema(description = "Número de séries realizadas", example = "3")
        Integer seriesRealizadas,
        @Schema(description = "Número de repetições realizadas por série", example = "10")
        Integer repeticoesRealizadas,
        @Schema(description = "Peso utilizado em kg", example = "80.0")
        Double pesoUtilizado
) {}
