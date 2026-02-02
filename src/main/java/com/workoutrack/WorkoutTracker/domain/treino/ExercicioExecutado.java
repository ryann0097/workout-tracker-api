package com.workoutrack.WorkoutTracker.domain.treino;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
public class ExercicioExecutado {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne
    private RegistroTreino registroTreino;

    @ManyToOne
    private ExercicioTreino exercicioTreino;

    private Integer seriesRealizadas;
    private Integer repeticoesRealizadas;
    private Double pesoUtilizado;
}

