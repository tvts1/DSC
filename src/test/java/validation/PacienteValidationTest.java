package validation;

import com.ifpe.clinica.domain.Paciente;
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
public class PacienteValidationTest extends GenericTest {

    @Test
    public void persistirPacienteInvalido() {
        Paciente paciente = new Paciente();

        paciente.setNome(""); 

        paciente.setCpf("00000000000"); 

        paciente.setEndereco(null);

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.persist(paciente);
            em.flush();
        });

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        constraintViolations.forEach(violation -> {
            String mensagemErro = violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage();

            boolean erroEsperado = 
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Paciente.nome: O nome é obrigatório.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Paciente.cpf: O número do CPF informado é inválido.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Paciente.endereco: O endereço do paciente é obrigatório.");

            assertTrue(erroEsperado, "Violação inesperada no Bean Validation: " + mensagemErro);
        });

        assertEquals(3, constraintViolations.size());
        assertNull(paciente.getId());
    }

    @Test
    public void atualizarPacienteInvalido() {
        TypedQuery<Paciente> query = em.createQuery("SELECT p FROM Paciente p", Paciente.class);
        query.setMaxResults(1);
        Paciente paciente = query.getSingleResult();

        paciente.setCpf(null);

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.flush();
        });

        ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();

        assertEquals("O CPF do paciente é obrigatório.", violation.getMessage());
        assertEquals(1, ex.getConstraintViolations().size());
    }
}