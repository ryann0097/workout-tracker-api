package com.workoutrack.WorkoutTracker.dto.treino;

import com.workoutrack.WorkoutTracker.domain.treino.CategoriaExercicio;
import com.workoutrack.WorkoutTracker.domain.treino.GrupoMuscular;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para criar ou atualizar um exercício")
public record ExercicioRequest(
        @Schema(description = "Nome do exercício", example = "Supino Reto")
        String nome,
        @Schema(description = "Descrição detalhada do exercício", example = "Exercício para peitoral usando barra")
        String descricao,
        @Schema(description = "Categoria do exercício", example = "FORCA")
        CategoriaExercicio categoria,
        @Schema(description = "Grupo muscular trabalhado", example = "PEITO")
        GrupoMuscular grupoMuscular
) {}
