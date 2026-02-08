package com.workoutrack.WorkoutTracker.domain.treino;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Treino {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    @ManyToOne
    @JoinColumn(name = "plano_treino_id")
    private PlanoTreino planoTreino;

    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExercicioTreino> exercicios;

}
