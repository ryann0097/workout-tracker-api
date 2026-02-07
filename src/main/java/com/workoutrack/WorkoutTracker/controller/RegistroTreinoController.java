package com.workoutrack.WorkoutTracker.controller;

import com.workoutrack.WorkoutTracker.dto.treino.RegistroTreinoRequest;
import com.workoutrack.WorkoutTracker.dto.treino.RegistroTreinoResponse;
import com.workoutrack.WorkoutTracker.service.RegistroTreinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/registros")
@RequiredArgsConstructor
public class RegistroTreinoController {

    private final RegistroTreinoService registroTreinoService;

    @PostMapping("/treinos/{treinoId}")
    public RegistroTreinoResponse registrar(
            @PathVariable UUID treinoId,
            @RequestBody @Valid RegistroTreinoRequest request
    ) {
        return registroTreinoService.registrar(treinoId, request);
    }

    @GetMapping
    public List<RegistroTreinoResponse> listar(
            @RequestParam(required = false) LocalDateTime data
    ) {
        return registroTreinoService.listarDoUsuario(data);
    }

    @GetMapping("/{id}")
    public RegistroTreinoResponse buscar(@PathVariable UUID id) {
        return registroTreinoService.buscarDoUsuario(id);
    }
}
