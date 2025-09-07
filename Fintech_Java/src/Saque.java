public class Saque extends Transacao{
    public Saque(double valor, Conta conta) {
        super(valor, conta);
    }

    public void processar(){
        this.status = StatusTransacao.CONCLUIDA;
        conta.saldo -= valor;
        System.out.println("Saque de R$" + valor + " realizado com sucesso.");
    }

    public void cancelar(){
        if(this.status == StatusTransacao.PENDENTE){
            this.status = StatusTransacao.CANCELADA;
            System.out.println("Saque de R$" + valor + " cancelado.");
        }else{
            System.out.println("Não é possível cancelar um saque que já foi concluído ou cancelado.");
        }
    }


    @Override
    public String toString() {
        return "Saque{" +
                "valor=" + valor +
                ", status=" + status +
                ", conta=" + conta +
                '}';
    }
}
