package com.workoutrack.WorkoutTracker.controller;

import com.workoutrack.WorkoutTracker.dto.treino.PlanoTreinoRequest;
import com.workoutrack.WorkoutTracker.dto.treino.PlanoTreinoResponse;
import com.workoutrack.WorkoutTracker.service.PlanoTreinoService;
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
@RequestMapping("/planos")
@RequiredArgsConstructor
@Tag(name = "Planos de Treino", description = "Endpoints para gerenciar planos de treino dos usuários")
public class PlanoTreinoController {

    private final PlanoTreinoService planoTreinoService;

    @PostMapping
    @Operation(summary = "Criar novo plano de treino", description = "Cria um novo plano de treino para o usuário autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano de treino criado com sucesso",
                    content = @Content(schema = @Schema(implementation = PlanoTreinoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public PlanoTreinoResponse criar(@RequestBody @Valid PlanoTreinoRequest request) {
        return planoTreinoService.criar(request);
    }

    @GetMapping
    @Operation(summary = "Listar planos de treino", description = "Retorna todos os planos de treino do usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Lista de planos retornada com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlanoTreinoResponse.class))))
    public List<PlanoTreinoResponse> listar() {
        return planoTreinoService.listarDoUsuario();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar plano de treino por ID", description = "Retorna os detalhes de um plano de treino específico do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano de treino encontrado",
                    content = @Content(schema = @Schema(implementation = PlanoTreinoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Plano de treino não encontrado")
    })
    public PlanoTreinoResponse buscar(@PathVariable UUID id) {
        return planoTreinoService.buscarDoUsuario(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar plano de treino", description = "Atualiza os dados de um plano de treino existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano de treino atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = PlanoTreinoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Plano de treino não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public PlanoTreinoResponse atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid PlanoTreinoRequest request
    ) {
        return planoTreinoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar plano de treino", description = "Remove um plano de treino e seus treinos associados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plano de treino deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano de treino não encontrado")
    })
    public void deletar(@PathVariable UUID id) {
        planoTreinoService.deletar(id);
    }
}

