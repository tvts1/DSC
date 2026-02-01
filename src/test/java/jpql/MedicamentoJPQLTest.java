package jpql;

import com.ifpe.clinica.domain.Medicamento;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class MedicamentoJPQLTest extends GenericTest {

    @Test
    public void buscarPorNomeOuDoseForte() {
        String jpql = "SELECT m FROM Medicamento m WHERE m.nome = :nome OR m.dose = :dose";
        
        TypedQuery<Medicamento> query = em.createQuery(jpql, Medicamento.class);
        query.setParameter("nome", "Dipirona");
        query.setParameter("dose", "1000mg");

        List<Medicamento> resultados = query.getResultList();

        Assertions.assertEquals(2, resultados.size(), "Deveria encontrar Dipirona e Dramin");
    }

    @Test
    public void buscarMedicamentosDeUmProntuarioEspecifico() {
        String jpql = "SELECT m FROM Medicamento m JOIN m.prontuario p WHERE p.id = :idProntuario";
        
        TypedQuery<Medicamento> query = em.createQuery(jpql, Medicamento.class);
        query.setParameter("idProntuario", 1L);

        List<Medicamento> resultados = query.getResultList();

        Assertions.assertEquals(2, resultados.size());
    }

    @Test
    public void contarMedicamentosPorDose() {
        String jpql = "SELECT m.dose, COUNT(m) FROM Medicamento m GROUP BY m.dose HAVING COUNT(m) >= 2";
        
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        List<Object[]> resultados = query.getResultList();

        Assertions.assertEquals(1, resultados.size(), "Apenas a dose de 500mg deve ser listada");
        Assertions.assertEquals("500mg", resultados.get(0)[0]); 
        Assertions.assertEquals(2L, resultados.get(0)[1]);
    }

    @Test
    public void listarMedicamentosOrdenadosPeloNome() {
        String jpql = "SELECT m FROM Medicamento m ORDER BY m.nome ASC";
        
        TypedQuery<Medicamento> query = em.createQuery(jpql, Medicamento.class);
        query.setMaxResults(3);
        
        List<Medicamento> resultados = query.getResultList();

        Assertions.assertEquals(3, resultados.size());
        Assertions.assertEquals("Alprazolam", resultados.get(0).getNome());
        Assertions.assertEquals("Dipirona", resultados.get(1).getNome());
    }
}