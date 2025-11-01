package br.com.fiap.fintech_backend.controller;

import br.com.fiap.fintech_backend.model.Transacao;
import br.com.fiap.fintech_backend.service.TransacaoService;
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

@WebMvcTest(TransacaoController.class)
public class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransacaoService transacaoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllTransacoes() throws Exception {
        when(transacaoService.findAll()).thenReturn(Collections.singletonList(new Transacao()));
        mockMvc.perform(get("/transacoes"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTransacaoById() throws Exception {
        when(transacaoService.findById(1L)).thenReturn(Optional.of(new Transacao()));
        mockMvc.perform(get("/transacoes/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTransacaoByIdNotFound() throws Exception {
        when(transacaoService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/transacoes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateTransacao() throws Exception {
        Transacao transacao = new Transacao();
        when(transacaoService.save(any(Transacao.class))).thenReturn(transacao);
        mockMvc.perform(post("/transacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateTransacao() throws Exception {
        Transacao transacao = new Transacao();
        when(transacaoService.findById(1L)).thenReturn(Optional.of(new Transacao()));
        when(transacaoService.save(any(Transacao.class))).thenReturn(transacao);
        mockMvc.perform(put("/transacoes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTransacaoNotFound() throws Exception {
        Transacao transacao = new Transacao();
        when(transacaoService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(put("/transacoes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTransacao() throws Exception {
        when(transacaoService.deleteById(1L)).thenReturn(true);
        mockMvc.perform(delete("/transacoes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTransacaoNotFound() throws Exception {
        when(transacaoService.deleteById(1L)).thenReturn(false);
        mockMvc.perform(delete("/transacoes/1"))
                .andExpect(status().isNotFound());
    }
}
