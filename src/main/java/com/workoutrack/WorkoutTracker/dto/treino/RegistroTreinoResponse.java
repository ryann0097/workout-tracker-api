package com.workoutrack.WorkoutTracker.dto.treino;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegistroTreinoResponse(
        UUID id,
        LocalDateTime dataExecucao,
        String nomeTreino
) {}
