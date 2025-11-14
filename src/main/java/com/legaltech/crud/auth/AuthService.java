package com.legaltech.crud.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.legaltech.crud.dtos.AuthResponse;
import com.legaltech.crud.dtos.LoginRequest;
import com.legaltech.crud.dtos.RegisterRequest;
import com.legaltech.crud.exceptions.NotFoundException;
import com.legaltech.crud.jwt.JwtService;
import com.legaltech.crud.models.Usuario;
import com.legaltech.crud.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse login(LoginRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        Usuario user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        return mapToDTO(user);
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("El email ya esta registrado");
        }
        Usuario user = new Usuario();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return mapToDTO(user);
    }

    private AuthResponse mapToDTO(Usuario user) {
        return AuthResponse.builder()
                .token(jwtService.createJwtToken(user))
                .userId(user.getId())
                .build();
    }
}
