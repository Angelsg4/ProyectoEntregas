package com.proyecto.entregas.services;

import com.proyecto.entregas.models.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey claveSecreta;
    private final long expiracion;

    public JwtService(
            @Value("${jwt.secret}") String secreto,
            @Value("${jwt.expiration}") long expiracion) {

        byte[] bytes = Base64.getDecoder().decode(secreto);

        this.claveSecreta = Keys.hmacShaKeyFor(bytes);
        this.expiracion = expiracion;
    }

    public String generarToken(Usuario usuario) {

        Date ahora = new Date();

        Date fechaExpiracion = new Date(
                ahora.getTime() + expiracion
        );

        return Jwts.builder()
                .subject(usuario.getCorreo())
                .issuedAt(ahora)
                .expiration(fechaExpiracion)
                .signWith(claveSecreta)
                .compact();
    }

    public String extraerCorreo(String token) {
        return extraerClaims(token).getSubject();
    }

    public boolean validarToken(String token) {
        try {
            extraerClaims(token);
            return true;
        } catch (io.jsonwebtoken.JwtException |
                 IllegalArgumentException e) {
            return false;
        }
    }

    private Claims extraerClaims(String token) {

        return Jwts.parser()
                .verifyWith(claveSecreta)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}