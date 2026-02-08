package com.workoutrack.WorkoutTracker.treino;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workoutrack.WorkoutTracker.domain.treino.DiaSemana;
import com.workoutrack.WorkoutTracker.dto.treino.PlanoTreinoRequest;
import com.workoutrack.WorkoutTracker.dto.treino.TreinoRequest;
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
import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TreinoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criarTreinoParaPlanoDoUsuario() throws Exception {
        String token = registrarELogarParaToken();
        UUID planoId = criarPlano(token, "Plano Teste");

        TreinoRequest request = new TreinoRequest(
                "Treino A - Peito e Tríceps",
                DiaSemana.SEGUNDA,
                Collections.emptyList()
        );

        mockMvc.perform(post("/planos/" + planoId + "/treinos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Treino A - Peito e Tríceps"))
                .andExpect(jsonPath("$.diaSemana").value("SEGUNDA"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void criarTreinoSemTokenDeveRetornar401() throws Exception {
        UUID planoId = UUID.randomUUID();

        TreinoRequest request = new TreinoRequest(
                "Treino Teste",
                DiaSemana.TERCA,
                Collections.emptyList()
        );

        mockMvc.perform(post("/planos/" + planoId + "/treinos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void usuarioNaoPodeCriarTreinoEmPlanoDeOutroUsuario() throws Exception {
        String token1 = registrarELogarParaToken();
        String token2 = registrarELogarParaToken();

        UUID planoId = criarPlano(token1, "Plano Usuario 1");

        TreinoRequest request = new TreinoRequest(
                "Treino Não Autorizado",
                DiaSemana.QUARTA,
                Collections.emptyList()
        );

        mockMvc.perform(post("/planos/" + planoId + "/treinos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void listarTreinosDoUsuarioOrdenadosPorData() throws Exception {
        String token = registrarELogarParaToken();
        UUID planoId = criarPlano(token, "Plano Semanal");

        TreinoRequest treino1 = new TreinoRequest("Treino Segunda", DiaSemana.SEGUNDA, Collections.emptyList());
        TreinoRequest treino2 = new TreinoRequest("Treino Quarta", DiaSemana.QUARTA, Collections.emptyList());

        mockMvc.perform(post("/planos/" + planoId + "/treinos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(treino1)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/planos/" + planoId + "/treinos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(treino2)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/planos/" + planoId + "/treinos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void usuarioNaoPodeExcluirTreinoDeOutroUsuario() throws Exception {
        String token1 = registrarELogarParaToken();
        String token2 = registrarELogarParaToken();

        UUID planoId = criarPlano(token1, "Plano Usuario 1");

        TreinoRequest createRequest = new TreinoRequest(
                "Treino para Excluir",
                DiaSemana.SEXTA,
                Collections.emptyList()
        );

        String responseBody = mockMvc.perform(post("/planos/" + planoId + "/treinos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID treinoId = UUID.fromString(
                objectMapper.readTree(responseBody).get("id").asText()
        );

        mockMvc.perform(delete("/planos/" + planoId + "/treinos/" + treinoId)
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

    private UUID criarPlano(String token, String nomePlano) throws Exception {
        PlanoTreinoRequest request = new PlanoTreinoRequest(nomePlano, 12);

        String responseBody = mockMvc.perform(post("/planos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return UUID.fromString(objectMapper.readTree(responseBody).get("id").asText());
    }
}
