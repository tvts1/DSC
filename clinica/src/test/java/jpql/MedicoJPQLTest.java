package jpql;

import com.ifpe.clinica.domain.Medico;
import com.ifpe.clinica.enums.Especialidade;
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
public class MedicoJPQLTest extends GenericTest {

    @Test
public void testBuscarMedicoPorCrmExato() {
    String jpql = "SELECT m FROM Medico m WHERE m.crm = :crm";
    
    Medico medico = em.createQuery(jpql, Medico.class)
            .setParameter("crm", "CRM222")
            .getSingleResult();

    assertNotNull(medico);
    assertEquals("Dr. Henrique", medico.getNome());
}

@Test
public void testListarMedicosPorEspecialidadeEIniciais() {
    String jpql = "SELECT m FROM Medico m WHERE m.especialidade = :esp AND m.nome LIKE :prefixo";
    
    List<Medico> resultados = em.createQuery(jpql, Medico.class)
            .setParameter("esp", Especialidade.GINECOLOGISTA)
            .setParameter("prefixo", "Dra.%")
            .getResultList();

    assertFalse(resultados.isEmpty());
    assertTrue(resultados.stream().anyMatch(m -> m.getNome().contains("Beatriz")));
}

@Test
public void testUpdateEspecialidadePorCrm() {
    String jpqlUpdate = "UPDATE Medico m SET m.especialidade = :novaEsp WHERE m.crm = :crm";
    
    int atualizado = em.createQuery(jpqlUpdate)
            .setParameter("novaEsp", Especialidade.NEUROLOGISTA)
            .setParameter("crm", "CRM773")
            .executeUpdate();

    assertEquals(1, atualizado);
    
    Medico m = em.find(Medico.class, 12L);
    assertEquals(Especialidade.NEUROLOGISTA, m.getEspecialidade());
}

@Test
public void testBuscarMedicosSemCrmDefinido() {
    String jpql = "SELECT m FROM Medico m WHERE m.crm IS NULL OR m.crm = ''";
    
    List<Medico> pendentes = em.createQuery(jpql, Medico.class)
            .getResultList();

    assertTrue(pendentes.isEmpty());
}
}
