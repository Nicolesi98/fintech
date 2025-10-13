package br.com.fiap.model;

import java.time.LocalDateTime;

public class ContaEntity {
    private Long id;
    private int agencia;
    private int numero;
    private TipoConta tipoConta;
    private double saldo;
    private LocalDateTime dataCriacao;

    public ContaEntity(Long id, int agencia, int numero, TipoConta tipoConta, double saldo, LocalDateTime dataCriacao) {
        this.id = id;
        this.agencia = agencia;
        this.numero = numero;
        this.tipoConta = tipoConta;
        this.saldo = saldo;
        this.dataCriacao = dataCriacao;
    }

    public ContaEntity(int agencia, int numero, TipoConta tipoConta) {
        this.agencia = agencia;
        this.numero = numero;
        this.tipoConta = tipoConta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", agencia=" + agencia +
                ", numero=" + numero +
                ", tipoConta=" + tipoConta +
                ", saldo=" + saldo +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
