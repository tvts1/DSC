package domain;

import com.ifpe.clinica.domain.Consulta;
import com.ifpe.clinica.domain.Exame;
import com.ifpe.clinica.domain.Medico;
import com.ifpe.clinica.domain.Paciente;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConsultaTest extends GenericTest {

    @Test
    public void testPersist() {
        Paciente paciente = em.find(Paciente.class, 1L);
        Medico medico = em.find(Medico.class, 1L);
        Exame exame1 = em.find(Exame.class, 1L);
        Exame exame2 = em.find(Exame.class, 2L);

        Consulta consulta = new Consulta();
        LocalDateTime dataConsulta = LocalDateTime.parse("2024-10-11T15:30:00");
        consulta.setData(dataConsulta);
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setExames(List.of(exame1, exame2));

        em.persist(consulta);
        em.flush();

        assertNotNull(consulta.getId());
    }

    @Test
    public void testAtualizarConsulta() {

        TypedQuery<Consulta> query = em.createQuery(
                "SELECT c FROM Consulta c WHERE c.data = :data",
                Consulta.class
        );
        LocalDateTime dataOriginal = LocalDateTime.parse("2024-10-12T09:15:00");
        query.setParameter("data", dataOriginal);

        Consulta consulta = query.getSingleResult();
        Assertions.assertNotNull(consulta);

        LocalDateTime dataAtualizada = LocalDateTime.parse("2024-10-15T20:35:00");
        consulta.setData(dataAtualizada);
        em.flush();

        query.setParameter("data", dataOriginal);
        Assertions.assertEquals(0, query.getResultList().size());

        query.setParameter("data", dataAtualizada);
        Consulta atualizado = query.getSingleResult();
        Assertions.assertNotNull(atualizado);
    }


    @Test
    @SuppressWarnings("UnusedAssignment")
    public void testAtualizarConsultaMerge()  {
        TypedQuery<Consulta> query = em.createQuery(
                "SELECT c FROM Consulta c WHERE c.data = :data",
                Consulta.class
        );
        LocalDateTime dataOriginal = LocalDateTime.parse("2025-12-18T17:30:00");
        query.setParameter("data", dataOriginal);
        Consulta consulta = query.getSingleResult();
        Assertions.assertNotNull(consulta);

        
        LocalDateTime dataAtualizada = LocalDateTime.parse("2024-11-25T10:00:00");
        consulta.setData(dataAtualizada);
        em.clear();
        
        consulta = (Consulta) em.merge(consulta);
        em.flush();

        query.setParameter("data", dataOriginal);
        Assertions.assertEquals(0, query.getResultList().size());

        query.setParameter("data", dataAtualizada);
        Consulta atualizado = query.getSingleResult();
        Assertions.assertNotNull(atualizado);
    }
    
    @Test
    public void testRemoverConsulta() {
        TypedQuery<Consulta> query = em.createQuery(
                "SELECT c FROM Consulta c WHERE c.data = :data",
                Consulta.class);
        
        LocalDateTime data = LocalDateTime.parse("2025-05-14T18:30:00");
        
        query.setParameter("data", data);
        Consulta consulta = query.getSingleResult();
        Assertions.assertNotNull(consulta);
        
        em.remove(consulta);
        em.flush();
        
        query.setParameter("data", data);
        Assertions.assertEquals(0, query.getResultList().size());
    }
    
    @Test
    public void testFindById() {
        Consulta consulta = em.find(Consulta.class, 1L);
        LocalDateTime expectedDate = LocalDateTime.parse("2024-10-10T14:30:00");
        assertEquals(expectedDate, consulta.getData());
    }
}
