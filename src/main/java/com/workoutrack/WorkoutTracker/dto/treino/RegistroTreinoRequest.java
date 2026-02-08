package com.workoutrack.WorkoutTracker.dto.treino;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Dados para registrar a execução de um treino")
public record RegistroTreinoRequest(
        @Schema(description = "Data e hora da execução do treino", example = "2026-02-08T14:30:00")
        LocalDateTime dataExecucao,
        @Schema(description = "Lista de exercícios executados no treino")
        List<ExercicioExecutadoRequest> exercicios
) {}
