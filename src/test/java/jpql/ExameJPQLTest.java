package jpql;

import com.ifpe.clinica.domain.Exame;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExameJPQLTest extends GenericTest {

    @Test
    public void buscarExamePorNomeOuLinkDeAnexo() {
        String jpql = "SELECT e FROM Exame e JOIN e.urlsAnexos url WHERE e.nome = :nome OR url = :url";
        
        TypedQuery<Exame> query = em.createQuery(jpql, Exame.class);
        query.setParameter("nome", "Hemograma");
        query.setParameter("url", "https://arquivos.clinica.com/laudo/raiox-11.png");

        List<Exame> resultados = query.getResultList();

        Assertions.assertEquals(2, resultados.size());
    }

    @Test
    public void contarAnexosPorExame() {
        String jpql = "SELECT e.nome, COUNT(url) FROM Exame e JOIN e.urlsAnexos url GROUP BY e.nome HAVING COUNT(url) = 1";
        
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        List<Object[]> resultados = query.getResultList();

        Assertions.assertEquals(3, resultados.size());
    }

    @Test
    public void buscarExamesComCertasExtensoesDeArquivo() {
        String jpql = "SELECT DISTINCT e FROM Exame e JOIN e.urlsAnexos url WHERE url LIKE :extensao";
        
        TypedQuery<Exame> query = em.createQuery(jpql, Exame.class);
        query.setParameter("extensao", "%.pdf");

        List<Exame> resultados = query.getResultList();

        Assertions.assertEquals(1, resultados.size());
        Assertions.assertEquals("Hemograma", resultados.get(0).getNome());
    }

    @Test
    public void listarExamesEmOrdemAlfabeticaComLimite() {
        String jpql = "SELECT e FROM Exame e ORDER BY e.nome ASC";
        
        TypedQuery<Exame> query = em.createQuery(jpql, Exame.class);
        query.setMaxResults(2);

        List<Exame> resultados = query.getResultList();

        Assertions.assertEquals(2, resultados.size());
        Assertions.assertEquals("Eletrocardiograma", resultados.get(0).getNome());
        Assertions.assertEquals("Exame de Catarata", resultados.get(1).getNome());
    }
}
