package br.com.fiap;

import br.com.fiap.dao.ContaDao;
import br.com.fiap.exception.EntidadeNaoEcontradaException;
import br.com.fiap.model.ContaEntity;
import br.com.fiap.model.TipoConta;

import java.sql.SQLException;
import java.util.ArrayList;

public class Teste {
    public static void main(String[] args) {

        try {
            ContaDao contaDao = new ContaDao();
            ArrayList<ContaEntity> listaContas = new ArrayList<>();

            listaContas.add(new ContaEntity(1,2345, TipoConta.CORRENTE));
            listaContas.add(new ContaEntity(1,4567, TipoConta.POUPANCA));
            listaContas.add(new ContaEntity(1,7899, TipoConta.CORRENTE));
            listaContas.add(new ContaEntity(1,5612, TipoConta.SALARIO));
            listaContas.add(new ContaEntity(1,8934, TipoConta.POUPANCA));

            listaContas.forEach(c -> processEntity(c, contaDao));
            contaDao.getAll().forEach(c -> System.out.println(c.toString()));

            contaDao.fecharConexao();
        } catch (SQLException | EntidadeNaoEcontradaException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void processEntity(ContaEntity c, ContaDao contaDao) {
        try {
            contaDao.delete(c);
            contaDao.insert(c);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
