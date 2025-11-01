package br.com.fiap.fintech_backend.controller;

import br.com.fiap.fintech_backend.model.Meta;
import br.com.fiap.fintech_backend.service.MetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/metas")
@Tag(name = "Metas", description = "Operações relacionadas a metas financeiras")
public class MetaController {

    @Autowired
    private MetaService metaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista todas as metas")
    public List<Meta> getAllMetas() {
        return metaService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma meta por ID")
    public ResponseEntity<Meta> getMetaById(@PathVariable Long id) {
        Optional<Meta> meta = metaService.findById(id);
        return meta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria uma nova meta")
    public Meta createMeta(@RequestBody Meta meta) {
        return metaService.save(meta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma meta existente")
    public ResponseEntity<Meta> updateMeta(@PathVariable Long id, @RequestBody Meta metaDetails) {
        return metaService.findById(id).map(meta -> {
            meta.setNome(metaDetails.getNome());
            meta.setValorAlvo(metaDetails.getValorAlvo());
            meta.setValorAtual(metaDetails.getValorAtual());
            meta.setStatus(metaDetails.getStatus());
            meta.setDataAlvo(metaDetails.getDataAlvo());
            meta.setCategoria(metaDetails.getCategoria());
            return ResponseEntity.ok(metaService.save(meta));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma meta")
    public ResponseEntity<Void> deleteMeta(@PathVariable Long id) {
        if (metaService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
