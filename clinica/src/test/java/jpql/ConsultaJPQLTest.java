package jpql;

import static org.junit.jupiter.api.Assertions.*;
import com.ifpe.clinica.domain.Convenio;
import com.ifpe.clinica.enums.TipoConvenio;
import domain.GenericTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConsultaJPQLTest extends GenericTest {
    @Test
    public void testBuscarConvenioPorNomeExato() {
        String jpql = "SELECT c FROM Convenio c WHERE c.nome = 'Bradesco Saude'";

        Convenio resultado = em.createQuery(jpql, Convenio.class).getSingleResult();

        assertNotNull(resultado);
        assertEquals(15, (int) resultado.getCarenciaDias());
        assertEquals(TipoConvenio.EMPRESARIAL, resultado.getTipo());
    }

    @Test
    public void testContarConveniosEmpresariais() {
        String jpql = "SELECT COUNT(c) FROM Convenio c WHERE c.tipo = :tipo";

        Long total = em.createQuery(jpql, Long.class)
                .setParameter("tipo", TipoConvenio.EMPRESARIAL)
                .getSingleResult();

        assertTrue(total >= 3, "Deveria encontrar ao menos os 3 convênios empresariais do XML");
    }

    @Test
    public void testBuscarConvenioComMaiorCarencia() {
        String jpql = "SELECT MAX(c.carenciaDias) FROM Convenio c";

        Integer maxCarencia = em.createQuery(jpql, Integer.class).getSingleResult();

        assertTrue(maxCarencia >= 90);
    }

    @Test
    public void testFiltrarPorCarenciaETipo() {
        String jpql = "SELECT c FROM Convenio c WHERE c.carenciaDias = 10 AND c.tipo = :tipo";

        Convenio casssi = em.createQuery(jpql, Convenio.class)
                .setParameter("tipo", TipoConvenio.OUTROS)
                .getSingleResult();

        assertEquals("Casssi", casssi.getNome());
    }
}
