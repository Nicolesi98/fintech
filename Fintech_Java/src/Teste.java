
public class Teste {

    public static void main(String[] args) {
        Usuario usuario = new Usuario();

        usuario.cadastrar("abc", "teste@email.com", "senha123");

        Login login = new Login(usuario);

        login.doLogin();

        Deposito transacao = new Deposito(100.0, usuario.getConta());

        transacao.processar();

        Categoria categoria = new Categoria("Viagens");

        Meta meta = new Meta(categoria, "Viagem para Paris", 5000.0);

        usuario.registrarMeta(meta);

        meta.adicionarValorAlcancado(1000.0);

        Saque saque = new Saque(50.0, usuario.getConta());
        saque.processar();

        System.out.println("Seu saldo final é: R$"+usuario.getConta().consultaSaldo());

    }

}