package com.workoutrack.WorkoutTracker.dto.treino;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados completos de um registro de treino retornados pela API")
public record RegistroTreinoResponse(
        @Schema(description = "ID único do registro de treino", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,
        @Schema(description = "Data e hora da execução do treino", example = "2026-02-08T14:30:00")
        LocalDateTime dataExecucao,
        @Schema(description = "Nome do treino executado", example = "Peito e Tríceps")
        String nomeTreino
) {}
