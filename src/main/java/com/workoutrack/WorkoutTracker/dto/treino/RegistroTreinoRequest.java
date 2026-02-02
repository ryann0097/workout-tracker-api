package com.workoutrack.WorkoutTracker.dto.treino;

import java.sql.Timestamp;
import java.util.List;

public record RegistroTreinoRequest(
        Timestamp dataExecucao,
        List<ExercicioExecutadoRequest> exercicios
) {}
