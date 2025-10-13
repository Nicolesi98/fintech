package br.com.fiap.model;

import java.util.stream.Stream;

public enum TipoConta {

    CORRENTE("Conta Corrente"),
    POUPANCA("Conta Poupança"),
    SALARIO("Conta Salário");

    private String descricao;

    TipoConta(String descricao) {
        this.descricao = descricao;
    }

    public static TipoConta fromDescricao(String descricao) {
        return Stream.of(TipoConta.values())
                .filter(tipo -> tipo.getDescricao().equalsIgnoreCase(descricao))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nenhum tipo de conta encontrado com a descrição: " + descricao));
    }

    public String getDescricao() {
        return descricao;
    }
}
