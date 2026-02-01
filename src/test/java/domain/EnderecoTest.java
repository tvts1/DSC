package domain;

import com.ifpe.clinica.domain.Endereco;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnderecoTest extends GenericTest {

    @Test
    public void testPersist() {
        Endereco end = new Endereco();
        end.setRua("Jose da Luz");
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

        query.setParameter("rua", "Rua C");
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
}