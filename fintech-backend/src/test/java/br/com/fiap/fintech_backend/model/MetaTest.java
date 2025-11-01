package br.com.fiap.fintech_backend.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MetaTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testMetaValida() {
        Meta meta = new Meta();
        meta.setNome("Viagem para a praia");
        meta.setValorAlvo(new BigDecimal("5000.00"));
        meta.setValorAtual(new BigDecimal("1500.00"));
        meta.setStatus(StatusMeta.ATIVA);
        meta.setDataAlvo(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<Meta>> violations = validator.validate(meta);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNomeNulo() {
        Meta meta = new Meta();
        meta.setValorAlvo(new BigDecimal("5000.00"));
        meta.setValorAtual(new BigDecimal("1500.00"));
        meta.setStatus(StatusMeta.ATIVA);
        meta.setDataAlvo(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<Meta>> violations = validator.validate(meta);
        assertEquals(1, violations.size());
        assertEquals("Nome da meta não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testValorAlvoNulo() {
        Meta meta = new Meta();
        meta.setNome("Viagem para a praia");
        meta.setValorAtual(new BigDecimal("1500.00"));
        meta.setStatus(StatusMeta.ATIVA);
        meta.setDataAlvo(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<Meta>> violations = validator.validate(meta);
        assertEquals(1, violations.size());
        assertEquals("Valor alvo não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testValorAtualNulo() {
        Meta meta = new Meta();
        meta.setNome("Viagem para a praia");
        meta.setValorAlvo(new BigDecimal("5000.00"));
        meta.setValorAtual(null);
        meta.setStatus(StatusMeta.ATIVA);
        meta.setDataAlvo(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<Meta>> violations = validator.validate(meta);
        assertEquals(1, violations.size());
        assertEquals("Valor atual não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testValorAtualNegativo() {
        Meta meta = new Meta();
        meta.setNome("Viagem para a praia");
        meta.setValorAlvo(new BigDecimal("5000.00"));
        meta.setValorAtual(new BigDecimal("-100.00"));
        meta.setStatus(StatusMeta.ATIVA);
        meta.setDataAlvo(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<Meta>> violations = validator.validate(meta);
        assertEquals(1, violations.size());
        assertEquals("Valor atual deve ser positivo ou zero", violations.iterator().next().getMessage());
    }

    @Test
    public void testStatusNulo() {
        Meta meta = new Meta();
        meta.setNome("Viagem para a praia");
        meta.setValorAlvo(new BigDecimal("5000.00"));
        meta.setValorAtual(new BigDecimal("1500.00"));
        meta.setDataAlvo(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<Meta>> violations = validator.validate(meta);
        assertEquals(1, violations.size());
        assertEquals("Status da meta não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testDataAlvoNula() {
        Meta meta = new Meta();
        meta.setNome("Viagem para a praia");
        meta.setValorAlvo(new BigDecimal("5000.00"));
        meta.setValorAtual(new BigDecimal("1500.00"));
        meta.setStatus(StatusMeta.ATIVA);

        Set<ConstraintViolation<Meta>> violations = validator.validate(meta);
        assertEquals(1, violations.size());
        assertEquals("Data alvo não pode ser nula", violations.iterator().next().getMessage());
    }

    @Test
    public void testDataAlvoNoPassado() {
        Meta meta = new Meta();
        meta.setNome("Viagem para a praia");
        meta.setValorAlvo(new BigDecimal("5000.00"));
        meta.setValorAtual(new BigDecimal("1500.00"));
        meta.setStatus(StatusMeta.ATIVA);
        meta.setDataAlvo(LocalDate.now().minusDays(1));

        Set<ConstraintViolation<Meta>> violations = validator.validate(meta);
        assertEquals(1, violations.size());
        assertEquals("Data alvo deve ser no futuro", violations.iterator().next().getMessage());
    }

    @Test
    public void testDataCriacao() {
        Meta meta = new Meta();
        assertNotNull(meta.getDataCriacao());
        assertTrue(meta.getDataCriacao().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    public void testCategoria() {
        Meta meta = new Meta();
        Categoria categoria = new Categoria();
        meta.setCategoria(categoria);
        assertEquals(categoria, meta.getCategoria());
    }
}
