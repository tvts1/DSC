package domain;

import com.ifpe.clinica.domain.Contato;
import com.ifpe.clinica.domain.ContatoPaciente;
import com.ifpe.clinica.domain.Paciente;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContatoPacienteTest extends GenericTest {

    @Test
    public void testPersist() {
        ContatoPaciente contatoPaciente = new ContatoPaciente();

        Contato contato = new Contato();
        contato.setTelefone("11111111");
        contato.setEmail("teste@email.com");

        contatoPaciente.setContato(contato);

        Paciente paciente = em.find(Paciente.class, 1L);
        contatoPaciente.setPaciente(paciente);

        em.persist(contatoPaciente);
        em.flush();

        Assertions.assertNotNull(contatoPaciente.getId());
    }

    @Test
    public void testAtualizar() {
        TypedQuery<ContatoPaciente> query = em.createQuery(
                "SELECT c FROM ContatoPaciente c WHERE c.contato.email = :email",
                ContatoPaciente.class);

        query.setParameter("email", "joao.silva@email.com");
        ContatoPaciente contatoPaciente = query.getSingleResult();

        contatoPaciente.getContato().setEmail("novo@email.com");
        em.flush();

        query.setParameter("email", "novo@email.com");
        Assertions.assertNotNull(query.getSingleResult());
    }

    @Test
    public void testAtualizarMerge() {
        TypedQuery<ContatoPaciente> query = em.createQuery(
                "SELECT c FROM ContatoPaciente c WHERE c.id = :id",
                ContatoPaciente.class);

        query.setParameter("id", 1L);
        ContatoPaciente contatoPaciente = query.getSingleResult();

        contatoPaciente.getContato().setTelefone("99999999");
        em.clear();

        contatoPaciente = em.merge(contatoPaciente);
        em.flush();

        query.setParameter("id", 1L);
        Assertions.assertEquals(
                "99999999",
                query.getSingleResult().getContato().getTelefone()
        );
    }

    @Test
    public void testRemover() {
        TypedQuery<ContatoPaciente> query = em.createQuery(
                "SELECT c FROM ContatoPaciente c WHERE c.id = :id",
                ContatoPaciente.class);

        query.setParameter("id", 4L);
        ContatoPaciente contatoPaciente = query.getSingleResult();

        em.remove(contatoPaciente);
        em.flush();
        em.clear();

        query.setParameter("id", 4L);
        Assertions.assertEquals(0, query.getResultList().size());
    }

    @Test
    public void testFindById() {
        ContatoPaciente contatoPaciente = em.find(ContatoPaciente.class, 1L);
        Assertions.assertNotNull(contatoPaciente);
        Assertions.assertNotNull(contatoPaciente.getContato());
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
