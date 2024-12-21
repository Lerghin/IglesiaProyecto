package com.academia.iglesia.auth;

import com.academia.iglesia.JWT.JwtService;
import com.academia.iglesia.repository.IUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://dashboard-academy-church.vercel.app" )
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final IUserRepository userRepository;


    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("admin/register/user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(authService.registerUser(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(authService.registerAdmin(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @GetMapping("/username")
    public ResponseEntity<String> getUsername() {
        try {
            // Obtener detalles del usuario desde el contexto de seguridad
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                return ResponseEntity.ok(username);
            } else {
                return ResponseEntity.badRequest().body("Usuario no autenticado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener el nombre de usuario: " + e.getMessage());
        }
    }
    @PostMapping("/logout")
    public String performLogout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            return "Successfully logged out";
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // No hay usuario autenticado
            return "No user logged in";
        }
    }

    @GetMapping("/token")
    public ResponseEntity<AuthResponse> getToken(HttpServletRequest request) {
        try {
            // Obtener el nuevo token usando el método getToken del servicio de autenticación
            AuthResponse response = authService.getToken(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
