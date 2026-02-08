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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        Treino treino = treinoRepository
                .findByIdAndPlanoTreinoUsuario(treinoId, usuario)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão"));

        RegistroTreino registro = new RegistroTreino();
        registro.setTreino(treino);
        registro.setPlanoTreino(treino.getPlanoTreino());
        registro.setDataExecucao(request.dataExecucao());

        RegistroTreino savedRegistro = registroTreinoRepository.save(registro);

        if (request.exercicios() != null && !request.exercicios().isEmpty()) {
            List<ExercicioExecutado> exerciciosExecutados = new ArrayList<>();

            for (ExercicioExecutadoRequest exReq : request.exercicios()) {
                ExercicioTreino exercicioTreino = exercicioTreinoRepository.findById(exReq.exercicioTreinoId())
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercício não encontrado"));

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

    public List<RegistroTreinoResponse> listarDoUsuario(LocalDateTime data) {
        Usuario usuario = getUsuarioAutenticado();

        List<RegistroTreino> registros;
        if (data != null) {
            LocalDateTime inicioDoDia = data.toLocalDate().atStartOfDay();
            LocalDateTime fimDoDia = data.toLocalDate().atTime(23, 59, 59);
            registros = registroTreinoRepository.findByPlanoTreinoUsuarioAndDataExecucaoBetween(
                    usuario, inicioDoDia, fimDoDia);
        } else {
            registros = registroTreinoRepository.findByPlanoTreinoUsuario(usuario);
        }

        return registros.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public RegistroTreinoResponse buscarDoUsuario(UUID id) {
        Usuario usuario = getUsuarioAutenticado();
        RegistroTreino registro = registroTreinoRepository.findByIdAndPlanoTreinoUsuario(id, usuario)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado"));
        return toResponse(registro);
    }

    private Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado"));
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