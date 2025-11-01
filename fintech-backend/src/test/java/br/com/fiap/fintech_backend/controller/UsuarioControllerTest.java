package br.com.fiap.fintech_backend.controller;

import br.com.fiap.fintech_backend.model.Login;
import br.com.fiap.fintech_backend.model.Usuario;
import br.com.fiap.fintech_backend.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static br.com.fiap.fintech_backend.model.MessageError.SENHA_INCORRETA;
import static br.com.fiap.fintech_backend.model.MessageError.USUARIO_NAO_ENCONTRADO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllUsuarios() throws Exception {
        when(usuarioService.findAll()).thenReturn(Collections.singletonList(new Usuario()));
        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUsuarioById() throws Exception {
        when(usuarioService.findById(1L)).thenReturn(Optional.of(new Usuario()));
        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUsuarioByIdNotFound() throws Exception {
        when(usuarioService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateUsuario() throws Exception {
        Usuario usuario = new Usuario();
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);
        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testLogin() throws Exception {
        Login login = new Login();
        login.setEmail("test@test.com");
        login.setSenha("password");
        Usuario usuario = new Usuario();
        when(usuarioService.login(any(Login.class))).thenReturn(usuario);
        mockMvc.perform(post("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginSenhaIncorreta() throws Exception {
        Login login = new Login();
        login.setEmail("test@test.com");
        login.setSenha("wrong_password");
        when(usuarioService.login(any(Login.class))).thenThrow(new RuntimeException(SENHA_INCORRETA));
        mockMvc.perform(post("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginUsuarioNaoEncontrado() throws Exception {
        Login login = new Login();
        login.setEmail("not_found@test.com");
        login.setSenha("password");
        when(usuarioService.login(any(Login.class))).thenThrow(new RuntimeException(USUARIO_NAO_ENCONTRADO));
        mockMvc.perform(post("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUsuario() throws Exception {
        Usuario usuario = new Usuario();
        when(usuarioService.findById(1L)).thenReturn(Optional.of(new Usuario()));
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);
        mockMvc.perform(put("/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUsuarioNotFound() throws Exception {
        Usuario usuario = new Usuario();
        when(usuarioService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(put("/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUsuario() throws Exception {
        when(usuarioService.deleteById(1L)).thenReturn(true);
        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUsuarioNotFound() throws Exception {
        when(usuarioService.deleteById(1L)).thenReturn(false);
        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isNotFound());
    }
}
