package com.workoutrack.WorkoutTracker.service;

import com.workoutrack.WorkoutTracker.domain.treino.ExercicioTreino;
import com.workoutrack.WorkoutTracker.repository.ExercicioTreinoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExercicioTreinoService {

    private final ExercicioTreinoRepository exercicioTreinoRepository;

    public ExercicioTreino save(ExercicioTreino exercicioTreino) {
        return exercicioTreinoRepository.save(exercicioTreino);
    }

    public Optional<ExercicioTreino> findById(UUID id) {
        return exercicioTreinoRepository.findById(id);
    }

    public List<ExercicioTreino> findAll() {
        return exercicioTreinoRepository.findAll();
    }

    public void deleteById(UUID id) {
        exercicioTreinoRepository.deleteById(id);
    }
}