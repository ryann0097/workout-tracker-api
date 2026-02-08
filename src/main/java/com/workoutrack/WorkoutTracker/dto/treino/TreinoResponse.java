package com.workoutrack.WorkoutTracker.dto.treino;

import com.workoutrack.WorkoutTracker.domain.treino.DiaSemana;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(description = "Dados completos de um treino retornados pela API")
public record TreinoResponse(
        @Schema(description = "ID único do treino", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,
        @Schema(description = "Nome do treino", example = "Peito e Tríceps")
        String nome,
        @Schema(description = "Dia da semana para executar o treino", example = "SEGUNDA")
        DiaSemana diaSemana,
        @Schema(description = "Lista de exercícios que fazem parte do treino")
        List<ExercicioTreinoResponse> exercicios
) {}
