package jpql;

import com.ifpe.clinica.domain.Consulta;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class ConsultaJPQLTest extends GenericTest {

    @Test
    public void buscarConsultasSemMedicoVinculado() {
        String jpql = "SELECT c FROM Consulta c WHERE c.medico IS NULL ORDER BY c.data DESC";
        
        TypedQuery<Consulta> query = em.createQuery(jpql, Consulta.class);
        List<Consulta> resultados = query.getResultList();

        Assertions.assertEquals(3, resultados.size());
    }

    @Test
    public void buscarConsultasNoPeriodoDeDatas() {
        String jpql = "SELECT c FROM Consulta c WHERE c.data BETWEEN :inicio AND :fim ORDER BY c.data ASC";

        TypedQuery<Consulta> query = em.createQuery(jpql, Consulta.class);
        query.setParameter("inicio", java.time.LocalDateTime.parse("2024-10-01T00:00:00"));
        query.setParameter("fim", java.time.LocalDateTime.parse("2024-10-31T23:59:59"));

        List<Consulta> resultados = query.getResultList();

        Assertions.assertEquals(2, resultados.size());
    }

    @Test
    public void contarConsultasRepetidasNoMesmoDia() {
        String jpql = "SELECT c.data, COUNT(c) FROM Consulta c GROUP BY c.data HAVING COUNT(c) > 1";

        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        List<Object[]> resultados = query.getResultList();

        Assertions.assertEquals(0, resultados.size());
    }

    @Test
    public void buscarConsultasQueExigemExames() {
        String jpql = "SELECT c FROM Consulta c WHERE c.paciente.id = :pacienteId AND c.exames IS NOT EMPTY";

        TypedQuery<Consulta> query = em.createQuery(jpql, Consulta.class);
        query.setParameter("pacienteId", 1L);

        List<Consulta> resultados = query.getResultList();

        Assertions.assertEquals(1, resultados.size());
    }
}
