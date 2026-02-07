package com.workoutrack.WorkoutTracker.treino;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workoutrack.WorkoutTracker.domain.treino.DiaSemana;
import com.workoutrack.WorkoutTracker.dto.treino.PlanoTreinoRequest;
import com.workoutrack.WorkoutTracker.dto.treino.RegistroTreinoRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegistroTreinoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registrarExecucaoDeTreinoDoUsuario() throws Exception {
        String token = registrarELogarParaToken();
        UUID treinoId = criarTreinoCompleto(token);

        RegistroTreinoRequest request = new RegistroTreinoRequest(
                LocalDateTime.now(),
                Collections.emptyList()
        );

        mockMvc.perform(post("/registros/treinos/" + treinoId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.dataExecucao").isNotEmpty());
    }

    @Test
    void registrarExecucaoSemTokenDeveRetornar401() throws Exception {
        UUID treinoId = UUID.randomUUID();

        RegistroTreinoRequest request = new RegistroTreinoRequest(
                LocalDateTime.now(),
                Collections.emptyList()
        );

        mockMvc.perform(post("/registros/treinos/" + treinoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void usuarioNaoPodeRegistrarExecucaoDeOutroUsuario() throws Exception {
        String token1 = registrarELogarParaToken();
        String token2 = registrarELogarParaToken();

        UUID treinoId = criarTreinoCompleto(token1);

        RegistroTreinoRequest request = new RegistroTreinoRequest(
                LocalDateTime.now(),
                Collections.emptyList()
        );

        mockMvc.perform(post("/registros/treinos/" + treinoId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void listarHistoricoDeTreinosDoUsuario() throws Exception {
        String token = registrarELogarParaToken();
        UUID treinoId = criarTreinoCompleto(token);

        RegistroTreinoRequest request1 = new RegistroTreinoRequest(
                LocalDateTime.now().minusDays(1),
                Collections.emptyList()
        );

        RegistroTreinoRequest request2 = new RegistroTreinoRequest(
                LocalDateTime.now(),
                Collections.emptyList()
        );

        mockMvc.perform(post("/registros/treinos/" + treinoId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/registros/treinos/" + treinoId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/registros")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void usuarioNaoPodeVisualizarHistoricoDeOutroUsuario() throws Exception {
        String token1 = registrarELogarParaToken();
        String token2 = registrarELogarParaToken();

        UUID treinoId = criarTreinoCompleto(token1);

        RegistroTreinoRequest request = new RegistroTreinoRequest(
                LocalDateTime.now(),
                Collections.emptyList()
        );

        mockMvc.perform(post("/registros/treinos/" + treinoId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/registros")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
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

    private UUID criarTreinoCompleto(String token) throws Exception {
        PlanoTreinoRequest planoRequest = new PlanoTreinoRequest("Plano Teste", 12);

        String planoResponseBody = mockMvc.perform(post("/planos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planoRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID planoId = UUID.fromString(objectMapper.readTree(planoResponseBody).get("id").asText());

        TreinoRequest treinoRequest = new TreinoRequest(
                "Treino Teste",
                DiaSemana.SEGUNDA,
                Collections.emptyList()
        );

        String treinoResponseBody = mockMvc.perform(post("/planos/" + planoId + "/treinos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(treinoRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return UUID.fromString(objectMapper.readTree(treinoResponseBody).get("id").asText());
    }
}
