package com.workoutrack.WorkoutTracker.repository;

import com.workoutrack.WorkoutTracker.domain.treino.RegistroTreino;
import com.workoutrack.WorkoutTracker.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistroTreinoRepository extends JpaRepository<RegistroTreino, UUID>{
    @Query("SELECT r FROM RegistroTreino r WHERE r.planoTreino.usuario = :usuario")
    List<RegistroTreino> findByUsuario(Usuario usuario);
    
    @Query("SELECT r FROM RegistroTreino r WHERE r.planoTreino.usuario = :usuario AND CAST(r.dataExecucao AS date) = :data")
    List<RegistroTreino> findByUsuarioAndData(Usuario usuario, LocalDate data);
    
    @Query("SELECT r FROM RegistroTreino r WHERE r.id = :id AND r.planoTreino.usuario = :usuario")
    Optional<RegistroTreino> findByIdAndUsuario(UUID id, Usuario usuario);
}
