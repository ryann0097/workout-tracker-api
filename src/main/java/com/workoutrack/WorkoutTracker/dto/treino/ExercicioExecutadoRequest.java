package com.workoutrack.WorkoutTracker.dto.treino;

import java.util.UUID;

public record ExercicioExecutadoRequest(
        UUID exercicioTreinoId,
        Integer seriesRealizadas,
        Integer repeticoesRealizadas,
        Double pesoUtilizado
) {}
