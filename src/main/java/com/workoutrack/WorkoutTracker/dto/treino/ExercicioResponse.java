package com.workoutrack.WorkoutTracker.dto.treino;

import com.workoutrack.WorkoutTracker.domain.treino.CategoriaExercicio;
import com.workoutrack.WorkoutTracker.domain.treino.GrupoMuscular;

import java.util.UUID;

public record ExercicioResponse(
        UUID id,
        String nome,
        String descricao,
        CategoriaExercicio categoria,
        GrupoMuscular grupoMuscular
) {}

