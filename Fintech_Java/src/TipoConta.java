public enum TipoConta {
    CORRENTE("Conta Corrente"),
    POUPANCA("Conta Poupança"),
    SALARIO("Conta Salário");

    private String descricao;

    TipoConta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
