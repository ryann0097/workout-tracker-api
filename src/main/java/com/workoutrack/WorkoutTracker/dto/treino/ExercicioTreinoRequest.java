package com.workoutrack.WorkoutTracker.dto.treino;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dados para adicionar um exercício a um treino")
public record ExercicioTreinoRequest(
        @Schema(description = "ID do exercício a ser adicionado", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID exercicioId,
        @Schema(description = "Ordem de execução do exercício no treino", example = "1")
        Integer ordem,
        @Schema(description = "Número de séries programadas", example = "3")
        Integer series,
        @Schema(description = "Número de repetições por série", example = "10")
        Integer repeticoes,
        @Schema(description = "Peso programado em kg", example = "80.0")
        Double peso
) {}
