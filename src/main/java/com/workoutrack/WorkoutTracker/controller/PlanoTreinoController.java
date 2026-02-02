package com.workoutrack.WorkoutTracker.controller;

import com.workoutrack.WorkoutTracker.dto.treino.PlanoTreinoRequest;
import com.workoutrack.WorkoutTracker.dto.treino.PlanoTreinoResponse;
import com.workoutrack.WorkoutTracker.service.PlanoTreinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/planos")
@RequiredArgsConstructor
public class PlanoTreinoController {

    private final PlanoTreinoService planoTreinoService;

    @PostMapping
    public PlanoTreinoResponse criar(@RequestBody @Valid PlanoTreinoRequest request) {
        return planoTreinoService.criar(request);
    }

    @GetMapping
    public List<PlanoTreinoResponse> listar() {
        return planoTreinoService.listarDoUsuario();
    }

    @GetMapping("/{id}")
    public PlanoTreinoResponse buscar(@PathVariable UUID id) {
        return planoTreinoService.buscarDoUsuario(id);
    }

    @PutMapping("/{id}")
    public PlanoTreinoResponse atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid PlanoTreinoRequest request
    ) {
        return planoTreinoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable UUID id) {
        planoTreinoService.deletar(id);
    }
}

