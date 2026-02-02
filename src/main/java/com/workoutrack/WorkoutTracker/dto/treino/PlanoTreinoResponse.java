package com.workoutrack.WorkoutTracker.dto.treino;

import java.util.UUID;

public record PlanoTreinoResponse(
        UUID id,
        String nome,
        Integer semanasDuracao
) {}
