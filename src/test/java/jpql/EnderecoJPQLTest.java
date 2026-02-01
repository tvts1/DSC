package jpql;

import com.ifpe.clinica.domain.Endereco;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnderecoJPQLTest extends GenericTest {

    @Test
    public void buscarPorCidadeEestadoOuRuaEspecifica() {
        String jpql = "SELECT e FROM Endereco e WHERE (e.estado = :estado AND e.cidade = :cidade) OR e.rua = :rua";
        
        TypedQuery<Endereco> query = em.createQuery(jpql, Endereco.class);
        query.setParameter("estado", "PE");
        query.setParameter("cidade", "Olinda");
        query.setParameter("rua", "Rua D");

        List<Endereco> resultados = query.getResultList();

        Assertions.assertEquals(3, resultados.size());
    }

    @Test
    public void contarQuantosEnderecosTemCadaEstado() {
        String jpql = "SELECT e.estado, COUNT(e) FROM Endereco e GROUP BY e.estado HAVING COUNT(e) > 1";
        
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        List<Object[]> resultados = query.getResultList();

        Assertions.assertFalse(resultados.isEmpty());
        Assertions.assertEquals("PE", resultados.get(0)[0]);
        Assertions.assertTrue((Long) resultados.get(0)[1] > 1);
    }

    @Test
    public void ordenarNomesPelaCidadeERua() {
        String jpql = "SELECT e FROM Endereco e WHERE e.estado = :estado ORDER BY e.cidade DESC, e.rua ASC";
        
        TypedQuery<Endereco> query = em.createQuery(jpql, Endereco.class);
        query.setParameter("estado", "PE");
        query.setMaxResults(2);

        List<Endereco> resultados = query.getResultList();

        Assertions.assertEquals(2, resultados.size());
        Assertions.assertEquals("Recife", resultados.get(0).getCidade());
    }

    @Test
    public void listarNomesDeCidadesSemRepetir() {
        String jpql = "SELECT DISTINCT e.cidade FROM Endereco e WHERE e.estado = 'PE'";
        
        TypedQuery<String> query = em.createQuery(jpql, String.class);
        List<String> cidades = query.getResultList();

        Assertions.assertEquals(3, cidades.size());
        Assertions.assertTrue(cidades.contains("Recife"));
    }
}