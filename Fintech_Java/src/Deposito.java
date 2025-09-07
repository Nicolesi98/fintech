public class Deposito extends Transacao{
    public Deposito(double valor, Conta conta) {
        super(valor, conta);
    }

    public void processar(){
        this.status = StatusTransacao.CONCLUIDA;
        conta.saldo += valor;
        System.out.println("Deposito de R$" + valor + " realizado com sucesso.");
    }

    public void cancelar(){
        if(this.status == StatusTransacao.PENDENTE){
            this.status = StatusTransacao.CANCELADA;
            System.out.println("Deposito de R$" + valor + " cancelado.");
        }else{
            System.out.println("Não é possível cancelar um deposito que já foi concluído ou cancelado.");
        }
    }


    @Override
    public String toString() {
        return "Deposito{" +
                "valor=" + valor +
                ", status=" + status +
                ", conta=" + conta +
                '}';
    }
}
