package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEcontradaException;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.ContaEntity;
import br.com.fiap.model.TipoConta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class ContaDao {

    private Connection conexao;

    public ContaDao() throws SQLException {
        this.conexao = ConnectionFactory.getConnection();
    }

    public void fecharConexao() throws SQLException{
        conexao.close();
    }

    public void insert(ContaEntity conta) throws SQLException{
        String sql = "INSERT INTO LOCALDB.CONTA(AGENCIA, NUMERO, TIPO_CONTA) VALUES(?, ?, ?)";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setInt(1, conta.getAgencia());
        stm.setInt(2, conta.getNumero());
        stm.setString(3, conta.getTipoConta().getDescricao());
        stm.executeUpdate();
        System.out.println(conta +" cadastrada!");
    }

    public List<ContaEntity> getAll() throws SQLException,EntidadeNaoEcontradaException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM LOCALDB.CONTA");
        ResultSet result = stm.executeQuery();
        List<ContaEntity> contas = new ArrayList<ContaEntity>();
        DateTimeFormatter formatador = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss")
                .optionalStart()
                .appendFraction(ChronoField.NANO_OF_SECOND, 1, 9, true)
                .optionalEnd()
                .toFormatter();

        while (result.next()){
            Long id = result.getLong("id");
            int agencia = result.getInt("agencia");
            int numero = result.getInt("numero");
            TipoConta tipoConta = TipoConta.fromDescricao(result.getString("tipo_conta"));
            Double saldo = result.getDouble("saldo");
            LocalDateTime dataCriacao = LocalDateTime.parse(result.getString("data_criacao"),formatador).withNano(0);
            contas.add(new ContaEntity(id,agencia,numero,tipoConta,saldo,dataCriacao));
        }

        if (contas.isEmpty()) {
            throw new EntidadeNaoEcontradaException("Não há contas cadastradas");
        }

        System.out.println("");
        System.out.println("=== Lista de Contas ===");
        return contas;
    }

    public void update(ContaEntity contaEditada) throws SQLException{

    }

    public void delete(ContaEntity contaExcluir) throws SQLException{
        String sql = "DELETE FROM LOCALDB.CONTA WHERE agencia = ? and numero = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setInt(1, contaExcluir.getAgencia());
        stm.setInt(2, contaExcluir.getNumero());
        stm.executeUpdate();
        System.out.println(contaExcluir +" excluida com sucesso!");
    }
}
