package validation;

import com.ifpe.clinica.domain.FotoPaciente;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FotoPacienteValidationTest extends GenericTest {

    @Test
    public void persistirFotoPacienteInvalida() {
        FotoPaciente foto = new FotoPaciente();

        foto.setTitulo("");
        foto.setImagem(null); 
        foto.setTipoImagem(null); 
        foto.setTamanho(0L); 
        foto.setDataUpload(LocalDateTime.now().plusDays(2));
        foto.setCategoria(null); 
        foto.setPaciente(null);

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.persist(foto);
            em.flush();
        });

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        constraintViolations.forEach(violation -> {
            String mensagemErro = violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage();

            boolean erroEsperado = 
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.FotoPaciente.titulo: O título da foto é obrigatório.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.FotoPaciente.imagem: O arquivo binário da imagem é obrigatório.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.FotoPaciente.tipoImagem: O formato/tipo da imagem é obrigatório.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.FotoPaciente.tamanho: O tamanho informado deve ser maior que 0 bytes.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.FotoPaciente.dataUpload: A data do upload não pode ser uma data futura.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.FotoPaciente.categoria: A categoria da foto é obrigatória.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.FotoPaciente.paciente: O paciente dono da foto é obrigatório.");

            assertTrue(erroEsperado, "Violação inesperada no Bean Validation: " + mensagemErro);
        });

        assertEquals(7, constraintViolations.size());
        assertNull(foto.getId());
    }

    @Test
    public void atualizarFotoPacienteInvalida() {
        TypedQuery<FotoPaciente> query = em.createQuery("SELECT f FROM FotoPaciente f", FotoPaciente.class);
        query.setMaxResults(1);
        FotoPaciente foto = query.getSingleResult();

        StringBuilder descricaoGigante = new StringBuilder();
        for (int i = 0; i < 505; i++) {
            descricaoGigante.append("A");
        }
        foto.setDescricao(descricaoGigante.toString());

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.flush();
        });

        ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();

        assertEquals("A descrição deve ter no máximo 500 caracteres.", violation.getMessage());
        assertEquals(1, ex.getConstraintViolations().size());
    }
}