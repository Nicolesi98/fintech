public class Transacao {
    protected double valor;
    protected Conta conta;
    protected StatusTransacao status;

    public Transacao(double valor, Conta conta) {
        this.valor = valor;
        this.conta = conta;
        this.status = StatusTransacao.PENDENTE;
    }

}
