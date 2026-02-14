package validation;

import com.ifpe.clinica.domain.Convenio;
import com.ifpe.clinica.enums.TipoConvenio;
import domain.GenericTest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConvenioValidationTest extends GenericTest{
    @Test
    public void testConvenioNomeVazio() {
        Convenio c = new Convenio();
        c.setNome("");
        c.setTipo(TipoConvenio.PARTICULAR);

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(c);
            em.flush();
        });
    }

    @Test
    public void testConvenioCarenciaNegativa() {
        Convenio c = new Convenio();
        c.setNome("Teste");
        c.setTipo(TipoConvenio.PARTICULAR);
        c.setCarenciaDias(-5);
        
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(c);
            em.flush();
        });
    }
}
