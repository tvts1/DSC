package jpql;

import com.ifpe.clinica.domain.Convenio;
import com.ifpe.clinica.enums.TipoConvenio;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConvenioJPQLTest extends GenericTest {

    @Test
    public void testBuscarConveniosSemCarencia() {
        String jpql = "SELECT c FROM Convenio c WHERE c.carenciaDias = 0";

        List<Convenio> resultados = em.createQuery(jpql, Convenio.class).getResultList();

        assertFalse(resultados.isEmpty());
        assertEquals(2, resultados.size());
    }

    @Test
    public void testListarNomesDeConveniosDistintos() {
        String jpql = "SELECT DISTINCT c.nome FROM Convenio c ORDER BY c.nome ASC";

        TypedQuery<String> query = em.createQuery(jpql, String.class);
        List<String> nomes = query.getResultList();

        assertFalse(nomes.isEmpty());

        assertTrue(nomes.contains("Unimed"));
        assertTrue(nomes.contains("SUS"));
        assertTrue(nomes.contains("HAPVIDA"));

        nomes.forEach(nome -> System.out.println("Convênio disponível: " + nome));
    }

    @Test
    public void testUpdateCarenciaPlanosParticulares() {
        String jpql = "UPDATE Convenio c SET c.carenciaDias = :novaCarencia WHERE c.tipo = :tipo";

        int atualizados = em.createQuery(jpql)
                .setParameter("novaCarencia", 15)
                .setParameter("tipo", TipoConvenio.PARTICULAR)
                .executeUpdate();

        assertTrue(atualizados >= 3);

        em.clear();

        Convenio hapvida = em.createQuery("SELECT c FROM Convenio c WHERE c.nome = 'HAPVIDA'", Convenio.class)
                .getSingleResult();
        assertEquals(15, (int) hapvida.getCarenciaDias());
    }

    @Test
    public void testBuscarPorNomeIniciandoCom() {
        String jpql = "SELECT c FROM Convenio c WHERE c.nome LIKE 'Sul%'";

        Convenio sul = em.createQuery(jpql, Convenio.class).getSingleResult();
        assertEquals("SulAmerica", sul.getNome());
    }
}
