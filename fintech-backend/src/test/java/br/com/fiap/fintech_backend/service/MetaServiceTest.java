package br.com.fiap.fintech_backend.service;

import br.com.fiap.fintech_backend.model.Meta;
import br.com.fiap.fintech_backend.repository.MetaRepository;
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
public class MetaServiceTest {

    @InjectMocks
    private MetaService metaService;

    @Mock
    private MetaRepository metaRepository;

    @Test
    public void testFindAll() {
        when(metaRepository.findAll()).thenReturn(Collections.singletonList(new Meta()));
        assertEquals(1, metaService.findAll().size());
    }

    @Test
    public void testFindById() {
        when(metaRepository.findById(1L)).thenReturn(Optional.of(new Meta()));
        assertTrue(metaService.findById(1L).isPresent());
    }

    @Test
    public void testSave() {
        Meta meta = new Meta();
        when(metaRepository.save(meta)).thenReturn(meta);
        assertEquals(meta, metaService.save(meta));
    }

    @Test
    public void testDeleteById() {
        when(metaRepository.existsById(1L)).thenReturn(true);
        metaService.deleteById(1L);
        verify(metaRepository, times(1)).deleteById(1L);
    }
}
