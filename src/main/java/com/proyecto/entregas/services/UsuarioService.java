package com.proyecto.entregas.services;

import com.proyecto.entregas.dtos.RegistroUsuarioDTO;
import com.proyecto.entregas.dtos.UsuarioResponseDTO;
import com.proyecto.entregas.enums.Rol;
import com.proyecto.entregas.models.Usuario;
import com.proyecto.entregas.repositories.RepositorioUsuario;
import com.proyecto.entregas.dtos.AuthResponseDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.proyecto.entregas.dtos.LoginDTO;

@Service
public class UsuarioService {

    private final RepositorioUsuario repositorioUsuario;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsuarioService(
        RepositorioUsuario repositorioUsuario,
        PasswordEncoder passwordEncoder,
        JwtService jwtService) {

    this.repositorioUsuario = repositorioUsuario;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    }

    @Transactional
    public UsuarioResponseDTO registrarCliente(
            RegistroUsuarioDTO dto) {

        String correo = dto.getCorreo().trim()
                .toLowerCase(java.util.Locale.ROOT);

        if (repositorioUsuario.existsByCorreo(correo)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "El correo ya está registrado"
            );
        }

        Usuario usuario = new Usuario();

        usuario.setNombre(dto.getNombre().trim());
        usuario.setCorreo(correo);

        usuario.setContrasena(
            passwordEncoder.encode(dto.getContrasena())
        );

        usuario.setRol(Rol.CLIENTE);

        Usuario guardado = repositorioUsuario.save(usuario);

        return convertirADTO(guardado);
    }

    private UsuarioResponseDTO convertirADTO(Usuario usuario) {

        UsuarioResponseDTO dto = new UsuarioResponseDTO();

        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setRol(usuario.getRol());

        return dto;
    }
    public AuthResponseDTO iniciarSesion(LoginDTO dto) {

    String correo = dto.getCorreo()
            .trim()
            .toLowerCase(java.util.Locale.ROOT);

    Usuario usuario = repositorioUsuario
            .findByCorreo(correo)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Credenciales incorrectas"
            ));

    boolean contrasenaCorrecta = passwordEncoder.matches(
        dto.getContrasena(),
        usuario.getContrasena()
    );

    if (!contrasenaCorrecta) {
        throw new ResponseStatusException(
            HttpStatus.UNAUTHORIZED,
            "Credenciales incorrectas"
        );
    }

    String token = jwtService.generarToken(usuario);

    UsuarioResponseDTO usuarioDTO = convertirADTO(usuario);

    return new AuthResponseDTO(
        token,
        "Bearer",
        usuarioDTO
    );
}
}