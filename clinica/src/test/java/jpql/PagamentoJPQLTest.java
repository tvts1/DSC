package jpql;

import com.ifpe.clinica.domain.Pagamento;
import com.ifpe.clinica.enums.FormaPagamento;
import com.ifpe.clinica.enums.StatusPagamento;
import domain.GenericTest;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PagamentoJPQLTest extends GenericTest {

    @Test
    public void testBuscarPorFormaPagamento() {
        String jpql = "SELECT p FROM Pagamento p WHERE p.formaPagamento = :forma";

        List<Pagamento> pix = em.createQuery(jpql, Pagamento.class)
                .setParameter("forma", FormaPagamento.PIX)
                .getResultList();

        assertFalse(pix.isEmpty());
    }

    @Test
    public void testSomarValorTotal() {
        String jpql = "SELECT SUM(p.valorTotal) FROM Pagamento p";

        BigDecimal total = em.createQuery(jpql, BigDecimal.class)
                .getSingleResult();

        assertNotNull(total);
        assertTrue(total.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    public void testUpdateStatusSimples() {
        String jpql = "UPDATE Pagamento p SET p.status = :status WHERE p.consulta.id = 6";

        int modificado = em.createQuery(jpql)
                .setParameter("status", StatusPagamento.PAGO)
                .executeUpdate();

        assertEquals(1, modificado);
    }

    @Test
    public void testRemoverPagamentoPorId() {
        String jpql = "DELETE FROM Pagamento p WHERE p.consulta.id = :id";

        int removidos = em.createQuery(jpql)
                .setParameter("id", 9L)
                .executeUpdate();

        assertTrue(removidos >= 1);
    }
}
