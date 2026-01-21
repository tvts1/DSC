package domain;

import com.ifpe.clinica.domain.Endereco;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnderecoTest extends GenericTest {

    @Test
    public void testPersist() {
        Endereco end = new Endereco();
        end.setRua("Rua Nova");
        end.setCidade("Olinda");
        end.setEstado("PE");

        em.persist(end);
        em.flush();
        Assertions.assertNotNull(end.getId(), "ID deve ter sido gerado após persist");
    }

    @Test
    public void testAtualizarEndereco() {
        TypedQuery<Endereco> query = em.createQuery(
                "SELECT e FROM Endereco e WHERE e.rua = :rua",
                Endereco.class);

        query.setParameter("rua", "Rua C");  // Busca Rua C (não Rua A)
        Endereco endereco = query.getSingleResult();
        Assertions.assertNotNull(endereco);

        endereco.setRua("Nova Rua");
        endereco.setEstado("PE");
        endereco.setCidade("Petrolina");
        em.flush();

        query.setParameter("rua", "Rua C");
        Assertions.assertEquals(0, query.getResultList().size());

        query.setParameter("rua", "Nova Rua");
        Endereco atualizado = query.getSingleResult();
        Assertions.assertNotNull(atualizado);
    }

    @Test
    @SuppressWarnings("UnusedAssignment")
    public void testAtualizarEnderecoMerge() {
        TypedQuery<Endereco> query = em.createQuery(
                "SELECT e FROM Endereco e WHERE e.rua = :rua",
                Endereco.class);

        query.setParameter("rua", "Rua B");  
        Endereco endereco = query.getSingleResult();
        Assertions.assertNotNull(endereco);

        endereco.setRua("Avenida Brasil");
        endereco.setEstado("SP");
        endereco.setCidade("Sao Paulo");
        em.clear(); 

        endereco = (Endereco) em.merge(endereco);
        em.flush();

        query.setParameter("rua", "Rua B");
        Assertions.assertEquals(0, query.getResultList().size());

        query.setParameter("rua", "Avenida Brasil");
        Endereco atualizado = query.getSingleResult();
        Assertions.assertNotNull(atualizado);
    }
    
    @Test
    public void testRemoverEndereco() {
        TypedQuery<Endereco> query = em.createQuery(
                "SELECT e FROM Endereco e WHERE e.rua = :rua",
                Endereco.class);
        
        query.setParameter("rua", "Rua D");
        Endereco endereco = query.getSingleResult();
        Assertions.assertNotNull(endereco);
        
        em.remove(endereco);
        em.flush();
        
        query.setParameter("rua", "Rua D");
        Assertions.assertEquals(0, query.getResultList().size());
    }

    @Test
    public void testFindById() {
        Endereco e = em.find(Endereco.class, 1L);
        Assertions.assertEquals("Rua A", e.getRua());
    }

    @Test
    public void testBuscarPorEstado() {
        String jpql = "SELECT e FROM Endereco e WHERE e.estado = :estado";
        
        List<Endereco> resultados = em.createQuery(jpql, Endereco.class)
                .setParameter("estado", "PE")
                .getResultList();

        Assertions.assertTrue(resultados.size() >= 3);
    }

    @Test
    public void testContarPorCidade() {
        String jpql = "SELECT COUNT(e) FROM Endereco e WHERE e.cidade = :cidade";
        
        Long total = em.createQuery(jpql, Long.class)
                .setParameter("cidade", "Olinda")
                .getSingleResult();

        Assertions.assertEquals(2L, total);
    }

    @Test
    public void testBuscarEstadosDiferentes() {
        String jpql = "SELECT e FROM Endereco e WHERE e.estado NOT IN ('PE')";
        
        List<Endereco> resultados = em.createQuery(jpql, Endereco.class)
                .getResultList();

        Assertions.assertFalse(resultados.isEmpty());
        Assertions.assertEquals("SP", resultados.get(0).getEstado());
    }

    @Test
    public void testListarCidadesUnicas() {
        String jpql = "SELECT DISTINCT e.cidade FROM Endereco e ORDER BY e.cidade";
        
        List<String> cidades = em.createQuery(jpql, String.class)
                .getResultList();

        Assertions.assertTrue(cidades.contains("Olinda"));
        Assertions.assertTrue(cidades.size() >= 3);
    }

    @Test
    public void testBuscarRuaParcial() {
        String jpql = "SELECT e FROM Endereco e WHERE e.rua LIKE :termo";
        
        List<Endereco> resultados = em.createQuery(jpql, Endereco.class)
                .setParameter("termo", "Rua%")
                .getResultList();

        Assertions.assertFalse(resultados.isEmpty());
    }
}
