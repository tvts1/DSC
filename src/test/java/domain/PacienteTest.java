package domain;

import com.ifpe.clinica.domain.Endereco;
import com.ifpe.clinica.domain.Paciente;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PacienteTest extends GenericTest {

    @Test
    public void testPersist() {
        Endereco end = new Endereco();
        end.setRua("Rua da Aurora");
        end.setCidade("Olinda");
        end.setEstado("PE");

        Paciente p = new Paciente();
        p.setNome("Carlos Silva");
        p.setCpf("05221971089");
        p.setEndereco(end);

        em.persist(p);
        em.flush();

        Assertions.assertNotNull(p.getId());
    }

    @Test
    public void atualizarPaciente() {
        TypedQuery<Paciente> query = em.createQuery(
                "SELECT p FROM Paciente p WHERE p.nome = :nome",
                Paciente.class);

        query.setParameter("nome", "Maria Souza");
        Paciente paciente = query.getSingleResult();
        
        paciente.setNome("Wanessa Wolf");
        em.flush();

        query.setParameter("nome", "Wanessa Wolf");
        Assertions.assertNotNull(query.getSingleResult());
    }

    @Test
    @SuppressWarnings("UnusedAssignment")
    public void atualizarPacienteMerge() {
        TypedQuery<Paciente> query = em.createQuery(
                "SELECT p FROM Paciente p WHERE p.nome = :nome",
                Paciente.class);

        query.setParameter("nome", "Cleiton Rasta");
        Paciente paciente = query.getSingleResult();
        
        paciente.setNome("Joao Pedro");
        em.clear(); 

        paciente = (Paciente) em.merge(paciente);
        em.flush();

        query.setParameter("nome", "Joao Pedro");
        Assertions.assertNotNull(query.getSingleResult());
    }

    @Test
    public void removePaciente() {
        TypedQuery<Paciente> query = em.createQuery(
                "SELECT p FROM Paciente p WHERE p.nome = :nome",
                Paciente.class);

        query.setParameter("nome", "Sandra Maria");
        Paciente paciente = query.getSingleResult();
        Assertions.assertNotNull(paciente);

        em.createQuery("DELETE FROM FotoPaciente f WHERE f.paciente.id = :id")
          .setParameter("id", paciente.getId())
          .executeUpdate();
          
        em.createQuery("DELETE FROM ContatoPaciente c WHERE c.paciente.id = :id")
          .setParameter("id", paciente.getId())
          .executeUpdate();

        em.remove(paciente);
        em.flush();
        em.clear(); 

        query.setParameter("nome", "Sandra Maria");
        Assertions.assertEquals(0, query.getResultList().size());
    }

    @Test
    public void testFindById() {
        Paciente p = em.find(Paciente.class, 1L);
        Assertions.assertEquals("João Silva", p.getNome());
        Assertions.assertEquals("11144477735", p.getCpf());
    }
}