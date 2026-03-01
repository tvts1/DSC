package validation;

import com.ifpe.clinica.domain.Consulta;
import com.ifpe.clinica.domain.Exame;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConsultaValidationTest extends GenericTest {

    @Test
    public void persistirConsultaInvalida() {
        Consulta consulta = new Consulta();

        consulta.setData(LocalDateTime.of(2023, 10, 15, 10, 0));

        consulta.setMedico(null);
        consulta.setPaciente(null);

        List<Exame> muitosExames = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            muitosExames.add(new Exame());
        }
        consulta.setExames(muitosExames);

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.persist(consulta);
            em.flush();
        });

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        constraintViolations.forEach(violation -> {
            String mensagemErro = violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage();

            boolean erroEsperado = 
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Consulta.data: A consulta deve ser agendada para uma data e hora no futuro") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Consulta.data: Consultas podem ser agendadas apenas em dias úteis") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Consulta.medico: O médico responsável pela consulta é obrigatório") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Consulta.paciente: O paciente da consulta é obrigatório") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Consulta.exames: Uma consulta pode ter no máximo 15 exames associados");

            assertTrue(erroEsperado, "Violação inesperada no Bean Validation: " + mensagemErro);
        });

        assertEquals(5, constraintViolations.size());
        assertNull(consulta.getId());
    }

    @Test
    public void atualizarConsultaInvalida() {
        TypedQuery<Consulta> query = em.createQuery("SELECT c FROM Consulta c", Consulta.class);
        query.setMaxResults(1);
        Consulta consulta = query.getSingleResult();

        consulta.setData(LocalDateTime.of(2023, 10, 16, 10, 0));

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.flush();
        });

        ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();

        assertEquals("A consulta deve ser agendada para uma data e hora no futuro.", violation.getMessage());
        assertEquals(1, ex.getConstraintViolations().size());
    }
}