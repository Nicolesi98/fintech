package br.com.fiap.fintech_backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "USUARIO")
@Schema(description = "Representa um usuário do sistema")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Schema(description = "ID único do usuário")
    private Long id;

    @NotBlank(message = "Nome não pode ser nulo")
    @Size(max = 50, message = "Nome deve ter no máximo 50 caracteres")
    @Column(name = "NOME", nullable = false)
    @Schema(description = "Nome do usuário")
    private String nome;

    @NotBlank(message = "Email não pode ser nulo")
    @Email(message = "Email inválido")
    @Size(max = 50, message = "Email deve ter no máximo 50 caracteres")
    @Column(name = "EMAIL", nullable = false, unique = true)
    @Schema(description = "Email do usuário", example = "usuario@example.com")
    private String email;

    @NotBlank(message = "Senha não pode ser nula")
    @Size(max = 50, message = "Senha deve ter no máximo 50 caracteres")
    @Column(name = "SENHA", nullable = false)
    @Schema(description = "Senha do usuário")
    private String senha;

    @Column(name = "DATA_CADASTRO", nullable = false, updatable = false)
    @Schema(description = "Data de cadastro do usuário")
    private LocalDateTime dataCadastro = LocalDateTime.now();

    @ManyToMany
    @JoinTable(
            name = "USUARIO_CONTA",
            joinColumns = @JoinColumn(name = "FK_USUARIO_ID"),
            inverseJoinColumns = @JoinColumn(name = "FK_CONTA_ID")
    )
    @Schema(description = "Lista de contas associadas ao usuário")
    private List<Conta> contas;

    @ManyToMany
    @JoinTable(
            name = "USUARIO_META",
            joinColumns = @JoinColumn(name = "FK_USUARIO_ID"),
            inverseJoinColumns = @JoinColumn(name = "FK_META_ID")
    )
    @Schema(description = "Lista de metas associadas ao usuário")
    private List<Meta> metas;
}
