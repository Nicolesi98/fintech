
public class ContaCorrente extends Conta{
    private TipoConta tipoConta;

    public ContaCorrente() {
        super();
        this.tipoConta = TipoConta.CORRENTE;
    }


    @Override
    public String toString() {
        return "Conta{" +
                "agencia=" + agencia +
                ", numero=" + numero +
                ", tipoConta=" + tipoConta +
                ", saldo=" + saldo +
                '}';
    }

}
