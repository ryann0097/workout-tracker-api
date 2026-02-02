package com.workoutrack.WorkoutTracker.service;

import com.workoutrack.WorkoutTracker.domain.treino.ExercicioExecutado;
import com.workoutrack.WorkoutTracker.domain.treino.ExercicioTreino;
import com.workoutrack.WorkoutTracker.domain.treino.RegistroTreino;
import com.workoutrack.WorkoutTracker.domain.treino.Treino;
import com.workoutrack.WorkoutTracker.domain.usuario.Usuario;
import com.workoutrack.WorkoutTracker.dto.treino.ExercicioExecutadoRequest;
import com.workoutrack.WorkoutTracker.dto.treino.RegistroTreinoRequest;
import com.workoutrack.WorkoutTracker.dto.treino.RegistroTreinoResponse;
import com.workoutrack.WorkoutTracker.repository.ExercicioTreinoRepository;
import com.workoutrack.WorkoutTracker.repository.RegistroTreinoRepository;
import com.workoutrack.WorkoutTracker.repository.TreinoRepository;
import com.workoutrack.WorkoutTracker.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistroTreinoService {

    private final RegistroTreinoRepository registroTreinoRepository;
    private final TreinoRepository treinoRepository;
    private final ExercicioTreinoRepository exercicioTreinoRepository;
    private final UsuarioRepository usuarioRepository;

    public RegistroTreino save(RegistroTreino registroTreino) {
        return registroTreinoRepository.save(registroTreino);
    }

    public Optional<RegistroTreino> findById(UUID id) {
        return registroTreinoRepository.findById(id);
    }

    public List<RegistroTreino> findAll() {
        return registroTreinoRepository.findAll();
    }

    public void deleteById(UUID id) {
        registroTreinoRepository.deleteById(id);
    }

    // Métodos do Controller
    @Transactional
    public RegistroTreinoResponse registrar(UUID treinoId, RegistroTreinoRequest request) {
        Usuario usuario = getUsuarioAutenticado();
        
        Treino treino = treinoRepository.findById(treinoId)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado"));
        
        // Verificar se o treino pertence ao usuário
        if (treino.getPlanoTreino() == null || 
            !treino.getPlanoTreino().getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Treino não pertence ao usuário");
        }
        
        RegistroTreino registro = new RegistroTreino();
        registro.setTreino(treino);
        registro.setPlanoTreino(treino.getPlanoTreino());
        registro.setDataExecucao(request.dataExecucao());
        
        RegistroTreino savedRegistro = registroTreinoRepository.save(registro);
        
        // Criar os exercícios executados
        if (request.exercicios() != null && !request.exercicios().isEmpty()) {
            List<ExercicioExecutado> exerciciosExecutados = new ArrayList<>();
            for (ExercicioExecutadoRequest exReq : request.exercicios()) {
                ExercicioTreino exercicioTreino = exercicioTreinoRepository.findById(exReq.exercicioTreinoId())
                        .orElseThrow(() -> new RuntimeException("Exercício do treino não encontrado"));
                
                ExercicioExecutado exercicioExecutado = new ExercicioExecutado();
                exercicioExecutado.setRegistroTreino(savedRegistro);
                exercicioExecutado.setExercicioTreino(exercicioTreino);
                exercicioExecutado.setSeriesRealizadas(exReq.seriesRealizadas());
                exercicioExecutado.setRepeticoesRealizadas(exReq.repeticoesRealizadas());
                exercicioExecutado.setPesoUtilizado(exReq.pesoUtilizado());
                
                exerciciosExecutados.add(exercicioExecutado);
            }
            savedRegistro.setExerciciosExecutados(exerciciosExecutados);
            savedRegistro = registroTreinoRepository.save(savedRegistro);
        }
        
        return toResponse(savedRegistro);
    }

    public List<RegistroTreinoResponse> listarDoUsuario(LocalDate data) {
        Usuario usuario = getUsuarioAutenticado();
        
        List<RegistroTreino> registros;
        if (data != null) {
            registros = registroTreinoRepository.findByUsuarioAndData(usuario, data);
        } else {
            registros = registroTreinoRepository.findByUsuario(usuario);
        }
        
        return registros.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public RegistroTreinoResponse buscarDoUsuario(UUID id) {
        Usuario usuario = getUsuarioAutenticado();
        RegistroTreino registro = registroTreinoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Registro de treino não encontrado"));
        return toResponse(registro);
    }

    private Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    private RegistroTreinoResponse toResponse(RegistroTreino registro) {
        String nomeTreino = registro.getTreino() != null ? registro.getTreino().getNome() : null;
        return new RegistroTreinoResponse(
                registro.getId(),
                registro.getDataExecucao(),
                nomeTreino
        );
    }
}