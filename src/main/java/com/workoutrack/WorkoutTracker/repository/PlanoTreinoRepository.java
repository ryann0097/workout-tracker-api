package com.workoutrack.WorkoutTracker.repository;

import com.workoutrack.WorkoutTracker.domain.treino.PlanoTreino;
import com.workoutrack.WorkoutTracker.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlanoTreinoRepository extends JpaRepository<PlanoTreino, UUID> {
    List<PlanoTreino> findByUsuario(Usuario usuario);
    Optional<PlanoTreino> findByIdAndUsuario(UUID id, Usuario usuario);
}
