package br.com.fiap.fintech_backend.service;

import br.com.fiap.fintech_backend.model.Login;
import br.com.fiap.fintech_backend.model.Usuario;
import br.com.fiap.fintech_backend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static br.com.fiap.fintech_backend.model.MessageError.SENHA_INCORRETA;
import static br.com.fiap.fintech_backend.model.MessageError.USUARIO_NAO_ENCONTRADO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    public void testFindAll() {
        when(usuarioRepository.findAll()).thenReturn(Collections.singletonList(new Usuario()));
        assertEquals(1, usuarioService.findAll().size());
    }

    @Test
    public void testFindById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(new Usuario()));
        assertTrue(usuarioService.findById(1L).isPresent());
    }

    @Test
    public void testSave() {
        Usuario usuario = new Usuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        assertEquals(usuario, usuarioService.save(usuario));
    }

    @Test
    public void testLoginComSucesso() {
        Login login = new Login();
        login.setEmail("test@test.com");
        login.setSenha("password");

        Usuario usuario = new Usuario();
        usuario.setEmail("test@test.com");
        usuario.setSenha("password");

        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.login(login);
        assertEquals(usuario, result);
    }

    @Test
    public void testLoginSenhaIncorreta() {
        Login login = new Login();
        login.setEmail("test@test.com");
        login.setSenha("wrong_password");

        Usuario usuario = new Usuario();
        usuario.setEmail("test@test.com");
        usuario.setSenha("password");

        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.login(login));
        assertEquals(SENHA_INCORRETA, exception.getMessage());
    }

    @Test
    public void testLoginUsuarioNaoEncontrado() {
        Login login = new Login();
        login.setEmail("not_found@test.com");
        login.setSenha("password");

        when(usuarioRepository.findByEmail("not_found@test.com")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.login(login));
        assertEquals(USUARIO_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    public void testDeleteById() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        usuarioService.deleteById(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}
