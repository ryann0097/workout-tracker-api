package com.workoutrack.WorkoutTracker.controller;

import com.workoutrack.WorkoutTracker.domain.usuario.UserDetailsImpl;
import com.workoutrack.WorkoutTracker.dto.usuario.LoginRequest;
import com.workoutrack.WorkoutTracker.dto.usuario.UsuarioRegisterRequest;
import com.workoutrack.WorkoutTracker.service.JwtTokenService;
import com.workoutrack.WorkoutTracker.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workoutrack.WorkoutTracker.service.UsuarioService;
import com.workoutrack.WorkoutTracker.dto.usuario.LoginResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para registro e login de usuários")
public class AutenticacaoController {
    private final UsuarioService usuarioService;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public void register(@RequestBody UsuarioRegisterRequest dto){
        usuarioService.registrar(dto);
    }

    @PostMapping("/login")
    @Operation(summary = "Fazer login", description = "Autentica um usuário e retorna um token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public LoginResponse login(@RequestBody LoginRequest dto){
        try {
            var authToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
            authenticationManager.authenticate(authToken);

            UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserByUsername(dto.email());
            String token = jwtTokenService.generateToken(user);

            return new LoginResponse(token, user.getId(), user.getUsername());
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }
    }
}
