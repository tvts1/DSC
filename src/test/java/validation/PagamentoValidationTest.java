package validation;

import com.ifpe.clinica.domain.Pagamento;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PagamentoValidationTest extends GenericTest {

    @Test
    public void persistirPagamentoInvalido() {
        Pagamento pagamento = new Pagamento();

        pagamento.setConsulta(null); // Viola @NotNull
        pagamento.setValorTotal(new BigDecimal("-10.00"));
        
        pagamento.setFormaPagamento(com.ifpe.clinica.enums.FormaPagamento.DINHEIRO);
        pagamento.setStatus(com.ifpe.clinica.enums.StatusPagamento.PENDENTE);
        pagamento.setDataGeracao(LocalDateTime.now());

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.persist(pagamento);
            em.flush();
        });

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        constraintViolations.forEach(violation -> {
            String mensagemErro = violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage();

            boolean erroEsperado = 
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Pagamento.consulta: A consulta associada ao pagamento é obrigatória.") ||
                    mensagemErro.startsWith("class com.ifpe.clinica.domain.Pagamento.valorTotal: O valor total deve ser maior que zero.");

            assertTrue(erroEsperado, "Violação inesperada no Bean Validation: " + mensagemErro);
        });

        assertEquals(2, constraintViolations.size());
        assertNull(pagamento.getId());
    }

    @Test
    public void atualizarPagamentoInvalido() {
        TypedQuery<Pagamento> query = em.createQuery("SELECT p FROM Pagamento p", Pagamento.class);
        query.setMaxResults(1);
        Pagamento pagamento = query.getSingleResult();

        pagamento.setDataPagamento(LocalDateTime.now().plusDays(5));

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            em.flush();
        });

        ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();

        assertEquals("A data do pagamento não pode ser uma data futura.", violation.getMessage());
        assertEquals(1, ex.getConstraintViolations().size());
    }
}