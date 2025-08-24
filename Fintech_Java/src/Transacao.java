public class Transacao {
    private double valor;
    private Conta conta;

    public Transacao(double valor, Conta conta) {
        this.valor = valor;
        this.conta = conta;
    }

    public void depositar() {
        // Realiza logica de deposito para o valor da transacao na conta
        System.out.println("Depósito de R$" + valor + " realizado com sucesso.");
    }

    public void sacar() {
        // Realiza logica de saque para o valor da transacao na conta
        System.out.println("Saque no valor de R$" + valor + " realizado com sucesso.");
    }

    @Override
    public String toString() {
        return "trasacao{" +
                "valor=" + valor +
                ", conta=" + conta +
                '}';
    }
}
