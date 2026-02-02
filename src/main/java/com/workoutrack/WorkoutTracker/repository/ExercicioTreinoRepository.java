package com.workoutrack.WorkoutTracker.repository;

import com.workoutrack.WorkoutTracker.domain.treino.ExercicioTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExercicioTreinoRepository extends JpaRepository<ExercicioTreino, UUID> {

}
