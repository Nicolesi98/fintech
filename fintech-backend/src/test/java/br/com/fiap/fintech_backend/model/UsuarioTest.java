package br.com.fiap.fintech_backend.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUsuarioValido() {
        Usuario usuario = new Usuario();
        usuario.setNome("Nome Válido");
        usuario.setEmail("email@valido.com");
        usuario.setSenha("senhaValida");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNomeNulo() {
        Usuario usuario = new Usuario();
        usuario.setEmail("email@valido.com");
        usuario.setSenha("senhaValida");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertEquals(1, violations.size());
        assertEquals("Nome não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testNomeMuitoLongo() {
        Usuario usuario = new Usuario();
        usuario.setNome("Nome muito longo que excede o limite de cinquenta caracteres");
        usuario.setEmail("email@valido.com");
        usuario.setSenha("senhaValida");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertEquals(1, violations.size());
        assertEquals("Nome deve ter no máximo 50 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmailNulo() {
        Usuario usuario = new Usuario();
        usuario.setNome("Nome Válido");
        usuario.setSenha("senhaValida");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertEquals(1, violations.size());
        assertEquals("Email não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmailInvalido() {
        Usuario usuario = new Usuario();
        usuario.setNome("Nome Válido");
        usuario.setEmail("email-invalido");
        usuario.setSenha("senhaValida");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertEquals(1, violations.size());
        assertEquals("Email inválido", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmailMuitoLongo() {
        Usuario usuario = new Usuario();
        usuario.setNome("Nome Válido");
        usuario.setEmail("emailmuitolongoquecedeocinquentacaracteres@valido.com");
        usuario.setSenha("senhaValida");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertEquals(1, violations.size());
        assertEquals("Email deve ter no máximo 50 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    public void testSenhaNula() {
        Usuario usuario = new Usuario();
        usuario.setNome("Nome Válido");
        usuario.setEmail("email@valido.com");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertEquals(1, violations.size());
        assertEquals("Senha não pode ser nula", violations.iterator().next().getMessage());
    }

    @Test
    public void testSenhaMuitoLonga() {
        Usuario usuario = new Usuario();
        usuario.setNome("Nome Válido");
        usuario.setEmail("email@valido.com");
        usuario.setSenha("senhamuitolongaqueexcedeocinquentacaracteresparaosenhateste");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertEquals(1, violations.size());
        assertEquals("Senha deve ter no máximo 50 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    public void testDataCadastro() {
        Usuario usuario = new Usuario();
        assertNotNull(usuario.getDataCadastro());
        assertTrue(usuario.getDataCadastro().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    public void testContas() {
        Usuario usuario = new Usuario();
        List<Conta> contas = new ArrayList<>();
        contas.add(new Conta());
        usuario.setContas(contas);
        assertEquals(contas, usuario.getContas());
    }

    @Test
    public void testMetas() {
        Usuario usuario = new Usuario();
        List<Meta> metas = new ArrayList<>();
        metas.add(new Meta());
        usuario.setMetas(metas);
        assertEquals(metas, usuario.getMetas());
    }
}
