package mweb.mw_backend.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mweb.mw_backend.auth.AuthResponse;
import mweb.mw_backend.auth.LoginRequest;
import mweb.mw_backend.auth.RegisterRequest;
import mweb.mw_backend.entity.User;
import mweb.mw_backend.enumeration.UserRole;
import mweb.mw_backend.jwt.JwtService;
import mweb.mw_backend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

   private final UserRepository userRepository;
   private final JwtService jwtService;
   private final PasswordEncoder passwordEncoder;
   private final AuthenticationManager authenticationManager;

   public AuthResponse login(LoginRequest request) {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
      User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
      String token = jwtService.getToken(user);
      return AuthResponse.builder()
            .token(token)
            .build();
   }

   public AuthResponse register(RegisterRequest request) {
      User user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
            .lastName(request.getLastName())
            .address(request.getAddress())
            .cel(request.getCel())
            .registerDate(LocalDateTime.now())
            .role(UserRole.CLIENT)
            .build();

      userRepository.save(user);

      return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
   }
}