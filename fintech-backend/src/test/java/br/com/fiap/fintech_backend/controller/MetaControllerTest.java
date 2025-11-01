package br.com.fiap.fintech_backend.controller;

import br.com.fiap.fintech_backend.model.Meta;
import br.com.fiap.fintech_backend.service.MetaService;
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

@WebMvcTest(MetaController.class)
public class MetaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetaService metaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllMetas() throws Exception {
        when(metaService.findAll()).thenReturn(Collections.singletonList(new Meta()));
        mockMvc.perform(get("/metas"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMetaById() throws Exception {
        when(metaService.findById(1L)).thenReturn(Optional.of(new Meta()));
        mockMvc.perform(get("/metas/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMetaByIdNotFound() throws Exception {
        when(metaService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/metas/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateMeta() throws Exception {
        Meta meta = new Meta();
        when(metaService.save(any(Meta.class))).thenReturn(meta);
        mockMvc.perform(post("/metas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meta)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateMeta() throws Exception {
        Meta meta = new Meta();
        when(metaService.findById(1L)).thenReturn(Optional.of(new Meta()));
        when(metaService.save(any(Meta.class))).thenReturn(meta);
        mockMvc.perform(put("/metas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meta)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateMetaNotFound() throws Exception {
        Meta meta = new Meta();
        when(metaService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(put("/metas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meta)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteMeta() throws Exception {
        when(metaService.deleteById(1L)).thenReturn(true);
        mockMvc.perform(delete("/metas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteMetaNotFound() throws Exception {
        when(metaService.deleteById(1L)).thenReturn(false);
        mockMvc.perform(delete("/metas/1"))
                .andExpect(status().isNotFound());
    }
}
