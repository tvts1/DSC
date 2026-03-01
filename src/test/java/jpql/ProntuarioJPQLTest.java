package jpql;

import com.ifpe.clinica.domain.Prontuario;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProntuarioJPQLTest extends GenericTest {

    @Test
    public void buscarProntuariosQueTemPacienteEdescricaoEspecifica() {
        String jpql = "SELECT p FROM Prontuario p WHERE p.paciente IS NOT NULL AND p.descricao LIKE :desc";
        
        TypedQuery<Prontuario> query = em.createQuery(jpql, Prontuario.class);
        query.setParameter("desc", "Paciente com%");

        List<Prontuario> resultados = query.getResultList();

        Assertions.assertEquals(4, resultados.size());
    }

    @Test
    public void carregarProntuarioComMedicamentosDeUmaVez() {
        String jpql = "SELECT DISTINCT p FROM Prontuario p JOIN FETCH p.medicamentos WHERE p.id = :id";
        
        TypedQuery<Prontuario> query = em.createQuery(jpql, Prontuario.class);
        query.setParameter("id", 1L);

        Prontuario resultado = query.getSingleResult();

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(2, resultado.getMedicamentos().size());
    }

    @Test
    public void contarMedicamentosPorProntuario() {
        String jpql = "SELECT p.descricao, COUNT(m) FROM Prontuario p JOIN p.medicamentos m GROUP BY p.descricao";
        
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        List<Object[]> resultados = query.getResultList();

        Assertions.assertEquals(4, resultados.size());
    }

    @Test
    public void ordenarProntuariosPeloIdInverso() {
        String jpql = "SELECT p FROM Prontuario p ORDER BY p.id DESC";
        
        TypedQuery<Prontuario> query = em.createQuery(jpql, Prontuario.class);
        query.setMaxResults(1);

        List<Prontuario> resultados = query.getResultList();

        Assertions.assertEquals(1, resultados.size());
        Assertions.assertEquals("Paciente com dor de dedo", resultados.get(0).getDescricao());
    }
}