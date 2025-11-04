package br.com.fiap.fintech_backend.service;

import br.com.fiap.fintech_backend.model.Conta;
import br.com.fiap.fintech_backend.model.Usuario;
import br.com.fiap.fintech_backend.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private UsuarioService usuarioService;

    @Test
    public void testFindAll() {
        Usuario usuario = new Usuario();
        usuario.setContas(List.of(new Conta()));
        when(usuarioService.findById(1L)).thenReturn(Optional.of(usuario));
        assertEquals(1, contaService.findAll(1L).size());
    }

    @Test
    public void testFindAllUsuarioSemContas() {
        when(usuarioService.findById(1L)).thenReturn(Optional.of(new Usuario()));
        assertEquals(0, contaService.findAll(1L).size());
    }

    @Test
    public void testFindById() {
        when(contaRepository.findById(1L)).thenReturn(Optional.of(new Conta()));
        assertTrue(contaService.findById(1L).isPresent());
    }

    @Test
    public void testSave() {
        Conta conta = new Conta();
        Usuario usuario = new Usuario();
        usuario.setContas(new ArrayList<>());

        when(usuarioService.findById(1L)).thenReturn(Optional.of(usuario));
        when(contaRepository.save(conta)).thenReturn(conta);

        Conta result = contaService.save(1L, conta);

        assertEquals(conta, result);
        assertTrue(usuario.getContas().contains(conta));
        verify(usuarioService, times(1)).save(usuario);
    }

    @Test
    public void testUpdate() {
        Conta conta = new Conta();
        when(contaRepository.save(conta)).thenReturn(conta);
        assertEquals(conta, contaService.update(conta));
    }

    @Test
    public void testDeleteById() {
        when(contaRepository.existsById(1L)).thenReturn(true);
        assertTrue(contaService.deleteById(1L));
        verify(contaRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteByIdNotFound() {
        when(contaRepository.existsById(1L)).thenReturn(false);
        assertFalse(contaService.deleteById(1L));
        verify(contaRepository, never()).deleteById(1L);
    }
}
