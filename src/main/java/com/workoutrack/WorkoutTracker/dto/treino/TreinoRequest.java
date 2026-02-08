package com.workoutrack.WorkoutTracker.dto.treino;

import com.workoutrack.WorkoutTracker.domain.treino.DiaSemana;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Dados para criar ou atualizar um treino dentro de um plano")
public record TreinoRequest(
        @Schema(description = "Nome do treino", example = "Peito e Tríceps")
        String nome,
        @Schema(description = "Dia da semana para executar o treino", example = "SEGUNDA")
        DiaSemana diaSemana,
        @Schema(description = "Lista de exercícios que fazem parte do treino")
        List<ExercicioTreinoRequest> exercicios
) {}

