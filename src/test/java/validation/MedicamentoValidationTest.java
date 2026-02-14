package validation;

import com.ifpe.clinica.domain.Medicamento;
import domain.GenericTest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MedicamentoValidationTest extends GenericTest{
    @Test
    public void testMedicamentoSemNome() {
        Medicamento m = new Medicamento();
        m.setNome(null);
        m.setDose("500mg");

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(m);
            em.flush();
        });
    }
    
    @Test
    public void testMedicamentoSemDose() {
        Medicamento m = new Medicamento();
        m.setNome("Aspirina");
        m.setDose("");

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(m);
            em.flush();
        });
    }
}
