package com.workoutrack.WorkoutTracker.service;

import com.workoutrack.WorkoutTracker.domain.treino.PlanoTreino;
import com.workoutrack.WorkoutTracker.domain.usuario.Usuario;
import com.workoutrack.WorkoutTracker.dto.treino.PlanoTreinoRequest;
import com.workoutrack.WorkoutTracker.dto.treino.PlanoTreinoResponse;
import com.workoutrack.WorkoutTracker.repository.PlanoTreinoRepository;
import com.workoutrack.WorkoutTracker.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanoTreinoService {

    private final PlanoTreinoRepository planoTreinoRepository;
    private final UsuarioRepository usuarioRepository;

    public PlanoTreino save(PlanoTreino planoTreino) {
        return planoTreinoRepository.save(planoTreino);
    }

    public Optional<PlanoTreino> findById(UUID id) {
        return planoTreinoRepository.findById(id);
    }

    public List<PlanoTreino> findAll() {
        return planoTreinoRepository.findAll();
    }

    public void deleteById(UUID id) {
        planoTreinoRepository.deleteById(id);
    }

    // Métodos do Controller
    public PlanoTreinoResponse criar(PlanoTreinoRequest request) {
        Usuario usuario = getUsuarioAutenticado();
        
        PlanoTreino planoTreino = new PlanoTreino();
        planoTreino.setNome(request.nome());
        planoTreino.setSemanasDuracao(request.semanasDuracao());
        planoTreino.setUsuario(usuario);
        
        PlanoTreino saved = planoTreinoRepository.save(planoTreino);
        return toResponse(saved);
    }

    public List<PlanoTreinoResponse> listarDoUsuario() {
        Usuario usuario = getUsuarioAutenticado();
        return planoTreinoRepository.findByUsuario(usuario).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PlanoTreinoResponse buscarDoUsuario(UUID id) {
        Usuario usuario = getUsuarioAutenticado();
        PlanoTreino planoTreino = planoTreinoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Plano de treino não encontrado"));
        return toResponse(planoTreino);
    }

    public PlanoTreinoResponse atualizar(UUID id, PlanoTreinoRequest request) {
        Usuario usuario = getUsuarioAutenticado();
        PlanoTreino planoTreino = planoTreinoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Plano de treino não encontrado"));
        
        planoTreino.setNome(request.nome());
        planoTreino.setSemanasDuracao(request.semanasDuracao());
        
        PlanoTreino updated = planoTreinoRepository.save(planoTreino);
        return toResponse(updated);
    }

    public void deletar(UUID id) {
        Usuario usuario = getUsuarioAutenticado();
        PlanoTreino planoTreino = planoTreinoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Plano de treino não encontrado"));
        planoTreinoRepository.delete(planoTreino);
    }

    private Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    private PlanoTreinoResponse toResponse(PlanoTreino planoTreino) {
        return new PlanoTreinoResponse(
                planoTreino.getId(),
                planoTreino.getNome(),
                planoTreino.getSemanasDuracao()
        );
    }
}