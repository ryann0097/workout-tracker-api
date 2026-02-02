package com.workoutrack.WorkoutTracker.service;

import com.workoutrack.WorkoutTracker.domain.treino.Exercicio;
import com.workoutrack.WorkoutTracker.domain.treino.ExercicioTreino;
import com.workoutrack.WorkoutTracker.domain.treino.PlanoTreino;
import com.workoutrack.WorkoutTracker.domain.treino.Treino;
import com.workoutrack.WorkoutTracker.domain.usuario.Usuario;
import com.workoutrack.WorkoutTracker.dto.treino.ExercicioTreinoRequest;
import com.workoutrack.WorkoutTracker.dto.treino.ExercicioTreinoResponse;
import com.workoutrack.WorkoutTracker.dto.treino.TreinoRequest;
import com.workoutrack.WorkoutTracker.dto.treino.TreinoResponse;
import com.workoutrack.WorkoutTracker.repository.ExercicioRepository;
import com.workoutrack.WorkoutTracker.repository.PlanoTreinoRepository;
import com.workoutrack.WorkoutTracker.repository.TreinoRepository;
import com.workoutrack.WorkoutTracker.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreinoService {

    private final TreinoRepository treinoRepository;
    private final PlanoTreinoRepository planoTreinoRepository;
    private final ExercicioRepository exercicioRepository;
    private final UsuarioRepository usuarioRepository;

    public Treino save(Treino treino) {
        return treinoRepository.save(treino);
    }

    public Optional<Treino> findById(UUID id) {
        return treinoRepository.findById(id);
    }

    public List<Treino> findAll() {
        return treinoRepository.findAll();
    }

    public void deleteById(UUID id) {
        treinoRepository.deleteById(id);
    }

    // Métodos do Controller
    @Transactional
    public TreinoResponse criar(UUID planoId, TreinoRequest request) {
        Usuario usuario = getUsuarioAutenticado();
        PlanoTreino planoTreino = planoTreinoRepository.findByIdAndUsuario(planoId, usuario)
                .orElseThrow(() -> new RuntimeException("Plano de treino não encontrado"));
        
        Treino treino = new Treino();
        treino.setNome(request.nome());
        treino.setDiaSemana(request.diaSemana());
        treino.setPlanoTreino(planoTreino);
        
        Treino savedTreino = treinoRepository.save(treino);
        
        // Criar os exercícios do treino
        if (request.exercicios() != null && !request.exercicios().isEmpty()) {
            List<ExercicioTreino> exerciciosTreino = new ArrayList<>();
            for (ExercicioTreinoRequest exReq : request.exercicios()) {
                Exercicio exercicio = exercicioRepository.findById(exReq.exercicioId())
                        .orElseThrow(() -> new RuntimeException("Exercício não encontrado"));
                
                ExercicioTreino exercicioTreino = new ExercicioTreino();
                exercicioTreino.setTreino(savedTreino);
                exercicioTreino.setExercicio(exercicio);
                exercicioTreino.setOrdemIndice(exReq.ordem());
                exercicioTreino.setSeries(exReq.series());
                exercicioTreino.setRepeticoes(exReq.repeticoes());
                exercicioTreino.setPeso(exReq.peso());
                
                exerciciosTreino.add(exercicioTreino);
            }
            savedTreino.setExercicios(exerciciosTreino);
            savedTreino = treinoRepository.save(savedTreino);
        }
        
        return toResponse(savedTreino);
    }

    public List<TreinoResponse> listarPorPlano(UUID planoId) {
        Usuario usuario = getUsuarioAutenticado();
        // Verifica se o plano pertence ao usuário
        planoTreinoRepository.findByIdAndUsuario(planoId, usuario)
                .orElseThrow(() -> new RuntimeException("Plano de treino não encontrado"));
        
        return treinoRepository.findByPlanoTreinoId(planoId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TreinoResponse atualizar(UUID planoId, UUID treinoId, TreinoRequest request) {
        Usuario usuario = getUsuarioAutenticado();
        // Verifica se o plano pertence ao usuário
        planoTreinoRepository.findByIdAndUsuario(planoId, usuario)
                .orElseThrow(() -> new RuntimeException("Plano de treino não encontrado"));
        
        Treino treino = treinoRepository.findByIdAndPlanoTreinoId(treinoId, planoId)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado"));
        
        treino.setNome(request.nome());
        treino.setDiaSemana(request.diaSemana());
        
        // Limpar exercícios antigos e adicionar novos
        if (treino.getExercicios() != null) {
            treino.getExercicios().clear();
        }
        
        if (request.exercicios() != null && !request.exercicios().isEmpty()) {
            List<ExercicioTreino> exerciciosTreino = new ArrayList<>();
            for (ExercicioTreinoRequest exReq : request.exercicios()) {
                Exercicio exercicio = exercicioRepository.findById(exReq.exercicioId())
                        .orElseThrow(() -> new RuntimeException("Exercício não encontrado"));
                
                ExercicioTreino exercicioTreino = new ExercicioTreino();
                exercicioTreino.setTreino(treino);
                exercicioTreino.setExercicio(exercicio);
                exercicioTreino.setOrdemIndice(exReq.ordem());
                exercicioTreino.setSeries(exReq.series());
                exercicioTreino.setRepeticoes(exReq.repeticoes());
                exercicioTreino.setPeso(exReq.peso());
                
                exerciciosTreino.add(exercicioTreino);
            }
            treino.setExercicios(exerciciosTreino);
        }
        
        Treino updated = treinoRepository.save(treino);
        return toResponse(updated);
    }

    public void deletar(UUID planoId, UUID treinoId) {
        Usuario usuario = getUsuarioAutenticado();
        // Verifica se o plano pertence ao usuário
        planoTreinoRepository.findByIdAndUsuario(planoId, usuario)
                .orElseThrow(() -> new RuntimeException("Plano de treino não encontrado"));
        
        Treino treino = treinoRepository.findByIdAndPlanoTreinoId(treinoId, planoId)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado"));
        
        treinoRepository.delete(treino);
    }

    private Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    private TreinoResponse toResponse(Treino treino) {
        List<ExercicioTreinoResponse> exerciciosResponse = null;
        if (treino.getExercicios() != null) {
            exerciciosResponse = treino.getExercicios().stream()
                    .map(et -> new ExercicioTreinoResponse())
                    .collect(Collectors.toList());
        }
        
        return new TreinoResponse(
                treino.getId(),
                treino.getNome(),
                treino.getDiaSemana(),
                exerciciosResponse
        );
    }
}