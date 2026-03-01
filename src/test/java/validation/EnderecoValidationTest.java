package validation;

import com.ifpe.clinica.domain.Endereco;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnderecoValidationTest extends GenericTest {

    @Test
    public void persistirEnderecoInvalido() {
        Endereco endereco = new Endereco();

        endereco.setRua(null); 
        endereco.setCidade(null);
        endereco.setEstado("XX");

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.persist(endereco);
            em.flush();
        });

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        constraintViolations.forEach(violation -> {
            String mensagemErro = violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage();

            boolean erroEsperado = 
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Endereco.rua: A rua é obrigatória.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Endereco.cidade: A cidade é obrigatória.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Endereco.estado: A sigla do estado informada é inválida.");

            assertTrue(erroEsperado, "Violação inesperada no Bean Validation: " + mensagemErro);
        });

        assertEquals(3, constraintViolations.size());
        assertNull(endereco.getId());
    }

    @Test
    public void atualizarEnderecoInvalido() {
        TypedQuery<Endereco> query = em.createQuery("SELECT e FROM Endereco e", Endereco.class);
        query.setMaxResults(1);
        Endereco endereco = query.getSingleResult();

        StringBuilder ruaGigante = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            ruaGigante.append("A");
        }
        endereco.setRua(ruaGigante.toString());

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.flush();
        });

        ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();

        assertEquals("A rua deve ter no máximo 255 caracteres.", violation.getMessage());
        assertEquals(1, ex.getConstraintViolations().size());
    }
}