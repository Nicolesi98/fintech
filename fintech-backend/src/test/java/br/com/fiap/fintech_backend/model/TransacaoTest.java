package br.com.fiap.fintech_backend.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TransacaoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTransacaoValida() {
        Transacao transacao = new Transacao();
        transacao.setDescricao("Almoço no restaurante");
        transacao.setValor(new BigDecimal("50.00"));
        transacao.setTipo(StatusTransacao.PENDENTE);
        transacao.setConta(new Conta());
        transacao.setCategoria(new Categoria());

        Set<ConstraintViolation<Transacao>> violations = validator.validate(transacao);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testDescricaoNula() {
        Transacao transacao = new Transacao();
        transacao.setValor(new BigDecimal("50.00"));
        transacao.setTipo(StatusTransacao.PENDENTE);
        transacao.setConta(new Conta());
        transacao.setCategoria(new Categoria());

        Set<ConstraintViolation<Transacao>> violations = validator.validate(transacao);
        assertEquals(1, violations.size());
        assertEquals("Descrição não pode ser nula", violations.iterator().next().getMessage());
    }

    @Test
    public void testValorNulo() {
        Transacao transacao = new Transacao();
        transacao.setDescricao("Almoço no restaurante");
        transacao.setTipo(StatusTransacao.PENDENTE);
        transacao.setConta(new Conta());
        transacao.setCategoria(new Categoria());

        Set<ConstraintViolation<Transacao>> violations = validator.validate(transacao);
        assertEquals(1, violations.size());
        assertEquals("Valor não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testTipoNulo() {
        Transacao transacao = new Transacao();
        transacao.setDescricao("Almoço no restaurante");
        transacao.setValor(new BigDecimal("50.00"));
        transacao.setConta(new Conta());
        transacao.setCategoria(new Categoria());

        Set<ConstraintViolation<Transacao>> violations = validator.validate(transacao);
        assertEquals(1, violations.size());
        assertEquals("Tipo da transação não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testDataCriacao() {
        Transacao transacao = new Transacao();
        assertNotNull(transacao.getDataCriacao());
        assertTrue(transacao.getDataCriacao().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    public void testConta() {
        Transacao transacao = new Transacao();
        Conta conta = new Conta();
        transacao.setConta(conta);
        assertEquals(conta, transacao.getConta());
    }

    @Test
    public void testCategoria() {
        Transacao transacao = new Transacao();
        Categoria categoria = new Categoria();
        transacao.setCategoria(categoria);
        assertEquals(categoria, transacao.getCategoria());
    }
}
