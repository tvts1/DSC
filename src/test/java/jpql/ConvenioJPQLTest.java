package jpql;

import org.junit.jupiter.api.TestInstance;

import com.ifpe.clinica.domain.Convenio;
import com.ifpe.clinica.enums.TipoConvenio;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConvenioJPQLTest extends GenericTest {

    @Test
    public void buscarPorTipoEcareciaMinima() {
        String jpql = "SELECT c FROM Convenio c WHERE c.tipo = :tipo AND c.carenciaDias > 0";
        
        TypedQuery<Convenio> query = em.createQuery(jpql, Convenio.class);
        query.setParameter("tipo", TipoConvenio.PARTICULAR);

        List<Convenio> resultados = query.getResultList();

        Assertions.assertEquals(1, resultados.size());
        Assertions.assertEquals("HAPVIDA", resultados.get(0).getNome());
    }

    @Test
    public void buscarConvêniosComCarenciaEntreDatas() {
        String jpql = "SELECT c FROM Convenio c WHERE c.carenciaDias BETWEEN :min AND :max";
        
        TypedQuery<Convenio> query = em.createQuery(jpql, Convenio.class);
        query.setParameter("min", 1);
        query.setParameter("max", 31);

        List<Convenio> resultados = query.getResultList();

        Assertions.assertEquals(3, resultados.size());
    }

    @Test
    public void contarConveniosPorTipo() {
        String jpql = "SELECT c.tipo, COUNT(c) FROM Convenio c GROUP BY c.tipo HAVING COUNT(c) > 1";
        
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        List<Object[]> resultados = query.getResultList();

        Assertions.assertEquals(2, resultados.size());
    }

    @Test
    public void ordenarPorCarenciaDecrescenteEnome() {
        String jpql = "SELECT c FROM Convenio c ORDER BY c.carenciaDias DESC, c.nome ASC";
        
        TypedQuery<Convenio> query = em.createQuery(jpql, Convenio.class);
        query.setMaxResults(2);

        List<Convenio> resultados = query.getResultList();

        Assertions.assertEquals(2, resultados.size());
        Assertions.assertEquals("Bradesco", resultados.get(0).getNome());
        Assertions.assertEquals("Unimed", resultados.get(1).getNome());
    }
}
