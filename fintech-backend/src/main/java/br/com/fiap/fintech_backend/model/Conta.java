package br.com.fiap.fintech_backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CONTA")
@Schema(description = "Representa uma conta bancária de um usuário")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Schema(description = "ID único da conta", example = "1")
    private Long id;

    @NotNull(message = "Agência não pode ser nula")
    @Column(name = "AGENCIA", nullable = false)
    @Schema(description = "Número da agência bancária", example = "1234")
    private Integer agencia;

    @NotNull(message = "Número da conta não pode ser nulo")
    @Column(name = "NUMERO", nullable = false)
    @Schema(description = "Número da conta bancária", example = "56789")
    private Integer numero;

    @NotNull(message = "Tipo da conta não pode ser nulo")
    @Column(name = "TIPO_CONTA", nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "Tipo da conta (ex: CORRENTE, POUPANCA)", example = "CORRENTE")
    private TipoConta tipoConta;

    @NotNull(message = "Saldo não pode ser nulo")
    @PositiveOrZero(message = "Saldo deve ser positivo ou zero")
    @Column(name = "SALDO", nullable = false)
    @Schema(description = "Saldo atual da conta", example = "1000.00")
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "DATA_CRIACAO", nullable = false, updatable = false)
    @Schema(description = "Data de criação da conta")
    private LocalDateTime dataCriacao = LocalDateTime.now();
}
