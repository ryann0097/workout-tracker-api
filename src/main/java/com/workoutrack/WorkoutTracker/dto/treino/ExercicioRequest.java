package com.workoutrack.WorkoutTracker.dto.treino;

import com.workoutrack.WorkoutTracker.domain.treino.CategoriaExercicio;
import com.workoutrack.WorkoutTracker.domain.treino.GrupoMuscular;

public record ExercicioRequest(
        String nome,
        String descricao,
        CategoriaExercicio categoria,
        GrupoMuscular grupoMuscular
) {}
