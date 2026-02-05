package com.workoutrack.WorkoutTracker.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workoutrack.WorkoutTracker.dto.usuario.LoginRequest;
import com.workoutrack.WorkoutTracker.dto.usuario.UsuarioRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
	void devePermitirAcessoSemToken() throws Exception {
		mockMvc.perform(get("/exercicios"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}

    @Test
    void devePermitirAcessoComTokenValido() throws Exception {
	String token = registrarELogarParaToken();

	mockMvc.perform(get("/exercicios")
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray());
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
