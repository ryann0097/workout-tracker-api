package com.workoutrack.WorkoutTracker.domain.treino;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter @Setter
public class RegistroTreino {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private Timestamp dataExecucao;

    @ManyToOne
    private Treino treino;

    @ManyToOne PlanoTreino planoTreino;

    @OneToMany(mappedBy = "registroTreino", cascade = ALL)
    private List<ExercicioExecutado> exerciciosExecutados;
}
