package br.com.fiap.fintech_backend.service;

import br.com.fiap.fintech_backend.model.Transacao;
import br.com.fiap.fintech_backend.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Test
    public void testFindAll() {
        when(transacaoRepository.findAll()).thenReturn(Collections.singletonList(new Transacao()));
        assertEquals(1, transacaoService.findAll().size());
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
        transacaoService.deleteById(1L);
        verify(transacaoRepository, times(1)).deleteById(1L);
    }
}
