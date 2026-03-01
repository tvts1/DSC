package jpql;

import com.ifpe.clinica.domain.Paciente;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PacienteJPQLTest extends GenericTest {

    @Test
    public void buscarPacientesDePernambucoPorNomeOuCpf() {
        String jpql = "SELECT p FROM Paciente p JOIN p.endereco e " +
                      "WHERE e.estado = :estado AND (p.nome LIKE :nome OR p.cpf = :cpf)";
        
        TypedQuery<Paciente> query = em.createQuery(jpql, Paciente.class);
        query.setParameter("estado", "PE");
        query.setParameter("nome", "João%");
        query.setParameter("cpf", "39053344705"); 

        List<Paciente> resultados = query.getResultList();

        Assertions.assertEquals(2, resultados.size(), "Deveria encontrar João e Maria");
    }

    @Test
    public void carregarPacienteEenderecoDeUmaVez() {
        String jpql = "SELECT p FROM Paciente p JOIN FETCH p.endereco WHERE p.id = :id";
        
        TypedQuery<Paciente> query = em.createQuery(jpql, Paciente.class);
        query.setParameter("id", 1L);

        Paciente resultado = query.getSingleResult();

        Assertions.assertNotNull(resultado);
        Assertions.assertNotNull(resultado.getEndereco());
        Assertions.assertEquals("Rua A", resultado.getEndereco().getRua());
    }

    @Test
    public void contarPacientesPorCidade() {
        String jpql = "SELECT e.cidade, COUNT(p) FROM Paciente p JOIN p.endereco e GROUP BY e.cidade HAVING COUNT(p) >= 1";
        
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        List<Object[]> resultados = query.getResultList();

        Assertions.assertEquals(3, resultados.size());
    }

    @Test
    public void buscarPacientesSemConsultas() {
        String jpql = "SELECT p FROM Paciente p WHERE p.consultas IS EMPTY ORDER BY p.nome ASC";
        
        TypedQuery<Paciente> query = em.createQuery(jpql, Paciente.class);
        query.setMaxResults(2);

        List<Paciente> resultados = query.getResultList();

        Assertions.assertEquals(0, resultados.size());
    }
}