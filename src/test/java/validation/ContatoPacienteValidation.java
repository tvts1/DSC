package validation;

import com.ifpe.clinica.domain.ContatoPaciente;
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
public class ContatoPacienteValidation extends GenericTest {

    @Test
    public void persistirContatoPacienteInvalido() {
        ContatoPaciente contatoPaciente = new ContatoPaciente();

        contatoPaciente.setContato(null);
        
        contatoPaciente.setPaciente(null);

        StringBuilder obs = new StringBuilder();
        for (int i = 0; i < 505; i++) {
            obs.append("x");
        }
        contatoPaciente.setObservacoes(obs.toString());

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.persist(contatoPaciente);
            em.flush();
        });

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        constraintViolations.forEach(violation -> {
            String mensagemErro = violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage();

            boolean erroEsperado = 
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.ContatoPaciente.contato: Os dados de contato do paciente são obrigatórios.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.ContatoPaciente.paciente: O paciente associado a este contato é obrigatório.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.ContatoPaciente.observacoes: As observações devem ter no máximo 500 caracteres.");

            assertTrue(erroEsperado, "Violação inesperada no Bean Validation: " + mensagemErro);
        });

        assertEquals(3, constraintViolations.size());
        assertNull(contatoPaciente.getId());
    }

    @Test
    public void atualizarContatoPacienteInvalido() {
        TypedQuery<ContatoPaciente> query = em.createQuery("SELECT cp FROM ContatoPaciente cp", ContatoPaciente.class);
        query.setMaxResults(1);
        ContatoPaciente contatoPaciente = query.getSingleResult();

        contatoPaciente.setPaciente(null);

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.flush();
        });

        ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();

        assertEquals("O paciente associado a este contato é obrigatório.", violation.getMessage());
        assertEquals(1, ex.getConstraintViolations().size());
    }
}