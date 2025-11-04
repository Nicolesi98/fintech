package br.com.fiap.fintech_backend.controller;

import br.com.fiap.fintech_backend.model.Conta;
import br.com.fiap.fintech_backend.service.ContaService;
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

@WebMvcTest(ContaController.class)
public class ContaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContaService contaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllContas() throws Exception {
        when(contaService.findAll(1L)).thenReturn(Collections.singletonList(new Conta()));
        mockMvc.perform(get("/contas/usuario/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetContaById() throws Exception {
        when(contaService.findById(1L)).thenReturn(Optional.of(new Conta()));
        mockMvc.perform(get("/contas/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetContaByIdNotFound() throws Exception {
        when(contaService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/contas/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateConta() throws Exception {
        Conta conta = new Conta();
        when(contaService.save(any(Long.class), any(Conta.class))).thenReturn(conta);
        mockMvc.perform(post("/contas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(conta)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateConta() throws Exception {
        Conta conta = new Conta();
        when(contaService.findById(1L)).thenReturn(Optional.of(new Conta()));
        when(contaService.update(any(Conta.class))).thenReturn(conta);
        mockMvc.perform(put("/contas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(conta)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateContaNotFound() throws Exception {
        Conta conta = new Conta();
        when(contaService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(put("/contas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(conta)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteConta() throws Exception {
        when(contaService.deleteById(1L)).thenReturn(true);
        mockMvc.perform(delete("/contas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteContaNotFound() throws Exception {
        when(contaService.deleteById(1L)).thenReturn(false);
        mockMvc.perform(delete("/contas/1"))
                .andExpect(status().isNotFound());
    }
}
