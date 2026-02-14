package validation;

import com.ifpe.clinica.domain.Consulta;
import com.ifpe.clinica.domain.Pagamento;
import com.ifpe.clinica.enums.FormaPagamento;
import domain.GenericTest;
import jakarta.validation.ConstraintViolationException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PagamentoValidationTest extends GenericTest{
    @Test
    public void testPagamentoValorNegativo() {
        Pagamento p = new Pagamento();
        p.setValorTotal(new BigDecimal("-10.00"));
        p.setFormaPagamento(FormaPagamento.PIX);
        p.setConsulta(em.find(Consulta.class, 4L));

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(p);
            em.flush();
        });
    }

    @Test
    public void testPagamentoSemForma() {
        Pagamento p = new Pagamento();
        p.setValorTotal(new BigDecimal("100.00"));
        p.setFormaPagamento(null);
        p.setConsulta(em.find(Consulta.class, 4L));

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            em.persist(p);
            em.flush();
        });
    }
}
