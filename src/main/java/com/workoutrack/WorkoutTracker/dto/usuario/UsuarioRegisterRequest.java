package com.workoutrack.WorkoutTracker.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Dados para registrar um novo usuário")
public record UsuarioRegisterRequest(
        @Schema(description = "Email do usuário", example = "usuario@exemplo.com")
        String email,
        @Schema(description = "Senha do usuário", example = "senha123")
        String senha,
        @Schema(description = "Nome completo do usuário", example = "João Silva")
        String nome,
        @Schema(description = "Data de nascimento do usuário", example = "1990-05-15T00:00:00")
        LocalDateTime dataNascimento,
        @Schema(description = "Peso do usuário em kg", example = "75.5")
        Double peso,
        @Schema(description = "Altura do usuário em cm", example = "180")
        Double altura
) {}