package br.com.fiap.fintech_backend.service;

import br.com.fiap.fintech_backend.model.Categoria;
import br.com.fiap.fintech_backend.repository.CategoriaRepository;
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
public class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Test
    public void testFindAll() {
        when(categoriaRepository.findAll()).thenReturn(Collections.singletonList(new Categoria()));
        assertEquals(1, categoriaService.findAll().size());
    }

    @Test
    public void testFindById() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(new Categoria()));
        assertTrue(categoriaService.findById(1L).isPresent());
    }

    @Test
    public void testSave() {
        Categoria categoria = new Categoria();
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        assertEquals(categoria, categoriaService.save(categoria));
    }

    @Test
    public void testDeleteById() {
        when(categoriaRepository.existsById(1L)).thenReturn(true);
        categoriaService.deleteById(1L);
        verify(categoriaRepository, times(1)).deleteById(1L);
    }
}
