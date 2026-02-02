package com.workoutrack.WorkoutTracker.service;

import com.workoutrack.WorkoutTracker.domain.perfil.PerfilTreino;
import com.workoutrack.WorkoutTracker.repository.PerfilTreinoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerfilTreinoService {

    private final PerfilTreinoRepository perfilTreinoRepository;

    public PerfilTreino save(PerfilTreino perfilTreino) {
        return perfilTreinoRepository.save(perfilTreino);
    }

    public Optional<PerfilTreino> findById(UUID id) {
        return perfilTreinoRepository.findById(id);
    }

    public List<PerfilTreino> findAll() {
        return perfilTreinoRepository.findAll();
    }

    public void deleteById(UUID id) {
        perfilTreinoRepository.deleteById(id);
    }
}