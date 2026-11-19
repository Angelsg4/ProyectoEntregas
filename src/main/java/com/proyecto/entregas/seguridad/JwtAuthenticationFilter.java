
package com.proyecto.entregas.security;

import com.proyecto.entregas.models.Usuario;
import com.proyecto.entregas.repositories.RepositorioUsuario;
import com.proyecto.entregas.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final RepositorioUsuario repositorioUsuario;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            RepositorioUsuario repositorioUsuario) {

        this.jwtService = jwtService;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String encabezado =
                request.getHeader("Authorization");

        if (encabezado == null ||
                !encabezado.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = encabezado.substring(7);

        if (jwtService.validarToken(token) &&
                SecurityContextHolder.getContext()
                        .getAuthentication() == null) {

            String correo = jwtService.extraerCorreo(token);

            Optional<Usuario> usuarioOptional =
                    repositorioUsuario.findByCorreo(correo);

            if (usuarioOptional.isPresent()) {

                Usuario usuario = usuarioOptional.get();

                var autoridad = new SimpleGrantedAuthority(
                        "ROLE_" + usuario.getRol().name()
                );

                var autenticacion =
                        new UsernamePasswordAuthenticationToken(
                                usuario.getCorreo(),
                                null,
                                List.of(autoridad)
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(autenticacion);
            }
        }

        filterChain.doFilter(request, response);
    }
}