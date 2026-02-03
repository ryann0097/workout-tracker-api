package com.workoutrack.WorkoutTracker.service;

import com.workoutrack.WorkoutTracker.domain.usuario.UserDetailsImpl;
import com.workoutrack.WorkoutTracker.domain.usuario.Usuario;
import com.workoutrack.WorkoutTracker.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
        return new UserDetailsImpl(usuario);
    }

    public UserDetailsImpl loadUserDetailsById(java.util.UUID userId) {
        Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + userId));
        return new UserDetailsImpl(usuario);
    }
}
