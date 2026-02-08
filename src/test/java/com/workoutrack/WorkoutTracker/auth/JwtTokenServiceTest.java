package com.workoutrack.WorkoutTracker.auth;

import com.workoutrack.WorkoutTracker.domain.usuario.UserDetailsImpl;
import com.workoutrack.WorkoutTracker.domain.usuario.Usuario;
import com.workoutrack.WorkoutTracker.service.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtTokenServiceTest {

	@Autowired
	private JwtTokenService jwtTokenService;

	@Test
	void deveGerarEValidarToken() {
		UUID userId = UUID.randomUUID();
		String email = "user-" + userId + "@example.com";

		Usuario usuario = new Usuario();
		usuario.setId(userId);
		usuario.setEmail(email);
		usuario.setSenha("hash");

		UserDetailsImpl userDetails = new UserDetailsImpl(usuario);

		String token = jwtTokenService.generateToken(userDetails);

		assertThat(token).isNotBlank();
		assertThat(jwtTokenService.getSubjectFromToken(token)).isEqualTo(email);
		assertThat(jwtTokenService.getUserIdFromToken(token)).isEqualTo(userId);
	}
}
