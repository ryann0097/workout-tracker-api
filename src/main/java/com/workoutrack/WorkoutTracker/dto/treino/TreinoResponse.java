package com.workoutrack.WorkoutTracker.dto.treino;

import com.workoutrack.WorkoutTracker.domain.treino.DiaSemana;

import java.util.List;
import java.util.UUID;

public record TreinoResponse(
        UUID id,
        String nome,
        DiaSemana diaSemana,
        List<ExercicioTreinoResponse> exercicios
) {}
