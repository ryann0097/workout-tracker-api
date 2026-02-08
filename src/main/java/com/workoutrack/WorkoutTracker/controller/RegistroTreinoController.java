package com.workoutrack.WorkoutTracker.controller;

import com.workoutrack.WorkoutTracker.dto.treino.RegistroTreinoRequest;
import com.workoutrack.WorkoutTracker.dto.treino.RegistroTreinoResponse;
import com.workoutrack.WorkoutTracker.service.RegistroTreinoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/registros")
@RequiredArgsConstructor
@Tag(name = "Registros de Treino", description = "Endpoints para registrar e consultar execuções de treinos")
public class RegistroTreinoController {

    private final RegistroTreinoService registroTreinoService;

    @PostMapping("/treinos/{treinoId}")
    @Operation(summary = "Registrar execução de treino", description = "Registra a execução de um treino com os exercícios realizados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Treino registrado com sucesso",
                    content = @Content(schema = @Schema(implementation = RegistroTreinoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Treino não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public RegistroTreinoResponse registrar(
            @PathVariable UUID treinoId,
            @RequestBody @Valid RegistroTreinoRequest request
    ) {
        return registroTreinoService.registrar(treinoId, request);
    }

    @GetMapping
    @Operation(summary = "Listar registros de treino", description = "Retorna os registros de treino do usuário, opcionalmente filtrado por data")
    @ApiResponse(responseCode = "200", description = "Lista de registros retornada com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RegistroTreinoResponse.class))))
    public List<RegistroTreinoResponse> listar(
            @RequestParam(required = false) LocalDateTime data
    ) {
        return registroTreinoService.listarDoUsuario(data);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar registro de treino por ID", description = "Retorna os detalhes de um registro de treino específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de treino encontrado",
                    content = @Content(schema = @Schema(implementation = RegistroTreinoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Registro de treino não encontrado")
    })
    public RegistroTreinoResponse buscar(@PathVariable UUID id) {
        return registroTreinoService.buscarDoUsuario(id);
    }
}
