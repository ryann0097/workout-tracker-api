package com.workoutrack.WorkoutTracker.dto.treino;

import java.sql.Timestamp;
import java.util.UUID;

public record RegistroTreinoResponse(
        UUID id,
        Timestamp dataExecucao,
        String nomeTreino
) {}
