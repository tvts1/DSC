package validation;

import com.ifpe.clinica.domain.Convenio;
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
public class ConvenioValidationTest extends GenericTest {

    @Test
    public void persistirConvenioInvalido() {
        Convenio convenio = new Convenio();

        convenio.setNome("");
        convenio.setTipo(null);
        convenio.setCarenciaDias(-1);

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.persist(convenio);
            em.flush();
        });

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        constraintViolations.forEach(violation -> {
            String mensagemErro = violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage();

            boolean erroEsperado = 
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Convenio.nome: O nome do convênio é obrigatório.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Convenio.tipo: O tipo de convênio é obrigatório.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Convenio.carenciaDias: A carência em dias não pode ser negativa.");

            assertTrue(erroEsperado, "Violação inesperada no Bean Validation: " + mensagemErro);
        });

        assertEquals(3, constraintViolations.size());
        assertNull(convenio.getId());
    }

    @Test
    public void atualizarConvenioInvalido() {
        TypedQuery<Convenio> query = em.createQuery("SELECT c FROM Convenio c", Convenio.class);
        query.setMaxResults(1);
        Convenio convenio = query.getSingleResult();

        StringBuilder nomeGigante = new StringBuilder();
        for (int i = 0; i < 105; i++) {
            nomeGigante.append("X");
        }
        convenio.setNome(nomeGigante.toString());

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.flush();
        });

        ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();

        assertEquals("O nome do convênio deve ter no máximo 100 caracteres.", violation.getMessage());
        assertEquals(1, ex.getConstraintViolations().size());
    }
}