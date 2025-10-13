package br.com.fiap.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:FREE"; //jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl
    private static final String USUARIO = "system"; //RM565891
    private static final String SENHA = "9594"; //150998

    // Método para obter uma conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

}