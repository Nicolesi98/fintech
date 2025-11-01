package br.com.fiap.fintech_backend.repository;

import br.com.fiap.fintech_backend.model.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {
}
