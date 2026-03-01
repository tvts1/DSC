package validation;

import com.ifpe.clinica.domain.Medico;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MedicoValidationTest extends GenericTest {

    @Test
    public void persistirMedicoInvalido() {
        Medico medico = new Medico();

        medico.setNome(""); 

        medico.setCrm("123"); 

        medico.setEspecialidade(null);

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.persist(medico);
            em.flush();
        });

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        constraintViolations.forEach(violation -> {
            String mensagemErro = violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage();

            boolean erroEsperado = 
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Medico.nome: O nome é obrigatório.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Medico.crm: O formato do CRM é inválido. Utilize o formato de números seguidos da sigla do estado (Ex: 12345/SP ou 12345-SP).") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Medico.especialidade: A especialidade do médico é obrigatória.");

            assertTrue(erroEsperado, "Violação inesperada no Bean Validation: " + mensagemErro);
        });

        assertEquals(3, constraintViolations.size());
        assertNull(medico.getId());
    }

    @Test
    public void atualizarMedicoInvalido() {
        TypedQuery<Medico> query = em.createQuery("SELECT m FROM Medico m", Medico.class);
        query.setMaxResults(1);
        Medico medico = query.getSingleResult();

        StringBuilder nomeGigante = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            nomeGigante.append("M");
        }
        medico.setNome(nomeGigante.toString());

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.flush();
        });

        ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();

        assertEquals("O nome deve ter no máximo 255 caracteres.", violation.getMessage());
        assertEquals(1, ex.getConstraintViolations().size());
    }
}