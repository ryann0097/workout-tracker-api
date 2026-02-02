package com.workoutrack.WorkoutTracker.controller;

import com.workoutrack.WorkoutTracker.dto.treino.ExercicioRequest;
import com.workoutrack.WorkoutTracker.dto.treino.ExercicioResponse;
import com.workoutrack.WorkoutTracker.service.ExercicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exercicios")
@RequiredArgsConstructor
public class ExercicioController {

    private final ExercicioService service;

    @GetMapping
    public List<ExercicioResponse> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ExercicioResponse buscar(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ExercicioResponse criar(@RequestBody @Valid ExercicioRequest request) {
        return service.criar(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ExercicioResponse atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid ExercicioRequest request
    ) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public void deletar(@PathVariable UUID id) {
        service.deletar(id);
    }
}
