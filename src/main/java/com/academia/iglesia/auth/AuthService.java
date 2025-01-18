package com.academia.iglesia.auth;


import com.academia.iglesia.JWT.JwtService;
import com.academia.iglesia.model.Role;
import com.academia.iglesia.model.User;
import com.academia.iglesia.repository.IUserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
   private final IUserRepository userRepository;
   private final JwtService jwtService;
   private final PasswordEncoder passwordEncoder;
   private final AuthenticationManager authenticationManager;

   public AuthResponse login(LoginRequest request) {
      if (request.getUsername() == null || request.getUsername().isEmpty() ||
              request.getPassword() == null || request.getPassword().isEmpty()) {
         throw new GlobalExceptionHandler.MissingDataException("Username or password is missing");
      }

      try {
         authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
         );
      } catch (Exception e) {
         throw new GlobalExceptionHandler.InvalidCredentialsException("Invalid username or password");
      }

      UserDetails userDetails = userRepository.findByUsername(request.getUsername())
              .orElseThrow(() -> new GlobalExceptionHandler.InvalidCredentialsException("User not found"));
      String token = jwtService.getToken(userDetails);

      String role= userDetails.getAuthorities().stream().findFirst().orElseThrow().getAuthority();




      return AuthResponse.builder()
              .token(token)
              .role(role)
              .build();
   }
   public AuthResponse registerUser(RegisterRequest request){
      if (userRepository.existsByUsername(request.getUsername())) {
         throw new IllegalArgumentException("El nombre de usuario ya está registrado");
      }
      if (userRepository.existsByCedula(request.getCedula())) {
         throw new IllegalArgumentException("La cédula ya está registrada");
      }
      User user= User.builder()
              .username(request.username)
              .password(passwordEncoder.encode(request.password))
              .firstName(request.firstName)
              .lastName(request.lastName)
              .cedula(request.cedula)
              .role(Role.USER)
              .build();
      userRepository.save(user);
      String role= user.getRole().toString();
      String id= user.getIdUser();
      return AuthResponse.builder()
              .token(jwtService.getToken(user))
              .role(role)
              .id(id)
              .build();

   }

   public AuthResponse registerAdmin(RegisterRequest request){
      if (userRepository.existsByUsername(request.getUsername())) {
         throw new IllegalArgumentException("El nombre de usuario ya está registrado");
      }
      if (userRepository.existsByCedula(request.getCedula())) {
         throw new IllegalArgumentException("La cédula ya está registrada");
      }
      User user = User.builder()
              .username(request.getUsername())
              .password(passwordEncoder.encode(request.getPassword()))
              .firstName(request.getFirstName())
              .lastName(request.getLastName())
              .cedula(request.getCedula())
              .role(Role.ADMIN)
              .build();
      userRepository.save(user);
      return AuthResponse.builder()
              .token(jwtService.getToken(user))
              .build();

   }
   public AuthResponse getToken(HttpServletRequest request) {
      // Extraer el nombre de usuario del token de autorización en el encabezado de la solicitud
      String username = jwtService.getUsernameFromToken(request.getHeader("Authorization"));
      // Obtener los detalles del usuario desde el repositorio de usuarios
      UserDetails userDetails = (UserDetails) userRepository.findByUsername(username).orElseThrow();
      // Obtener el rol del usuario
      String token = jwtService.getToken(userDetails);
      // Devolver el nuevo token en la respuesta
      return AuthResponse.builder()
              .token(token)
              .build();
   }

}