package validation;

import com.ifpe.clinica.domain.Medico;
import com.ifpe.clinica.enums.Especialidade;
import domain.GenericTest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MedicoValidationTest extends GenericTest{
    
    @Test
    public void testMedicoCrmVazio() {
        Medico m = new Medico();
        m.setNome("Dr. Teste");
        m.setCrm(null); 
        m.setEspecialidade(Especialidade.CARDIOLOGISTA);

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(m);
            em.flush();
        });
    }

    @Test
    public void testMedicoSemEspecialidade() {
        Medico m = new Medico();
        m.setNome("Dr. Teste");
        m.setCrm("12345");
        m.setEspecialidade(null);

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(m);
            em.flush();
        });
    }
}
