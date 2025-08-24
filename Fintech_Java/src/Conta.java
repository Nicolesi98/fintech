import java.util.Random;

public class Conta {
    private int agencia;
    private int numero;
    private TipoConta tipoConta;
    private double saldo;

    public Conta(TipoConta tipoConta) {
        this.agencia = 2;
        this.numero = new Random().nextInt(100000) + 1;
        this.tipoConta = tipoConta;
        this.saldo = 0.0;
    }

    public double consultaSaldo() {
        return saldo;
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
