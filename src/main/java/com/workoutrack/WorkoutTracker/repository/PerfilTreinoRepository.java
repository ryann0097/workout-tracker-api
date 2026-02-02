package com.workoutrack.WorkoutTracker.repository;

import com.workoutrack.WorkoutTracker.domain.perfil.PerfilTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PerfilTreinoRepository extends JpaRepository<PerfilTreino, UUID> {

}
