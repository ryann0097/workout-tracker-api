package com.workoutrack.WorkoutTracker.dto.treino;

public record PlanoTreinoRequest(
        String nome,
        Integer semanasDuracao
) {}
