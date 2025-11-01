package br.com.fiap.fintech_backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CATEGORIA")
@Schema(description = "Representa uma categoria de transação")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Schema(description = "ID da categoria", example = "1")
    private Long id;

    @NotBlank(message = "Nome da categoria não pode ser nulo")
    @Size(max = 50, message = "Nome da categoria deve ter no máximo 50 caracteres")
    @Column(name = "NOME_CATEGORIA", nullable = false)
    @Schema(description = "Nome da categoria", example = "Alimentação")
    private String nomeCategoria;

    @NotBlank(message = "Tipo da categoria não pode ser nulo")
    @Size(max = 50, message = "Tipo da categoria deve ter no máximo 50 caracteres")
    @Column(name = "TIPO_CATEGORIA", nullable = false)
    @Schema(description = "Tipo da categoria", example = "Despesa")
    private String tipoCategoria;
}
