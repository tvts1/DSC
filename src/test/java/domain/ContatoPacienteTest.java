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
        contato.setTelefone("8133334444");
        contato.setCelular("81999998888"); 
        contato.setEmail("teste_novo@email.com");

        contatoPaciente.setContato(contato);
        contatoPaciente.setObservacoes("Paciente de teste");

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

        contatoPaciente.getContato().setEmail("novo_email@email.com");
        em.flush();

        query.setParameter("email", "novo_email@email.com");
        Assertions.assertNotNull(query.getSingleResult());
    }

    @Test
    public void testAtualizarMerge() {
        TypedQuery<ContatoPaciente> query = em.createQuery(
                "SELECT c FROM ContatoPaciente c WHERE c.id = :id",
                ContatoPaciente.class);

        query.setParameter("id", 1L);
        ContatoPaciente contatoPaciente = query.getSingleResult();

        contatoPaciente.getContato().setTelefone("8133339999");
        em.clear();

        contatoPaciente = em.merge(contatoPaciente);
        em.flush();

        query.setParameter("id", 1L);
        Assertions.assertEquals(
                "8133339999",
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
}