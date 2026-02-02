package com.workoutrack.WorkoutTracker.domain.treino;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Exercicio {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String nome;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private CategoriaExercicio categoriaExercicio;

    @Enumerated(EnumType.STRING)
    private GrupoMuscular grupoMuscular;
}
