package br.com.fiap.fintech_backend.controller;

import br.com.fiap.fintech_backend.model.Categoria;
import br.com.fiap.fintech_backend.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllCategorias() throws Exception {
        when(categoriaService.findAll()).thenReturn(Collections.singletonList(new Categoria()));
        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCategoriaById() throws Exception {
        when(categoriaService.findById(1L)).thenReturn(Optional.of(new Categoria()));
        mockMvc.perform(get("/categorias/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCategoriaByIdNotFound() throws Exception {
        when(categoriaService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/categorias/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateCategoria() throws Exception {
        Categoria categoria = new Categoria();
        when(categoriaService.save(any(Categoria.class))).thenReturn(categoria);
        mockMvc.perform(post("/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateCategoria() throws Exception {
        Categoria categoria = new Categoria();
        when(categoriaService.findById(1L)).thenReturn(Optional.of(new Categoria()));
        when(categoriaService.save(any(Categoria.class))).thenReturn(categoria);
        mockMvc.perform(put("/categorias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCategoriaNotFound() throws Exception {
        Categoria categoria = new Categoria();
        when(categoriaService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(put("/categorias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCategoria() throws Exception {
        when(categoriaService.deleteById(1L)).thenReturn(true);
        mockMvc.perform(delete("/categorias/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteCategoriaNotFound() throws Exception {
        when(categoriaService.deleteById(1L)).thenReturn(false);
        mockMvc.perform(delete("/categorias/1"))
                .andExpect(status().isNotFound());
    }
}
