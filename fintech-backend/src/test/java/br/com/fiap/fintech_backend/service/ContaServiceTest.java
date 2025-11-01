package br.com.fiap.fintech_backend.service;

import br.com.fiap.fintech_backend.model.Conta;
import br.com.fiap.fintech_backend.repository.ContaRepository;
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
public class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaRepository contaRepository;

    @Test
    public void testFindAll() {
        when(contaRepository.findAll()).thenReturn(Collections.singletonList(new Conta()));
        assertEquals(1, contaService.findAll().size());
    }

    @Test
    public void testFindById() {
        when(contaRepository.findById(1L)).thenReturn(Optional.of(new Conta()));
        assertTrue(contaService.findById(1L).isPresent());
    }

    @Test
    public void testSave() {
        Conta conta = new Conta();
        when(contaRepository.save(conta)).thenReturn(conta);
        assertEquals(conta, contaService.save(conta));
    }

    @Test
    public void testDeleteById() {
        when(contaRepository.existsById(1L)).thenReturn(true);
        contaService.deleteById(1L);
        verify(contaRepository, times(1)).deleteById(1L);
    }
}
