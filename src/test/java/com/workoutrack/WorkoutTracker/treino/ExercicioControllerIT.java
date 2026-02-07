package com.workoutrack.WorkoutTracker.treino;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workoutrack.WorkoutTracker.domain.treino.CategoriaExercicio;
import com.workoutrack.WorkoutTracker.domain.treino.GrupoMuscular;
import com.workoutrack.WorkoutTracker.dto.treino.ExercicioRequest;
import com.workoutrack.WorkoutTracker.dto.usuario.LoginRequest;
import com.workoutrack.WorkoutTracker.dto.usuario.UsuarioRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExercicioControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void adicionarExercicioEmTreinoDoUsuario() throws Exception {
        String token = registrarELogarParaToken();

        ExercicioRequest request = new ExercicioRequest(
                "Supino Reto",
                "Exercício para peito",
                CategoriaExercicio.FORCA,
                GrupoMuscular.PEITO
        );

        mockMvc.perform(post("/exercicios")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Supino Reto"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void usuarioNaoPodeAdicionarExercicioEmTreinoDeOutroUsuario() throws Exception { // deu erro nessa função
        mockMvc.perform(post("/exercicios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ExercicioRequest(
                                "Agachamento",
                                "Exercício para pernas",
                                CategoriaExercicio.FORCA,
                                GrupoMuscular.PERNAS
                        ))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void atualizarExercicioDoUsuario() throws Exception {
        String token = registrarELogarParaToken();

        ExercicioRequest createRequest = new ExercicioRequest(
                "Rosca Direta",
                "Exercício para bíceps",
                CategoriaExercicio.FORCA,
                GrupoMuscular.BICEPS
        );

        String responseBody = mockMvc.perform(post("/exercicios")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID exercicioId = UUID.fromString(
                objectMapper.readTree(responseBody).get("id").asText()
        );

        ExercicioRequest updateRequest = new ExercicioRequest(
                "Rosca Martelo",
                "Exercício para bíceps - variação",
                CategoriaExercicio.FORCA,
                GrupoMuscular.BICEPS
        );

        mockMvc.perform(put("/exercicios/" + exercicioId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Rosca Martelo"));
    }

    @Test
    void excluirExercicioDoUsuario() throws Exception {
        String token = registrarELogarParaToken();

        ExercicioRequest createRequest = new ExercicioRequest(
                "Tríceps Testa",
                "Exercício para tríceps",
                CategoriaExercicio.FORCA,
                GrupoMuscular.TRICEPS
        );

        String responseBody = mockMvc.perform(post("/exercicios")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID exercicioId = UUID.fromString(
                objectMapper.readTree(responseBody).get("id").asText()
        );

        mockMvc.perform(delete("/exercicios/" + exercicioId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }

    private String registrarELogarParaToken() throws Exception {
        String email = "user-" + UUID.randomUUID() + "@example.com";
        String senha = "senha123";

        UsuarioRegisterRequest registerRequest = new UsuarioRegisterRequest(
                email,
                senha,
                "Usuário Teste",
                LocalDateTime.of(2000, 1, 1, 0, 0),
                80.0,
                1.8
        );

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());

        LoginRequest loginRequest = new LoginRequest(email, senha);

        String responseBody = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(responseBody).get("token").asText();
    }
}
