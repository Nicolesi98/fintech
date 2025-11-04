package br.com.fiap.fintech_backend.controller;

import br.com.fiap.fintech_backend.model.Conta;
import br.com.fiap.fintech_backend.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contas")
@Tag(name = "Contas", description = "Operações relacionadas a contas bancárias")
@CrossOrigin("http://localhost:3000")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @GetMapping("/usuario/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista todas as contas")
    public List<Conta> getAllContas(@PathVariable Long userId) {
        return contaService.findAll(userId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma conta por ID")
    public ResponseEntity<Conta> getContaById(@PathVariable Long id) {
        Optional<Conta> conta = contaService.findById(id);
        return conta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria uma nova conta")
    public Conta createConta(@PathVariable Long userId, @RequestBody Conta conta) {
        return contaService.save(userId, conta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma conta existente")
    public ResponseEntity<Conta> updateConta(@PathVariable Long id, @RequestBody Conta contaDetails) {
        return contaService.findById(id).map(conta -> {
            conta.setAgencia(contaDetails.getAgencia());
            conta.setNumero(contaDetails.getNumero());
            conta.setTipoConta(contaDetails.getTipoConta());
            conta.setSaldo(contaDetails.getSaldo());
            return ResponseEntity.ok(contaService.update(conta));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma conta")
    public ResponseEntity<Void> deleteConta(@PathVariable Long id) {
        if (contaService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
