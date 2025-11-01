package br.com.fiap.fintech_backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TRANSACAO")
@Schema(description = "Representa uma transação financeira")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Schema(description = "ID da transação", example = "1")
    private Long id;

    @NotBlank(message = "Descrição não pode ser nula")
    @Column(name = "DESCRICAO", nullable = false)
    @Schema(description = "Descrição da transação", example = "Almoço no restaurante")
    private String descricao;

    @Column(name = "DATA_CRIACAO", nullable = false, updatable = false)
    @Schema(description = "Data de criação da transação")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @NotNull(message = "Valor não pode ser nulo")
    @Column(name = "VALOR", nullable = false)
    @Schema(description = "Valor da transação", example = "50.00")
    private BigDecimal valor;

    @NotBlank(message = "Tipo da transação não pode ser nulo")
    @Column(name = "TIPO_TRANSACAO", nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "Tipo da transação", example = "DEBITO")
    private StatusTransacao tipo;

    @ManyToOne
    @JoinColumn(name = "FK_CONTA_ID", referencedColumnName = "ID", nullable = false)
    @Schema(description = "Conta associada à transação")
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "FK_CATEGORIA_ID", referencedColumnName = "ID", nullable = false)
    @Schema(description = "Categoria da transação")
    private Categoria categoria;
}
