public class Login {

    private Usuario usuario;



    // Construtor padrão

    public Login() {}



    // Construtor com parâmetros
    public Login(Usuario usuario) {
        this.usuario = usuario;
    }



    // Exemplo de método

    public void doLogin() {
        // Lógica para realizar o login e validar senha
        System.out.println("Realizando login para o usuário: " + usuario);
    }

}