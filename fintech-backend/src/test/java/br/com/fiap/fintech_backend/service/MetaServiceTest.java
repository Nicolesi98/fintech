package br.com.fiap.fintech_backend.service;

import br.com.fiap.fintech_backend.model.Meta;
import br.com.fiap.fintech_backend.model.Usuario;
import br.com.fiap.fintech_backend.repository.MetaRepository;
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
public class MetaServiceTest {

    @InjectMocks
    private MetaService metaService;

    @Mock
    private MetaRepository metaRepository;

    @Mock
    private UsuarioService usuarioService;

    @Test
    public void testFindAll() {
        Usuario usuario = new Usuario();
        usuario.setMetas(List.of(new Meta()));
        when(usuarioService.findById(1L)).thenReturn(Optional.of(usuario));
        assertEquals(1, metaService.findAll(1L).size());
    }

    @Test
    public void testFindAllUsuarioSemMetas() {
        when(usuarioService.findById(1L)).thenReturn(Optional.of(new Usuario()));
        assertEquals(0, metaService.findAll(1L).size());
    }

    @Test
    public void testFindById() {
        when(metaRepository.findById(1L)).thenReturn(Optional.of(new Meta()));
        assertTrue(metaService.findById(1L).isPresent());
    }

    @Test
    public void testSave() {
        Meta meta = new Meta();
        Usuario usuario = new Usuario();
        usuario.setMetas(new ArrayList<>());

        when(usuarioService.findById(1L)).thenReturn(Optional.of(usuario));
        when(metaRepository.save(meta)).thenReturn(meta);

        Meta result = metaService.save(1L, meta);

        assertEquals(meta, result);
        assertTrue(usuario.getMetas().contains(meta));
        verify(usuarioService, times(1)).save(usuario);
    }

    @Test
    public void testUpdate() {
        Meta meta = new Meta();
        when(metaRepository.save(meta)).thenReturn(meta);
        assertEquals(meta, metaService.update(meta));
    }

    @Test
    public void testDeleteById() {
        when(metaRepository.existsById(1L)).thenReturn(true);
        assertTrue(metaService.deleteById(1L));
        verify(metaRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteByIdNotFound() {
        when(metaRepository.existsById(1L)).thenReturn(false);
        assertFalse(metaService.deleteById(1L));
        verify(metaRepository, never()).deleteById(1L);
    }
}
