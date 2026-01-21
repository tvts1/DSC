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

    @Test
    public void testBuscarPorCelular() {
        String jpql = "SELECT c FROM ContatoPaciente c WHERE c.contato.celular = :celular";
        
        ContatoPaciente resultado = em.createQuery(jpql, ContatoPaciente.class)
                .setParameter("celular", "8199998888")
                .getSingleResult();

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("joao.silva@email.com", resultado.getContato().getEmail());
    }

    @Test
    public void testBuscarPorObservacao() {
        String jpql = "SELECT c FROM ContatoPaciente c WHERE c.contato.observacoes LIKE :obs";
        
        List<ContatoPaciente> lista = em.createQuery(jpql, ContatoPaciente.class)
                .setParameter("obs", "%atualizado%")
                .getResultList();

        Assertions.assertFalse(lista.isEmpty());
        Assertions.assertEquals("maria.souza@email.com", lista.get(0).getContato().getEmail());
    }

    @Test
    public void testBuscarContatoPorNomePaciente() {
        String jpql = "SELECT c FROM ContatoPaciente c WHERE c.paciente.nome = :nome";
        
        ContatoPaciente resultado = em.createQuery(jpql, ContatoPaciente.class)
                .setParameter("nome", "Maria Souza")
                .getSingleResult();

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("8197776666", resultado.getContato().getCelular());
    }

    @Test
    public void testContarContatosComEmergencia() {
        String jpql = "SELECT COUNT(c) FROM ContatoPaciente c WHERE c.contato.telefoneEmergencia IS NOT NULL";
        
        Long total = em.createQuery(jpql, Long.class)
                .getSingleResult();

        Assertions.assertTrue(total >= 4);
    }

    @Test
    public void testProjecaoContato() {
        String jpql = "SELECT c.contato.email, c.contato.celular FROM ContatoPaciente c WHERE c.id = :id";
        
        Object[] dados = em.createQuery(jpql, Object[].class)
                .setParameter("id", 1L)
                .getSingleResult();

        Assertions.assertEquals("joao.silva@email.com", dados[0]);
        Assertions.assertNotNull(dados[1]);
    }
}
