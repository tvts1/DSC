package validation;

import com.ifpe.clinica.domain.Prontuario;
import domain.GenericTest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProntuarioValidationTest extends GenericTest{
    
    @Test
    public void testProntuarioSemDescricao() {
        Prontuario p = new Prontuario();
        p.setDescricao(null); 

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(p);
            em.flush();
        });
    }
    
    @Test
    public void testProntuarioDescricaoVazia() {
        Prontuario p = new Prontuario();
        p.setDescricao(""); 

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(p);
            em.flush();
        });
    }
}
