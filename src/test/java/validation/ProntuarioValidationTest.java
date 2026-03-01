package validation;

import com.ifpe.clinica.domain.Prontuario;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProntuarioValidationTest extends GenericTest {

    @Test
    public void persistirProntuarioInvalido() {
        Prontuario prontuario = new Prontuario();

        prontuario.setDescricao(""); 
        
        prontuario.setPaciente(null);

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.persist(prontuario);
            em.flush();
        });

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        constraintViolations.forEach(violation -> {
            String mensagemErro = violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage();

            boolean erroEsperado = 
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Prontuario.descricao: A descrição do prontuário é obrigatória.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Prontuario.paciente: O paciente dono do prontuário é obrigatório.");

            assertTrue(erroEsperado, "Violação inesperada no Bean Validation: " + mensagemErro);
        });

        assertEquals(2, constraintViolations.size());
        assertNull(prontuario.getId());
    }

    @Test
    public void atualizarProntuarioInvalido() {
        TypedQuery<Prontuario> query = em.createQuery("SELECT p FROM Prontuario p", Prontuario.class);
        query.setMaxResults(1);
        Prontuario prontuario = query.getSingleResult();

        StringBuilder descricaoGigante = new StringBuilder();
        for (int i = 0; i < 505; i++) {
            descricaoGigante.append("P");
        }
        prontuario.setDescricao(descricaoGigante.toString());

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.flush();
        });

        ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();

        assertEquals("A descrição do prontuário deve ter no máximo 500 caracteres.", violation.getMessage());
        assertEquals(1, ex.getConstraintViolations().size());
    }
}