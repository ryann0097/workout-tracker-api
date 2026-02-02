package com.workoutrack.WorkoutTracker.dto.treino;

import com.workoutrack.WorkoutTracker.domain.treino.DiaSemana;

import java.util.List;

public record TreinoRequest(
        String nome,
        DiaSemana diaSemana,
        List<ExercicioTreinoRequest> exercicios
) {}

