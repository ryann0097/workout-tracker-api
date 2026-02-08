package com.workoutrack.WorkoutTracker.controller;

import com.workoutrack.WorkoutTracker.dto.treino.ExercicioRequest;
import com.workoutrack.WorkoutTracker.dto.treino.ExercicioResponse;
import com.workoutrack.WorkoutTracker.service.ExercicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exercicios")
@RequiredArgsConstructor
@Tag(name = "Exercícios", description = "Endpoints para gerenciar exercícios disponíveis")
public class ExercicioController {

    private final ExercicioService service;

    @GetMapping
    @Operation(summary = "Listar todos os exercícios", description = "Retorna uma lista com todos os exercícios disponíveis")
    @ApiResponse(responseCode = "200", description = "Lista de exercícios retornada com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExercicioResponse.class))))
    public List<ExercicioResponse> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar exercício por ID", description = "Retorna os detalhes de um exercício específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercício encontrado",
                    content = @Content(schema = @Schema(implementation = ExercicioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Exercício não encontrado")
    })
    public ExercicioResponse buscar(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Criar novo exercício", description = "Cria um novo exercício no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercício criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ExercicioResponse.class))),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ExercicioResponse criar(@RequestBody @Valid ExercicioRequest request) {
        return service.criar(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Atualizar exercício", description = "Atualiza os dados de um exercício existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercício atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ExercicioResponse.class))),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Exercício não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ExercicioResponse atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid ExercicioRequest request
    ) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Deletar exercício", description = "Remove um exercício do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercício deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Exercício não encontrado")
    })
    public void deletar(@PathVariable UUID id) {
        service.deletar(id);
    }
}
