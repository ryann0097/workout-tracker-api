package com.workoutrack.WorkoutTracker.domain.treino;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

/**
 * Aqui, essa classe funciona como se fosse papo de um itemPedido numa classe pedido de um e-commerce.
 * */
@Getter
@Setter
@Entity
public class ExercicioTreino {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "treino_id")
    private Treino treino;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercicio_id")
    private Exercicio exercicio;

    private Integer ordemIndice;

    private Integer series;
    private Integer repeticoes;
    private Double peso;
}
