package jpql;

import com.ifpe.clinica.domain.Paciente;
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
public class PacienteJPQLTest extends GenericTest {

    @Test
    public void testBuscarPacientePorCpfExato() {
        String jpql = "SELECT p FROM Paciente p WHERE p.cpf = :cpf";

        TypedQuery<Paciente> query = em.createQuery(jpql, Paciente.class);
        query.setParameter("cpf", "11122233344");

        Paciente resultado = query.getSingleResult();

        assertNotNull(resultado);
        assertEquals("Fernando Costa", resultado.getNome());
    }

    @Test
    public void testListarPacientesDeEstadoEspecifico() {
        String jpql = "SELECT p FROM Paciente p WHERE p.endereco.estado = :uf";

        List<Paciente> pacientes = em.createQuery(jpql, Paciente.class)
                .setParameter("uf", "SP")
                .getResultList();

        assertFalse(pacientes.isEmpty());
        assertTrue(pacientes.stream().anyMatch(p -> p.getNome().equals("Ricardo Oliveira")));
    }

    @Test
    public void testBuscarNomesPorParteDoNome() {
        String jpql = "SELECT p.nome FROM Paciente p WHERE p.nome LIKE :parteNome";

        List<String> nomes = em.createQuery(jpql, String.class)
                .setParameter("parteNome", "%Juliana%")
                .getResultList();

        assertFalse(nomes.isEmpty());
        assertTrue(nomes.contains("Juliana Paes"));
    }

    @Test
    public void testBuscarPacientesPorCidadeERua() {
        String jpql = "SELECT p FROM Paciente p "
                + "WHERE p.endereco.cidade = :cidade "
                + "AND p.endereco.rua LIKE :rua";

        TypedQuery<Paciente> query = em.createQuery(jpql, Paciente.class);
        query.setParameter("cidade", "Fortaleza");
        query.setParameter("rua", "%Mar%");

        List<Paciente> resultados = query.getResultList();

        assertFalse(resultados.isEmpty());
        assertEquals("Juliana Paes", resultados.get(0).getNome());
    }
}
