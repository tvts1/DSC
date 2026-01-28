package jpql;

import com.ifpe.clinica.domain.Exame;
import domain.GenericTest;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExameJPQLTest extends GenericTest {

    @Test
    public void testBuscarExamePorNomeExato() {
        String jpql = "SELECT e FROM Exame e WHERE e.nome = 'Glicemia em Jejum'";

        Exame resultado = em.createQuery(jpql, Exame.class).getSingleResult();

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Glicemia em Jejum", resultado.getNome());
    }

    @Test
    public void testContarTodosExames() {
        String jpql = "SELECT COUNT(e) FROM Exame e";

        Long total = em.createQuery(jpql, Long.class).getSingleResult();

        Assertions.assertTrue(total >= 4);
    }

    @Test
    public void testUpdateNomeExameEspecifico() {
        String jpqlUpdate = "UPDATE Exame e SET e.nome = :novoNome WHERE e.nome = :nomeAntigo";

        int modificados = em.createQuery(jpqlUpdate)
                .setParameter("novoNome", "Urina Tipo 1 (LAB)")
                .setParameter("nomeAntigo", "Urina Tipo 1")
                .executeUpdate();

        em.clear();

        Assertions.assertEquals(1, modificados);

        Exame exame = em.createQuery("SELECT e FROM Exame e WHERE e.nome LIKE '%(LAB)'", Exame.class)
                .getSingleResult();
        Assertions.assertEquals("Urina Tipo 1 (LAB)", exame.getNome());
    }

    @Test
    public void testRemoverExamePorNome() {
        String jpqlDelete = "DELETE FROM Exame e WHERE e.nome = 'Teste de Esforço'";

        int removidos = em.createQuery(jpqlDelete).executeUpdate();

        em.clear();

        Assertions.assertEquals(1, removidos);

        Long contagem = em.createQuery("SELECT COUNT(e) FROM Exame e WHERE e.nome = 'Teste de Esforço'", Long.class)
                .getSingleResult();

        // Corrigido para comparar Long com Long
        Assertions.assertEquals(0L, contagem);
    }
}
