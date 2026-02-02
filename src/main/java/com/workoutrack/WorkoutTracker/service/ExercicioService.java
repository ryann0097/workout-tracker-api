package com.workoutrack.WorkoutTracker.service;

import com.workoutrack.WorkoutTracker.domain.treino.Exercicio;
import com.workoutrack.WorkoutTracker.dto.treino.ExercicioRequest;
import com.workoutrack.WorkoutTracker.dto.treino.ExercicioResponse;
import com.workoutrack.WorkoutTracker.repository.ExercicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExercicioService {

    private final ExercicioRepository exercicioRepository;

    public Exercicio save(Exercicio exercicio) {
        return exercicioRepository.save(exercicio);
    }

    public Optional<Exercicio> findById(UUID id) {
        return exercicioRepository.findById(id);
    }

    public List<Exercicio> findAll() {
        return exercicioRepository.findAll();
    }

    public void deleteById(UUID id) {
        exercicioRepository.deleteById(id);
    }

    // Métodos do Controller
    public List<ExercicioResponse> listarTodos() {
        return exercicioRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ExercicioResponse buscarPorId(UUID id) {
        Exercicio exercicio = exercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercício não encontrado"));
        return toResponse(exercicio);
    }

    public ExercicioResponse criar(ExercicioRequest request) {
        Exercicio exercicio = new Exercicio();
        exercicio.setNome(request.nome());
        exercicio.setDescricao(request.descricao());
        exercicio.setCategoriaExercicio(request.categoria());
        exercicio.setGrupoMuscular(request.grupoMuscular());
        
        Exercicio saved = exercicioRepository.save(exercicio);
        return toResponse(saved);
    }

    public ExercicioResponse atualizar(UUID id, ExercicioRequest request) {
        Exercicio exercicio = exercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercício não encontrado"));
        
        exercicio.setNome(request.nome());
        exercicio.setDescricao(request.descricao());
        exercicio.setCategoriaExercicio(request.categoria());
        exercicio.setGrupoMuscular(request.grupoMuscular());
        
        Exercicio updated = exercicioRepository.save(exercicio);
        return toResponse(updated);
    }

    public void deletar(UUID id) {
        if (!exercicioRepository.existsById(id)) {
            throw new RuntimeException("Exercício não encontrado");
        }
        exercicioRepository.deleteById(id);
    }

    private ExercicioResponse toResponse(Exercicio exercicio) {
        return new ExercicioResponse(
                exercicio.getId(),
                exercicio.getNome(),
                exercicio.getDescricao(),
                exercicio.getCategoriaExercicio(),
                exercicio.getGrupoMuscular()
        );
    }
}