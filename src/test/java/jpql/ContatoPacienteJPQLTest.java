
package jpql;

import com.ifpe.clinica.domain.ContatoPaciente;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContatoPacienteJPQLTest extends GenericTest {

    @Test
    public void buscarContatosPorEmailOuObservacao() {
        String jpql = "SELECT c FROM ContatoPaciente c WHERE c.contato.email = :email OR c.observacoes LIKE :obs";
        
        TypedQuery<ContatoPaciente> query = em.createQuery(jpql, ContatoPaciente.class);
        query.setParameter("email", "joao.silva@email.com");
        query.setParameter("obs", "%residencial%");

        List<ContatoPaciente> resultados = query.getResultList();

        Assertions.assertEquals(2, resultados.size());
    }

    @Test
    public void buscarContatoPeloNomeDoPaciente() {
        String jpql = "SELECT c FROM ContatoPaciente c JOIN c.paciente p WHERE p.nome = :nome";
        
        TypedQuery<ContatoPaciente> query = em.createQuery(jpql, ContatoPaciente.class);
        query.setParameter("nome", "Maria Souza");

        ContatoPaciente resultado = query.getSingleResult();

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("maria.souza@email.com", resultado.getContato().getEmail());
    }

    @Test
    public void contarContatosPorPrefixoDeTelefone() {
        String jpql = "SELECT c.contato.telefone, COUNT(c) FROM ContatoPaciente c GROUP BY c.contato.telefone HAVING COUNT(c) >= 1";
        
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        List<Object[]> resultados = query.getResultList();

        Assertions.assertEquals(4, resultados.size());
    }

    @Test
    public void listarContatosOrdenadosPorEmailComLimite() {
        String jpql = "SELECT c FROM ContatoPaciente c ORDER BY c.contato.email ASC";
        
        TypedQuery<ContatoPaciente> query = em.createQuery(jpql, ContatoPaciente.class);
        query.setMaxResults(2);

        List<ContatoPaciente> resultados = query.getResultList();

        Assertions.assertEquals(2, resultados.size());
        Assertions.assertEquals("ana.lima@email.com", resultados.get(0).getContato().getEmail());
    }
}
