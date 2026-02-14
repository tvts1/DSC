package validation;

import com.ifpe.clinica.domain.FotoPaciente;
import com.ifpe.clinica.domain.Paciente;
import domain.GenericTest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FotoPacienteValidationTest extends GenericTest{
    @Test
    public void testFotoSemTitulo() {
        FotoPaciente f = new FotoPaciente();
        f.setTitulo("");
        f.setPaciente(em.find(Paciente.class, 1L));

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(f);
            em.flush();
        });
    }

    @Test
    public void testFotoSemPaciente() {
        FotoPaciente f = new FotoPaciente();
        f.setTitulo("Foto");
        f.setPaciente(null);

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(f);
            em.flush();
        });
    }
}
