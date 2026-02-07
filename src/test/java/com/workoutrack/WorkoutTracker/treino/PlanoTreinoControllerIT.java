package com.workoutrack.WorkoutTracker.treino;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workoutrack.WorkoutTracker.dto.treino.PlanoTreinoRequest;
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
class PlanoTreinoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criarPlanoTreinoComUsuarioAutenticado() throws Exception {
        String token = registrarELogarParaToken();

        PlanoTreinoRequest request = new PlanoTreinoRequest(
                "Plano Hipertrofia",
                12
        );

        mockMvc.perform(post("/planos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Plano Hipertrofia"))
                .andExpect(jsonPath("$.semanasDuracao").value(12))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void criarPlanoTreinoSemTokenDeveRetornar403() throws Exception {
        PlanoTreinoRequest request = new PlanoTreinoRequest(
                "Plano Teste",
                8
        );

        mockMvc.perform(post("/planos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void listarPlanosTreinoDoUsuarioAutenticado() throws Exception {
        String token = registrarELogarParaToken();

        PlanoTreinoRequest request1 = new PlanoTreinoRequest("Plano A", 8);
        PlanoTreinoRequest request2 = new PlanoTreinoRequest("Plano B", 12);

        mockMvc.perform(post("/planos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/planos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/planos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void usuarioNaoPodeListarPlanosDeOutroUsuario() throws Exception {
        String token1 = registrarELogarParaToken();
        String token2 = registrarELogarParaToken();

        PlanoTreinoRequest request = new PlanoTreinoRequest("Plano Usuario 1", 10);

        mockMvc.perform(post("/planos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/planos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void usuarioNaoPodeAtualizarPlanoDeOutroUsuario() throws Exception {
        String token1 = registrarELogarParaToken();
        String token2 = registrarELogarParaToken();

        PlanoTreinoRequest createRequest = new PlanoTreinoRequest("Plano Original", 8);

        String responseBody = mockMvc.perform(post("/planos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID planoId = UUID.fromString(
                objectMapper.readTree(responseBody).get("id").asText()
        );

        PlanoTreinoRequest updateRequest = new PlanoTreinoRequest("Plano Modificado", 12);

        mockMvc.perform(put("/planos/" + planoId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void usuarioNaoPodeExcluirPlanoDeOutroUsuario() throws Exception {
        String token1 = registrarELogarParaToken();
        String token2 = registrarELogarParaToken();

        PlanoTreinoRequest createRequest = new PlanoTreinoRequest("Plano para Excluir", 8);

        String responseBody = mockMvc.perform(post("/planos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID planoId = UUID.fromString(
                objectMapper.readTree(responseBody).get("id").asText()
        );

        mockMvc.perform(delete("/planos/" + planoId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token2))
                .andExpect(status().is4xxClientError());
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
