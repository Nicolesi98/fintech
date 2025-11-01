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

public class ContaTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testContaValida() {
        Conta conta = new Conta();
        conta.setAgencia(1234);
        conta.setNumero(56789);
        conta.setTipoConta(TipoConta.CORRENTE);
        conta.setSaldo(new BigDecimal("1000.00"));

        Set<ConstraintViolation<Conta>> violations = validator.validate(conta);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testAgenciaNula() {
        Conta conta = new Conta();
        conta.setNumero(56789);
        conta.setTipoConta(TipoConta.CORRENTE);
        conta.setSaldo(new BigDecimal("1000.00"));

        Set<ConstraintViolation<Conta>> violations = validator.validate(conta);
        assertEquals(1, violations.size());
        assertEquals("Agência não pode ser nula", violations.iterator().next().getMessage());
    }

    @Test
    public void testNumeroNulo() {
        Conta conta = new Conta();
        conta.setAgencia(1234);
        conta.setTipoConta(TipoConta.CORRENTE);
        conta.setSaldo(new BigDecimal("1000.00"));

        Set<ConstraintViolation<Conta>> violations = validator.validate(conta);
        assertEquals(1, violations.size());
        assertEquals("Número da conta não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testTipoContaNulo() {
        Conta conta = new Conta();
        conta.setAgencia(1234);
        conta.setNumero(56789);
        conta.setSaldo(new BigDecimal("1000.00"));

        Set<ConstraintViolation<Conta>> violations = validator.validate(conta);
        assertEquals(1, violations.size());
        assertEquals("Tipo da conta não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testSaldoNulo() {
        Conta conta = new Conta();
        conta.setAgencia(1234);
        conta.setNumero(56789);
        conta.setTipoConta(TipoConta.CORRENTE);
        conta.setSaldo(null);

        Set<ConstraintViolation<Conta>> violations = validator.validate(conta);
        assertEquals(1, violations.size());
        assertEquals("Saldo não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testSaldoNegativo() {
        Conta conta = new Conta();
        conta.setAgencia(1234);
        conta.setNumero(56789);
        conta.setTipoConta(TipoConta.CORRENTE);
        conta.setSaldo(new BigDecimal("-100.00"));

        Set<ConstraintViolation<Conta>> violations = validator.validate(conta);
        assertEquals(1, violations.size());
        assertEquals("Saldo deve ser positivo ou zero", violations.iterator().next().getMessage());
    }

    @Test
    public void testDataCriacao() {
        Conta conta = new Conta();
        assertNotNull(conta.getDataCriacao());
        assertTrue(conta.getDataCriacao().isBefore(LocalDateTime.now().plusSeconds(1)));
    }
}
