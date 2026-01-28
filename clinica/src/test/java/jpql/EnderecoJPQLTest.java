package jpql;

import com.ifpe.clinica.domain.Endereco;
import domain.GenericTest;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnderecoJPQLTest extends GenericTest {

    @Test
    public void testBuscarEnderecosNoNordeste() {
        String jpql = "SELECT e FROM Endereco e WHERE e.estado IN ('PE', 'MA', 'CE')";
        
        List<Endereco> resultados = em.createQuery(jpql, Endereco.class).getResultList();
        
        Assertions.assertTrue(resultados.size() >= 3);
    }

    @Test
    public void testContarEnderecosPorEstado() {
        String jpql = "SELECT COUNT(e) FROM Endereco e WHERE e.estado = 'CE'";

        Long total = em.createQuery(jpql, Long.class).getSingleResult();

        Assertions.assertTrue(total >= 1);
    }

    @Test
    public void testBuscarCidadePorRuaEspecifica() {
        String jpql = "SELECT e.cidade FROM Endereco e WHERE e.rua = 'Av. Paulista'";

        String cidade = em.createQuery(jpql, String.class).getSingleResult();

        Assertions.assertEquals("Sao Paulo", cidade);
    }

    @Test
    public void testBuscarCidadesPorIniciais() {
        String jpql = "SELECT e.cidade FROM Endereco e WHERE e.cidade LIKE 'Sao%'";

        List<String> cidades = em.createQuery(jpql, String.class).getResultList();

        Assertions.assertTrue(cidades.size() >= 2);
        Assertions.assertTrue(cidades.contains("Sao Paulo"));
    }
}
