package br.com.fiap;

import br.com.fiap.dao.ContaDao;
import br.com.fiap.exception.EntidadeNaoEcontradaException;
import br.com.fiap.model.ContaEntity;
import br.com.fiap.model.TipoConta;
import oracle.jdbc.internal.XSCacheOutput;

import java.sql.SQLException;

public class Teste {
    public static void main(String[] args) {

        try {
            ContaDao contaDao = new ContaDao();
            ContaEntity entity = new ContaEntity(1,2345, TipoConta.CORRENTE);

            contaDao.delete(entity);
            contaDao.insert(entity);
            contaDao.getAll().forEach(c -> System.out.println(c.toString()));

            contaDao.fecharConexao();
        } catch (SQLException | EntidadeNaoEcontradaException e) {
            System.err.println(e.getMessage());
        }
    }
}
