package validation;

import com.ifpe.clinica.domain.Exame;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExameValidationTest extends GenericTest {

    @Test
    public void persistirExameInvalido() {
        Exame exame = new Exame();

        exame.setNome(""); 

        List<String> muitosAnexos = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            muitosAnexos.add("arquivo_laudo_" + i + ".pdf");
        }
        exame.setUrlsAnexos(muitosAnexos);

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.persist(exame);
            em.flush();
        });

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        constraintViolations.forEach(violation -> {
            String mensagemErro = violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage();

            boolean erroEsperado = 
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Exame.nome: O nome do exame é obrigatório.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Exame.urlsAnexos: Um exame pode ter no máximo 10 anexos associados.");

            assertTrue(erroEsperado, "Violação inesperada no Bean Validation: " + mensagemErro);
        });

        assertEquals(2, constraintViolations.size());
        assertNull(exame.getId());
    }

    @Test
    public void atualizarExameInvalido() {
        TypedQuery<Exame> query = em.createQuery("SELECT e FROM Exame e", Exame.class);
        query.setMaxResults(1);
        Exame exame = query.getSingleResult();

        StringBuilder nomeGigante = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            nomeGigante.append("E");
        }
        exame.setNome(nomeGigante.toString());

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.flush();
        });

        ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();

        assertEquals("O nome do exame deve ter no máximo 255 caracteres.", violation.getMessage());
        assertEquals(1, ex.getConstraintViolations().size());
    }
}