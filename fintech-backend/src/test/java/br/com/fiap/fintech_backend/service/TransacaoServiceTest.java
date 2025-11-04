package br.com.fiap.fintech_backend.service;

import br.com.fiap.fintech_backend.model.Conta;
import br.com.fiap.fintech_backend.model.Transacao;
import br.com.fiap.fintech_backend.model.Usuario;
import br.com.fiap.fintech_backend.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private UsuarioService usuarioService;

    @Test
    public void testFindAll() {
        Usuario usuario = new Usuario();
        Conta conta = new Conta();
        usuario.setContas(List.of(conta));

        when(usuarioService.findById(1L)).thenReturn(Optional.of(usuario));
        when(transacaoRepository.findByConta(conta)).thenReturn(Optional.of(new Transacao()));

        assertEquals(1, transacaoService.findAll(1L).size());
    }

    @Test
    public void testFindAllUsuarioSemConta() {
        when(usuarioService.findById(1L)).thenReturn(Optional.of(new Usuario()));
        assertEquals(0, transacaoService.findAll(1L).size());
    }

    @Test
    public void testFindById() {
        when(transacaoRepository.findById(1L)).thenReturn(Optional.of(new Transacao()));
        assertTrue(transacaoService.findById(1L).isPresent());
    }

    @Test
    public void testSave() {
        Transacao transacao = new Transacao();
        when(transacaoRepository.save(transacao)).thenReturn(transacao);
        assertEquals(transacao, transacaoService.save(transacao));
    }

    @Test
    public void testDeleteById() {
        when(transacaoRepository.existsById(1L)).thenReturn(true);
        assertTrue(transacaoService.deleteById(1L));
        verify(transacaoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteByIdNotFound() {
        when(transacaoRepository.existsById(1L)).thenReturn(false);
        assertFalse(transacaoService.deleteById(1L));
        verify(transacaoRepository, never()).deleteById(1L);
    }
}
