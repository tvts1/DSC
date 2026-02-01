
package jpql;

import com.ifpe.clinica.domain.Medico;
import com.ifpe.clinica.enums.Especialidade;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MedicoJPQLTest extends GenericTest {

    @Test
    public void buscarMedicosPorEspecialidadeOuCrm() {
        String jpql = "SELECT m FROM Medico m WHERE m.especialidade = :esp OR m.crm = :crm";
        
        TypedQuery<Medico> query = em.createQuery(jpql, Medico.class);
        query.setParameter("esp", Especialidade.CLINICO_GERAL);
        query.setParameter("crm", "CRM123");

        List<Medico> resultados = query.getResultList();

        Assertions.assertEquals(3, resultados.size());
    }

    @Test
    public void contarMedicosPorEspecialidade() {
        String jpql = "SELECT m.especialidade, COUNT(m) FROM Medico m GROUP BY m.especialidade HAVING COUNT(m) > 1";
        
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        List<Object[]> resultados = query.getResultList();

        Assertions.assertEquals(1, resultados.size());
        Assertions.assertEquals(Especialidade.CLINICO_GERAL, resultados.get(0)[0]);
        Assertions.assertEquals(2L, resultados.get(0)[1]);
    }

    @Test
    public void listarMedicosPeloNomeCrescente() {
        String jpql = "SELECT m FROM Medico m ORDER BY m.nome ASC";
        
        TypedQuery<Medico> query = em.createQuery(jpql, Medico.class);
        query.setMaxResults(2);

        List<Medico> resultados = query.getResultList();

        Assertions.assertEquals(2, resultados.size());
        Assertions.assertEquals("Dr. Carlos", resultados.get(0).getNome());
        Assertions.assertEquals("Dr. Elton", resultados.get(1).getNome());
    }

    @Test
    public void buscarMedicosComCrmEspecificoUsandoLike() {
        String jpql = "SELECT m FROM Medico m WHERE m.crm LIKE 'CRM1%'";
        
        TypedQuery<Medico> query = em.createQuery(jpql, Medico.class);
        List<Medico> resultados = query.getResultList();

        Assertions.assertEquals(2, resultados.size());
    }
}
