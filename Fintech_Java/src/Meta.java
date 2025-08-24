public class Meta {
    private Categoria categoria;
    private String nome;
    private double valorAlvo;
    private double valorAtual;
    private StatusMeta status;

    public Meta(Categoria categoria, String nome, double valorAlvo) {
        this.categoria = categoria;
        this.nome = nome;
        this.valorAlvo = valorAlvo;
        this.valorAtual = 0.0;
        this.status = StatusMeta.ATIVA;
    }

    public void adicionarValorAlcancado(double valor){
        this.valorAtual += valor;
        System.out.println("Valor de R$" + valor + " adicionado à meta '" + nome + "'. Valor atual: R$" + valorAtual);
    }

    @Override
    public String toString() {
        return "Meta{" +
                "categoria=" + categoria +
                ", nome='" + nome + '\'' +
                ", valorAlvo=" + valorAlvo +
                ", valorAtual=" + valorAtual +
                ", status=" + status +
                '}';
    }
}
