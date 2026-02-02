package com.workoutrack.WorkoutTracker.domain.treino;

import com.workoutrack.WorkoutTracker.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class PlanoTreino {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String nome;
    private Integer semanasDuracao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany
    @JoinColumn(name = "plano_treino_id")
    private List<Treino> treinos;
}
