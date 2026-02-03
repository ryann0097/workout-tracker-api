package com.workoutrack.WorkoutTracker.controller;

import com.workoutrack.WorkoutTracker.domain.usuario.UserDetailsImpl;
import com.workoutrack.WorkoutTracker.dto.usuario.LoginRequest;
import com.workoutrack.WorkoutTracker.dto.usuario.UsuarioRegisterRequest;
import com.workoutrack.WorkoutTracker.service.JwtTokenService;
import com.workoutrack.WorkoutTracker.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workoutrack.WorkoutTracker.service.UsuarioService;
import com.workoutrack.WorkoutTracker.dto.usuario.LoginResponse;

import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticacaoController {
    private final UsuarioService usuarioService;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public void register(@RequestBody UsuarioRegisterRequest dto){
        usuarioService.registrar(dto);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest dto){
        try {
            var authToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
            authenticationManager.authenticate(authToken);

            UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserByUsername(dto.email());
            String token = jwtTokenService.generateToken(user);

            return new LoginResponse(token, user.getId(), user.getUsername());
        } catch (AuthenticationException e){
            throw new RuntimeException("Credenciais inválidas");
        }
    }
}
