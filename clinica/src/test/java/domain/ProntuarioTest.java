package domain;

import com.ifpe.clinica.domain.Medicamento;
import com.ifpe.clinica.domain.Paciente;
import com.ifpe.clinica.domain.Prontuario;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.eclipse.persistence.config.QueryType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProntuarioTest extends GenericTest {

    @Test
    public void testPersist() {
        Paciente paciente = em.find(Paciente.class, 3L);
        Medicamento med1 = em.find(Medicamento.class, 1L);
        Medicamento med2 = em.find(Medicamento.class, 2L);

        Prontuario prontuario = new Prontuario();
        prontuario.setDescricao("Prontuario teste");
        prontuario.setPaciente(paciente);
        prontuario.setMedicamentos(List.of(med1, med2));

        em.persist(prontuario);
        em.flush();

        assertNotNull(prontuario.getId());
    }
    
    @Test
    public void testAtualizarProntuario() {
        TypedQuery<Prontuario> query = em.createQuery(
                "SELECT p FROM Prontuario p WHERE p.descricao = :descricao",
                Prontuario.class
        );
        
        query.setParameter("descricao", "Paciente com alergia");
        Prontuario prontuario = query.getSingleResult();
        Assertions.assertNotNull(prontuario);
        
        prontuario.setDescricao("Paciente com dor de ouvido");
        em.flush();
        
        query.setParameter("descricao", "Paciente com alergia");
        Assertions.assertEquals(0, query.getResultList().size());
           
        query.setParameter("descricao", "Paciente com dor de ouvido");
        Prontuario atualizado = query.getSingleResult();
        Assertions.assertNotNull(atualizado);
    }
    
    @Test
    public void testAtualizaProntuarioMerge() {
        TypedQuery<Prontuario> query = em.createQuery(
                "SELECT p FROM Prontuario p WHERE p.descricao = :descricao",
                Prontuario.class
        );
        
        query.setParameter("descricao", "Paciente com dor de cabeca");
        Prontuario prontuario = query.getSingleResult();
        Assertions.assertNotNull(prontuario);
        
        prontuario.setDescricao("Paciente com dor de dente");
        em.clear();
        
        prontuario = (Prontuario) em.merge(prontuario);
        em.flush();
        
        query.setParameter("descricao", "Paciente com dor de cabeca");
        Assertions.assertEquals(0, query.getResultList().size());
        
        query.setParameter("descricao", "Paciente com dor de dente");
        Prontuario atualizado = query.getSingleResult();
        Assertions.assertNotNull(atualizado);
    }
    
    @Test
    public void testRemoverProntuario() {
        TypedQuery<Prontuario> query = em.createQuery(
                "SELECT p FROM Prontuario p WHERE p.descricao = :descricao",
                Prontuario.class
        );
        
        query.setParameter("descricao", "Paciente com dor de dedo");
        Prontuario prontuario = query.getSingleResult();
        Assertions.assertNotNull(prontuario);
        
        em.remove(prontuario);
        em.flush();
        
        query.setParameter("descricao", "Paciente com dor de dedo");
        Assertions.assertEquals(0, query.getResultList().size());
    }

    @Test
    public void testFindById() {
        Prontuario prontuario = em.find(Prontuario.class, 1L);
        assertNotNull(prontuario);
        assertEquals("Paciente com histórico positivo", prontuario.getDescricao());
    }
}
