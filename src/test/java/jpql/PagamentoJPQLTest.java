package jpql;

import com.ifpe.clinica.domain.Pagamento;
import com.ifpe.clinica.enums.FormaPagamento;
import com.ifpe.clinica.enums.StatusPagamento;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PagamentoJPQLTest extends GenericTest {

    @Test
    public void buscarPagamentosPorFormaEstatus() {
        String jpql = "SELECT p FROM Pagamento p WHERE p.formaPagamento = :forma AND p.status = :status";
        
        TypedQuery<Pagamento> query = em.createQuery(jpql, Pagamento.class);
        query.setParameter("forma", FormaPagamento.PIX);
        query.setParameter("status", StatusPagamento.PAGO);

        List<Pagamento> resultados = query.getResultList();

        Assertions.assertEquals(1, resultados.size());
        Assertions.assertEquals(new BigDecimal("150.00"), resultados.get(0).getValorTotal());
    }

    @Test
    public void somarValorTotalDePagamentosPorForma() {
        String jpql = "SELECT p.formaPagamento, SUM(p.valorTotal) FROM Pagamento p GROUP BY p.formaPagamento HAVING SUM(p.valorTotal) > 100";
        
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        List<Object[]> resultados = query.getResultList();

        Assertions.assertEquals(4, resultados.size());
    }

    @Test
    public void buscarPagamentosDeUmPacienteEspecifico() {
        String jpql = "SELECT p FROM Pagamento p JOIN p.consulta c JOIN c.paciente pac WHERE pac.nome = :nome";
        
        TypedQuery<Pagamento> query = em.createQuery(jpql, Pagamento.class);
        query.setParameter("nome", "João Silva");

        List<Pagamento> resultados = query.getResultList();

        Assertions.assertEquals(1, resultados.size());
        Assertions.assertEquals(new BigDecimal("150.00"), resultados.get(0).getValorTotal());
    }

    @Test
    public void calcularMediaDeDescontos() {
        String jpql = "SELECT AVG(p.desconto) FROM Pagamento p";
        
        TypedQuery<Double> query = em.createQuery(jpql, Double.class);
        Double mediaDesconto = query.getSingleResult();

        Assertions.assertEquals(50.0, mediaDesconto);
    }
}
