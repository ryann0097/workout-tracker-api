package com.workoutrack.WorkoutTracker.dto.treino;

import java.time.LocalDateTime;
import java.util.List;

public record RegistroTreinoRequest(
        LocalDateTime dataExecucao,
        List<ExercicioExecutadoRequest> exercicios
) {}
