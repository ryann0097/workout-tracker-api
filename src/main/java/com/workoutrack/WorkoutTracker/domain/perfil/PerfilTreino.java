package com.workoutrack.WorkoutTracker.domain.perfil;

import com.workoutrack.WorkoutTracker.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class PerfilTreino {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String nome;

    private LocalDateTime dataNascimento;

    private Double peso;

    private Double altura;

    private Double IMC;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
