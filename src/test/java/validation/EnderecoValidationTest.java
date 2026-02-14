package validation;

import com.ifpe.clinica.domain.Endereco;
import domain.GenericTest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnderecoValidationTest extends GenericTest{
    @Test
    public void testEnderecoEstadoInvalido() {
        Endereco e = new Endereco();
        e.setRua("Rua X");
        e.setCidade("Recife");
        e.setEstado("XX");

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(e);
            em.flush();
        });
    }

    @Test
    public void testEnderecoSemRua() {
        Endereco e = new Endereco();
        e.setRua("");
        e.setCidade("Recife");
        e.setEstado("PE");

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(e);
            em.flush();
        });
    }
}
