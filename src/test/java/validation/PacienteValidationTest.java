package validation;

import com.ifpe.clinica.domain.Paciente;
import domain.GenericTest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PacienteValidationTest extends GenericTest {
    
    @Test
    public void testPacienteNomeVazio() {
        Paciente p = new Paciente();
        p.setNome(""); // Inválido (@NotBlank)
        p.setCpf("12345678901");
        
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(p);
            em.flush();
        });
    }

    @Test
    public void testPacienteCpfInvalido() {
        Paciente p = new Paciente();
        p.setNome("Teste CPF");
        p.setCpf("123");
        
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(p);
            em.flush();
        });
    }
}
