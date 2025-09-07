import java.time.LocalDateTime;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private LocalDateTime dataCadastro;
    private Conta conta;
    private Meta meta;

    public Usuario(){}

    public void cadastrar(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = LocalDateTime.now();
        this.conta = new ContaCorrente();
    }

    public void registrarMeta(Meta meta){
        this.meta = meta;
    }

    public Conta getConta() {
        return conta;
    }

    public Meta getMeta() {
        return meta;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", conta=" + conta +
                ", meta=" + meta +
                '}';
    }
}
