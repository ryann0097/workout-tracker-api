package com.workoutrack.WorkoutTracker.controller;

import com.workoutrack.WorkoutTracker.dto.treino.TreinoRequest;
import com.workoutrack.WorkoutTracker.dto.treino.TreinoResponse;
import com.workoutrack.WorkoutTracker.service.TreinoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/planos/{planoId}/treinos")
@RequiredArgsConstructor
@Tag(name = "Treinos", description = "Endpoints para gerenciar treinos dentro de planos")
public class TreinoController {

    private final TreinoService treinoService;

    @PostMapping
    @Operation(summary = "Criar novo treino", description = "Cria um novo treino dentro de um plano de treino")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Treino criado com sucesso",
                    content = @Content(schema = @Schema(implementation = TreinoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Plano de treino não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public TreinoResponse criar(
            @PathVariable UUID planoId,
            @RequestBody @Valid TreinoRequest request
    ) {
        return treinoService.criar(planoId, request);
    }

    @GetMapping
    @Operation(summary = "Listar treinos de um plano", description = "Retorna todos os treinos de um plano de treino específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de treinos retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TreinoResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Plano de treino não encontrado")
    })
    public List<TreinoResponse> listar(@PathVariable UUID planoId) {
        return treinoService.listarPorPlano(planoId);
    }

    @PutMapping("/{treinoId}")
    @Operation(summary = "Atualizar treino", description = "Atualiza os dados de um treino existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Treino atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = TreinoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Treino ou plano não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public TreinoResponse atualizar(
            @PathVariable UUID planoId,
            @PathVariable UUID treinoId,
            @RequestBody @Valid TreinoRequest request
    ) {
        return treinoService.atualizar(planoId, treinoId, request);
    }

    @DeleteMapping("/{treinoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar treino", description = "Remove um treino de um plano")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Treino deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Treino ou plano não encontrado")
    })
    public void deletar(
            @PathVariable UUID planoId,
            @PathVariable UUID treinoId
    ) {
        treinoService.deletar(planoId, treinoId);
    }
}
