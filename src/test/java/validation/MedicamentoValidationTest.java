package validation;

import com.ifpe.clinica.domain.Medicamento;
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
public class MedicamentoValidationTest extends GenericTest {

    @Test
    public void persistirMedicamentoInvalido() {
        Medicamento medicamento = new Medicamento();

        medicamento.setNome("");
        
        medicamento.setDose("");
        
        medicamento.setProntuario(null);

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.persist(medicamento);
            em.flush();
        });

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        constraintViolations.forEach(violation -> {
            String mensagemErro = violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage();

            boolean erroEsperado = 
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Medicamento.nome: O nome do medicamento é obrigatório.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Medicamento.dose: A dose do medicamento é obrigatória.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Medicamento.prontuario: O prontuário associado ao medicamento é obrigatório.");

            assertTrue(erroEsperado, "Violação inesperada no Bean Validation: " + mensagemErro);
        });

        assertEquals(3, constraintViolations.size());
        assertNull(medicamento.getId());
    }

    @Test
    public void atualizarMedicamentoInvalido() {
        TypedQuery<Medicamento> query = em.createQuery("SELECT m FROM Medicamento m", Medicamento.class);
        query.setMaxResults(1);
        Medicamento medicamento = query.getSingleResult();

        StringBuilder doseGigante = new StringBuilder();
        for (int i = 0; i < 55; i++) {
            doseGigante.append("D");
        }
        medicamento.setDose(doseGigante.toString());

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.flush();
        });

        ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();

        assertEquals("A dose do medicamento deve ter no máximo 50 caracteres.", violation.getMessage());
        assertEquals(1, ex.getConstraintViolations().size());
    }
}