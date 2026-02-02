package com.workoutrack.WorkoutTracker.controller;

import com.workoutrack.WorkoutTracker.dto.treino.TreinoRequest;
import com.workoutrack.WorkoutTracker.dto.treino.TreinoResponse;
import com.workoutrack.WorkoutTracker.service.TreinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/planos/{planoId}/treinos")
@RequiredArgsConstructor
public class TreinoController {

    private final TreinoService treinoService;

    @PostMapping
    public TreinoResponse criar(
            @PathVariable UUID planoId,
            @RequestBody @Valid TreinoRequest request
    ) {
        return treinoService.criar(planoId, request);
    }

    @GetMapping
    public List<TreinoResponse> listar(@PathVariable UUID planoId) {
        return treinoService.listarPorPlano(planoId);
    }

    @PutMapping("/{treinoId}")
    public TreinoResponse atualizar(
            @PathVariable UUID planoId,
            @PathVariable UUID treinoId,
            @RequestBody @Valid TreinoRequest request
    ) {
        return treinoService.atualizar(planoId, treinoId, request);
    }

    @DeleteMapping("/{treinoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(
            @PathVariable UUID planoId,
            @PathVariable UUID treinoId
    ) {
        treinoService.deletar(planoId, treinoId);
    }
}
