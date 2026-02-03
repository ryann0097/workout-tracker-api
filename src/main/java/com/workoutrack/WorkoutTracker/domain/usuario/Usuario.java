package com.workoutrack.WorkoutTracker.domain.usuario;

import com.workoutrack.WorkoutTracker.domain.perfil.PerfilTreino;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Usuario {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String email;

    private String senha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "perfil_treino_id")
    private PerfilTreino perfilTreino;
}
