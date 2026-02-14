package validation;

import com.ifpe.clinica.domain.Exame;
import domain.GenericTest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExameValidationTest extends GenericTest{
    
    @Test
    public void testExameSemNome() {
        Exame e = new Exame();
        e.setNome(null); 
        
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(e);
            em.flush();
        });
    }

    @Test
    public void testExameNomeVazio() {
        Exame e = new Exame();
        e.setNome("");

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(e);
            em.flush();
        });
    }
}
