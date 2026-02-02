package com.workoutrack.WorkoutTracker.dto.treino;

import java.util.UUID;

public record ExercicioTreinoRequest(
        UUID exercicioId,
        Integer ordem,
        Integer series,
        Integer repeticoes,
        Double peso
) {}
