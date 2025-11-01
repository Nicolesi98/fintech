package br.com.fiap.fintech_backend.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CategoriaTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testCategoriaValida() {
        Categoria categoria = new Categoria();
        categoria.setNomeCategoria("Alimentação");
        categoria.setTipoCategoria("Despesa");

        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNomeCategoriaNulo() {
        Categoria categoria = new Categoria();
        categoria.setTipoCategoria("Despesa");

        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);
        assertEquals(1, violations.size());
        assertEquals("Nome da categoria não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testNomeCategoriaMuitoLongo() {
        Categoria categoria = new Categoria();
        categoria.setNomeCategoria("Nome muito longo que excede o limite de cinquenta caracteres");
        categoria.setTipoCategoria("Despesa");

        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);
        assertEquals(1, violations.size());
        assertEquals("Nome da categoria deve ter no máximo 50 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    public void testTipoCategoriaNulo() {
        Categoria categoria = new Categoria();
        categoria.setNomeCategoria("Alimentação");

        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);
        assertEquals(1, violations.size());
        assertEquals("Tipo da categoria não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testTipoCategoriaMuitoLongo() {
        Categoria categoria = new Categoria();
        categoria.setNomeCategoria("Alimentação");
        categoria.setTipoCategoria("Tipo muito longo que excede o limite de cinquenta caracteres");

        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);
        assertEquals(1, violations.size());
        assertEquals("Tipo da categoria deve ter no máximo 50 caracteres", violations.iterator().next().getMessage());
    }
}
