package domain;

import com.ifpe.clinica.domain.Exame;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExameTest extends GenericTest{

    @Test
    public void testPersist() {
        Exame exame = new Exame();
        exame.setNome("Tomografia Completa");
        exame.getUrlsAnexos().add("https://link.com/ultrassom/01.jpg");
        exame.getUrlsAnexos().add("https://link.com/ultrassom/laudo.pdf");
        em.persist(exame);
        em.flush();         
        assertNotNull(exame.getId());
    }
    
    @Test
    public void testAtualizarExame() {
        TypedQuery<Exame> query = em.createQuery(
                "SELECT e FROM Exame e WHERE e.nome = :nome",
                Exame.class);
        
        query.setParameter("nome", "Raio-X");
        Exame exame = query.getSingleResult();
        Assertions.assertNotNull(exame);

        exame.setNome("Endoscopia");
        em.flush();
        
        query.setParameter("nome", "Raio-X");
        Assertions.assertEquals(0, query.getResultList().size());

        query.setParameter("nome", "Endoscopia");
        Exame atualizado = query.getSingleResult();
        Assertions.assertNotNull(atualizado);
    }
    
    @Test
    public void testAtualizarExameMerge() {
        TypedQuery<Exame> query = em.createQuery(
                "SELECT e FROM Exame e WHERE e.nome = :nome",
                Exame.class);
        
        query.setParameter("nome", "Eletrocardiograma");
        Exame exame = query.getSingleResult();
        Assertions.assertNotNull(exame);
        
        exame.setNome("Exame de Vista");
        em.clear();
        
        exame = (Exame) em.merge(exame);
        em.flush();
        
        query.setParameter("nome", "Eletrocardiograma");
        Assertions.assertEquals(0, query.getResultList().size());

        query.setParameter("nome", "Exame de Vista");
        Exame atualizado = query.getSingleResult();
        Assertions.assertNotNull(atualizado);
    }
    
    @Test
    public void testRemoverEmaxe() {
        TypedQuery<Exame> query = em.createQuery(
                "SELECT e FROM Exame e WHERE e.nome = :nome",
                Exame.class);
        
        query.setParameter("nome", "Tomografia Completa");
        Exame exame = query.getSingleResult();
        Assertions.assertNotNull(exame);

        em.remove(exame);
        em.flush();
        
        query.setParameter("nome", "Tomografia Completa");
        Assertions.assertEquals(0, query.getResultList().size());
    }

    @Test
    public void testFindById() {
        Exame exame = em.find(Exame.class, 1L);
        assertNotNull(exame);
        assertEquals("Hemograma", exame.getNome());
    }
}