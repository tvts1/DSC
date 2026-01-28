package domain;

import com.ifpe.clinica.domain.Convenio;
import com.ifpe.clinica.enums.TipoConvenio;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConvenioTest extends GenericTest {

    @Test
    public void testPersist() {
        Convenio c = new Convenio();
        c.setNome("Amil");
        c.setTipo(TipoConvenio.EMPRESARIAL);
        c.setCarenciaDias(20);

        em.persist(c);
        em.flush();

        Assertions.assertNotNull(c.getId());
    }

    @Test
    public void testAtualizarConvenio() {
        TypedQuery<Convenio> query = em.createQuery(
                "SELECT c FROM Convenio c WHERE c.nome = :nome",
                Convenio.class
        );

        query.setParameter("nome", "Unimed");
        Convenio convenio = query.getSingleResult();
        Assertions.assertNotNull(convenio);

        convenio.setNome("Unimed Premium");
        convenio.setCarenciaDias(15);
        em.flush();

        query.setParameter("nome", "Unimed Premium");
        Convenio atualizado = query.getSingleResult();
        Assertions.assertNotNull(atualizado);
    }

    @Test
    public void testAtualizaConvenioMerge() {
        TypedQuery<Convenio> query = em.createQuery(
                "SELECT c FROM Convenio c WHERE c.nome = :nome",
                Convenio.class
        );

        query.setParameter("nome", "SUS");
        Convenio convenio = query.getSingleResult();
        Assertions.assertNotNull(convenio);

        convenio.setNome("SUS Atualizado");
        em.clear();

        convenio = em.merge(convenio);
        em.flush();

        query.setParameter("nome", "SUS");
        Assertions.assertEquals(0, query.getResultList().size());

        query.setParameter("nome", "SUS Atualizado");
        Convenio atualizado = query.getSingleResult();
        Assertions.assertNotNull(atualizado);
    }

    @Test
    public void testRemoverConvenio() {
        TypedQuery<Convenio> query = em.createQuery(
                "SELECT c FROM Convenio c WHERE c.nome = :nome",
                Convenio.class
        );

        query.setParameter("nome", "Particular");
        Convenio convenio = query.getSingleResult();
        Assertions.assertNotNull(convenio);

        em.remove(convenio);
        em.flush();

        query.setParameter("nome", "Particular");
        Assertions.assertEquals(0, query.getResultList().size());
    }

    @Test
    public void testFindById() {
        Convenio c = em.find(Convenio.class, 1L);
        Assertions.assertEquals("Unimed", c.getNome());
        Assertions.assertEquals(TipoConvenio.EMPRESARIAL, c.getTipo());
    }
}
