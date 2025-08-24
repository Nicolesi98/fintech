public enum StatusMeta {
    ATIVA("Ativa"),
    CONCLUIDA("Concluída"),
    CANCELADA("Cancelada");

    private String descricao;

    StatusMeta(String descricao) {
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
