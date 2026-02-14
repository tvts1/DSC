package validation;

import com.ifpe.clinica.domain.Contato;
import com.ifpe.clinica.domain.ContatoPaciente;
import com.ifpe.clinica.domain.Paciente;
import domain.GenericTest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContatoPacienteValidation extends GenericTest{
    @Test
    public void testContatoEmailInvalido() {
        ContatoPaciente cp = new ContatoPaciente();
        Contato c = new Contato();
        c.setEmail("email_errado");
        cp.setContato(c);
        cp.setPaciente(em.find(Paciente.class, 1L));

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(cp);
            em.flush();
        });
    }
    
    @Test
    public void testContatoTelefoneLetras() {
        ContatoPaciente cp = new ContatoPaciente();
        Contato c = new Contato();
        c.setTelefone("123abc456");
        cp.setContato(c);
        cp.setPaciente(em.find(Paciente.class, 1L));

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(cp);
            em.flush();
        });
    }
}
