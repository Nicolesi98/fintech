package br.com.fiap.fintech_backend.repository;

import br.com.fiap.fintech_backend.model.Conta;
import br.com.fiap.fintech_backend.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    Optional<Transacao> findByConta(Conta conta);
}
