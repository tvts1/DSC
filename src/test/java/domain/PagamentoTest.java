package domain;

import com.ifpe.clinica.domain.Pagamento;
import com.ifpe.clinica.domain.Consulta;
import com.ifpe.clinica.enums.FormaPagamento;
import com.ifpe.clinica.enums.StatusPagamento;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PagamentoTest extends GenericTest {

    @Test
    public void testPersist() {
        Pagamento pagamento = new Pagamento();

        pagamento.setValorTotal(BigDecimal.valueOf(100.00));
        pagamento.setFormaPagamento(FormaPagamento.PIX);
        pagamento.setStatus(StatusPagamento.PENDENTE);

        Consulta consulta = em.find(Consulta.class, 4L);
        pagamento.setConsulta(consulta);

        em.persist(pagamento);
        em.flush();

        Assertions.assertNotNull(pagamento.getId());
    }

    @Test
    public void testAtualizar() {
        TypedQuery<Pagamento> query = em.createQuery(
                "SELECT p FROM Pagamento p WHERE p.valorTotal = :valor",
                Pagamento.class
        );

        query.setParameter("valor", BigDecimal.valueOf(150.00));
        Pagamento pagamento = query.getSingleResult();
        Assertions.assertNotNull(pagamento);

        pagamento.setValorTotal(BigDecimal.valueOf(160.00));
        em.flush();

        query.setParameter("valor", BigDecimal.valueOf(160.00));
        Assertions.assertNotNull(query.getSingleResult());
    }

    @Test
    public void testAtualizarMerge() {
        TypedQuery<Pagamento> query = em.createQuery(
                "SELECT p FROM Pagamento p WHERE p.valorTotal = :valor",
                Pagamento.class
        );

        query.setParameter("valor", BigDecimal.valueOf(180.00));
        Pagamento pagamento = query.getSingleResult();
        Assertions.assertNotNull(pagamento);

        pagamento.setValorPago(BigDecimal.valueOf(180.00));
        em.clear();

        pagamento = em.merge(pagamento);
        em.flush();

        query.setParameter("valor", BigDecimal.valueOf(180.00));
        Assertions.assertEquals(
                BigDecimal.valueOf(180.00),
                query.getSingleResult().getValorPago()
        );
    }

    @Test
    public void testRemover() {
        TypedQuery<Pagamento> query = em.createQuery(
                "SELECT p FROM Pagamento p WHERE p.valorTotal = :valor",
                Pagamento.class
        );

        query.setParameter("valor", BigDecimal.valueOf(120.00));
        Pagamento pagamento = query.getSingleResult();
        Assertions.assertNotNull(pagamento);

        em.remove(pagamento);
        em.flush();

        query.setParameter("valor", BigDecimal.valueOf(120.00));
        Assertions.assertEquals(0, query.getResultList().size());
    }

    @Test
    public void testFindPagamentoById() {
        Pagamento pagamento = em.find(Pagamento.class, 1L);

        assertNotNull(pagamento);
        assertEquals(FormaPagamento.PIX, pagamento.getFormaPagamento());
        assertEquals(StatusPagamento.PAGO, pagamento.getStatus());
        assertEquals(new BigDecimal("150.00"), pagamento.getValorTotal());
        assertEquals(new BigDecimal("150.00"), pagamento.getValorPago());
    }

}
