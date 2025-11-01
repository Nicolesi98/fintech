package br.com.fiap.fintech_backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "META")
@Schema(description = "Representa uma meta financeira de um usuário")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Schema(description = "ID da meta", example = "1")
    private Long id;

    @NotBlank(message = "Nome da meta não pode ser nulo")
    @Column(name = "NOME", nullable = false)
    @Schema(description = "Nome da meta", example = "Viagem para a praia")
    private String nome;

    @Column(name = "DATA_CRIACAO", nullable = false, updatable = false)
    @Schema(description = "Data de criação da meta")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @NotNull(message = "Valor alvo não pode ser nulo")
    @Column(name = "VALOR_ALVO", nullable = false)
    @Schema(description = "Valor alvo da meta", example = "5000.00")
    private BigDecimal valorAlvo;

    @NotNull(message = "Valor atual não pode ser nulo")
    @PositiveOrZero(message = "Valor atual deve ser positivo ou zero")
    @Column(name = "VALOR_ATUAL", nullable = false)
    @Schema(description = "Valor atual economizado para a meta", example = "1500.00")
    private BigDecimal valorAtual = BigDecimal.ZERO;

    @NotBlank(message = "Status da meta não pode ser nulo")
    @Column(name = "STATUS_META", nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "Status da meta", example = "EM_ANDAMENTO")
    private StatusMeta status;

    @NotNull(message = "Data alvo não pode ser nula")
    @Future(message = "Data alvo deve ser no futuro")
    @Column(name = "DATA_ALVO", nullable = false)
    @Schema(description = "Data alvo para atingir a meta", example = "2025-12-31")
    private LocalDate dataAlvo;

    @ManyToOne
    @JoinColumn(name = "FK_CATEGORIA_ID", referencedColumnName = "ID")
    @Schema(description = "Categoria da meta")
    private Categoria categoria;
}
