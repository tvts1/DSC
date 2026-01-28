package jpql;

import com.ifpe.clinica.domain.ContatoPaciente;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContatoPacienteJPQLTest extends GenericTest {

    @Test
    public void testBuscarContatoPorCpfDoPaciente() {
        String jpql = "SELECT cp FROM ContatoPaciente cp "
                + "JOIN cp.paciente p "
                + "WHERE p.cpf = :cpf";

        TypedQuery<ContatoPaciente> query = em.createQuery(jpql, ContatoPaciente.class);
        query.setParameter("cpf", "55544433322");

        ContatoPaciente resultado = query.getSingleResult();

        assertNotNull(resultado);
        assertEquals("ricardo.oliveira@email.com", resultado.getContato().getEmail());
    }

    @Test
    public void testBuscarContatosComerciaisSP() {
        String jpql = "SELECT cp FROM ContatoPaciente cp "
                + "WHERE cp.observacoes LIKE :obs "
                + "AND cp.contato.celular LIKE '11%'";

        List<ContatoPaciente> resultados = em.createQuery(jpql, ContatoPaciente.class)
                .setParameter("obs", "%comercial%")
                .getResultList();

        assertFalse(resultados.isEmpty());
    }

    @Test
    public void testUpdateObservacaoPorCidade() {
        String jpql = "UPDATE ContatoPaciente cp SET cp.observacoes = :texto "
                + "WHERE cp.paciente.endereco.cidade = :cidade";

        int atualizados = em.createQuery(jpql)
                .setParameter("texto", "Prioridade Regional")
                .setParameter("cidade", "Recife")
                .executeUpdate();

        assertTrue(atualizados >= 1);
    }

    @Test
    public void testBuscarContatoPorCelular() {
        String jpql = "SELECT cp FROM ContatoPaciente cp WHERE cp.contato.celular = :celular";

        TypedQuery<ContatoPaciente> query = em.createQuery(jpql, ContatoPaciente.class);
        query.setParameter("celular", "8199998888"); // Celular do João Silva (ID 1)

        ContatoPaciente resultado = query.getSingleResult();

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getPaciente().getNome());
    }
}
