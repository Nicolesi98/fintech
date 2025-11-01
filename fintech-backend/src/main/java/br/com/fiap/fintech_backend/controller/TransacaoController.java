package br.com.fiap.fintech_backend.controller;

import br.com.fiap.fintech_backend.model.Transacao;
import br.com.fiap.fintech_backend.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transacoes")
@Tag(name = "Transacões", description = "Operações relacionadas a transações financeiras")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista todas as transações")
    public List<Transacao> getAllTransacoes() {
        return transacaoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma transação por ID")
    public ResponseEntity<Transacao> getTransacaoById(@PathVariable Long id) {
        Optional<Transacao> transacao = transacaoService.findById(id);
        return transacao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria uma nova transação")
    public Transacao createTransacao(@RequestBody Transacao transacao) {
        return transacaoService.save(transacao);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma transação existente")
    public ResponseEntity<Transacao> updateTransacao(@PathVariable Long id, @RequestBody Transacao transacaoDetails) {
        Optional<Transacao> transacao = transacaoService.findById(id);
        if (transacao.isPresent()) {
            Transacao updatedTransacao = transacao.get();
            updatedTransacao.setDescricao(transacaoDetails.getDescricao());
            updatedTransacao.setValor(transacaoDetails.getValor());
            updatedTransacao.setTipo(transacaoDetails.getTipo());
            updatedTransacao.setConta(transacaoDetails.getConta());
            updatedTransacao.setCategoria(transacaoDetails.getCategoria());
            return ResponseEntity.ok(transacaoService.save(updatedTransacao));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma transação")
    public ResponseEntity<Void> deleteTransacao(@PathVariable Long id) {
        transacaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
