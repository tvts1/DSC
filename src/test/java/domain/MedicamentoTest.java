package domain;

import com.ifpe.clinica.domain.Medicamento;
import com.ifpe.clinica.domain.Prontuario;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MedicamentoTest extends GenericTest{

    @Test
    public void testPersist() {
        Prontuario prontuario = em.find(Prontuario.class, 3L);

        Medicamento medicamento = new Medicamento();
        medicamento.setNome("Paracetamol");
        medicamento.setDose("500mg");
        medicamento.setProntuario(prontuario);

        em.persist(medicamento);
        em.flush();

        assertNotNull(medicamento.getId());
    }
    
    @Test
    public void testAtualizarMedicamento() {
        TypedQuery<Medicamento> query = em.createQuery(
                "SELECT m FROM Medicamento m WHERE m.nome = :nome",
                Medicamento.class);

        query.setParameter("nome", "Ibuprofeno");  
        Medicamento medicamento = query.getSingleResult();
        Assertions.assertNotNull(medicamento);

        medicamento.setNome("Doril");
        em.flush();

        query.setParameter("nome", "Ibuprofeno");
        Assertions.assertEquals(0, query.getResultList().size());

        query.setParameter("nome", "Doril");
        Medicamento atualizado = query.getSingleResult();
        Assertions.assertNotNull(atualizado);
    }
    
    @Test
    public void testAtualizarMedicamentoMerge() {
        TypedQuery<Medicamento> query = em.createQuery(
                "SELECT m FROM Medicamento m WHERE m.nome = :nome",
                Medicamento.class);

        query.setParameter("nome", "Dramin");  
        Medicamento medicamento = query.getSingleResult();
        Assertions.assertNotNull(medicamento);

        medicamento.setNome("Amoxicilina");
        em.clear(); 
        
        medicamento = (Medicamento) em.merge(medicamento);
        em.flush();

        query.setParameter("nome", "Dramin");
        Assertions.assertEquals(0, query.getResultList().size());

        query.setParameter("nome", "Amoxicilina");
        Medicamento atualizado = query.getSingleResult();
        Assertions.assertNotNull(atualizado);
    }
    
    @Test
    public void testRemoverMedicamento() {
        TypedQuery<Medicamento> query = em.createQuery(
                "SELECT m FROM Medicamento m WHERE m.nome = :nome",
                Medicamento.class);
        
        query.setParameter("nome", "Amoxicilina");
        Medicamento medicamento = query.getSingleResult();
        Assertions.assertNotNull(medicamento);
        
        em.remove(medicamento);
        em.flush();
        
        query.setParameter("nome", "Amoxilina");
        Assertions.assertEquals(0, query.getResultList().size());
    }

    @Test
    public void testFindById() {
        Medicamento medicamento = em.find(Medicamento.class, 1L);
        assertNotNull(medicamento);
        assertEquals("Dipirona", medicamento.getNome());
        assertEquals("500mg", medicamento.getDose());
    }
}
