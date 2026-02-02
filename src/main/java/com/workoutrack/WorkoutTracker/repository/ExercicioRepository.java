package com.workoutrack.WorkoutTracker.repository;

import com.workoutrack.WorkoutTracker.domain.treino.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExercicioRepository extends JpaRepository<Exercicio, UUID> {
}
