package com.workoutrack.WorkoutTracker.repository;

import com.workoutrack.WorkoutTracker.domain.treino.Treino;
import com.workoutrack.WorkoutTracker.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, UUID> {
    @Query("SELECT t FROM Treino t WHERE t.planoTreino.id = :planoId")
    List<Treino> findByPlanoTreinoId(UUID planoId);
    
    @Query("SELECT t FROM Treino t WHERE t.id = :treinoId AND t.planoTreino.id = :planoId")
    Optional<Treino> findByIdAndPlanoTreinoId(UUID treinoId, UUID planoId);

    Optional<Treino> findByIdAndPlanoTreinoUsuario(UUID id, Usuario usuario);
}
