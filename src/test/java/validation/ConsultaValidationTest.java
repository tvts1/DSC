package validation;

import com.ifpe.clinica.domain.Consulta;
import com.ifpe.clinica.domain.Medico;
import com.ifpe.clinica.domain.Paciente;
import domain.GenericTest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConsultaValidationTest extends GenericTest{
    @Test
    public void testConsultaDomingo() {
        Consulta c = new Consulta();
        // Domingo (Inválido @DiaUtil)
        c.setData(LocalDateTime.of(2024, 10, 13, 10, 0)); 
        c.setMedico(em.find(Medico.class, 4L));
        c.setPaciente(em.find(Paciente.class, 1L));

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(c);
            em.flush();
        });
    }

    @Test
    public void testConsultaPassado() {
        Consulta c = new Consulta();
        c.setData(LocalDateTime.now().minusDays(1));
        c.setMedico(em.find(Medico.class, 4L));
        c.setPaciente(em.find(Paciente.class, 1L));

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(c);
            em.flush();
        });
    }
}
