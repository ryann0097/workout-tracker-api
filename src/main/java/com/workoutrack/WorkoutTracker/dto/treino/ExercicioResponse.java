package com.workoutrack.WorkoutTracker.dto.treino;

import com.workoutrack.WorkoutTracker.domain.treino.CategoriaExercicio;
import com.workoutrack.WorkoutTracker.domain.treino.GrupoMuscular;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dados completos de um exercício retornados pela API")
public record ExercicioResponse(
        @Schema(description = "ID único do exercício", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,
        @Schema(description = "Nome do exercício", example = "Supino Reto")
        String nome,
        @Schema(description = "Descrição detalhada do exercício", example = "Exercício para peitoral usando barra")
        String descricao,
        @Schema(description = "Categoria do exercício", example = "FORCA")
        CategoriaExercicio categoria,
        @Schema(description = "Grupo muscular trabalhado", example = "PEITO")
        GrupoMuscular grupoMuscular
) {}

