import java.math.BigDecimal;
import java.util.Random;

public class Conta{
    protected int agencia;
    protected int numero;
    protected double saldo;

    public Conta() {
        this.agencia = 2;
        this.numero = new Random().nextInt(100000) + 1;
        this.saldo = 0.0;
    }

    public double consultaSaldo() {
        return saldo;
    }

}
