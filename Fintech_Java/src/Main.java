//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Usuario usuario = new Usuario();

        usuario.cadastrar("abc", "teste@email.com", "senha123");

        Login login = new Login(usuario);

        login.doLogin();

        Transacao transacao = new Transacao(100.0, usuario.getConta());

        transacao.depositar();

        Categoria categoria = new Categoria("Viagens");

        Meta meta = new Meta(categoria, "Viagem para Paris", 5000.0);

        usuario.registrarMeta(meta);

        meta.adicionarValorAlcancado(1000.0);

    }
}