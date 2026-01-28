package jpql;

import com.ifpe.clinica.domain.Prontuario;
import domain.GenericTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProntuarioJPQLTest extends GenericTest {

    @Test
    public void testBuscarProntuarioPorNomePaciente() {
        String jpql = "SELECT p FROM Prontuario p WHERE p.paciente.nome = :nome";

        Prontuario resultado = em.createQuery(jpql, Prontuario.class)
                .setParameter("nome", "Ricardo Oliveira")
                .getSingleResult();

        assertNotNull(resultado);
        assertTrue(resultado.getDescricao().contains("pós-operatório"));
    }

    @Test
    public void testBuscarProntuariosPorDescricaoLike() {
        String jpql = "SELECT p FROM Prontuario p WHERE p.descricao LIKE :termo";

        List<Prontuario> resultados = em.createQuery(jpql, Prontuario.class)
                .setParameter("termo", "%ansiedade%")
                .getResultList();

        assertFalse(resultados.isEmpty());
        assertEquals(1, resultados.size());
    }

    @Test
    public void testListarProntuariosQuePossuemMedicamentos() {
        String jpql = "SELECT p FROM Prontuario p WHERE p.medicamentos IS NOT EMPTY";

        List<Prontuario> resultados = em.createQuery(jpql, Prontuario.class)
                .getResultList();

        assertFalse(resultados.isEmpty());
    }

    @Test
    public void testBuscarDescricaoPorCpfDoPaciente() {
        String jpql = "SELECT p.descricao FROM Prontuario p WHERE p.paciente.cpf = :cpf";

        String descricao = em.createQuery(jpql, String.class)
                .setParameter("cpf", "11122233344")
                .getSingleResult();

        assertNotNull(descricao);
        assertEquals("Paciente apresenta sintomas de gripe forte", descricao);
    }
}
