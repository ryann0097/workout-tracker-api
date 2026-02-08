package com.workoutrack.WorkoutTracker.repository;

import com.workoutrack.WorkoutTracker.domain.treino.RegistroTreino;
import com.workoutrack.WorkoutTracker.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistroTreinoRepository extends JpaRepository<RegistroTreino, UUID>{
    @Query("SELECT r FROM RegistroTreino r WHERE r.planoTreino.usuario = :usuario")
    List<RegistroTreino> findByUsuario(Usuario usuario);
    
    @Query("SELECT r FROM RegistroTreino r WHERE r.planoTreino.usuario = :usuario AND CAST(r.dataExecucao AS date) = :data")
    List<RegistroTreino> findByUsuarioAndData(Usuario usuario, LocalDate data);

    List<RegistroTreino> findByPlanoTreinoUsuarioAndDataExecucaoBetween(
            Usuario usuario,
            LocalDateTime inicio,
            LocalDateTime fim
    );

    List<RegistroTreino> findByPlanoTreinoUsuario(Usuario usuario);

    Optional<RegistroTreino> findByIdAndPlanoTreinoUsuario(UUID id, Usuario usuario);
}
