package domain;

import com.ifpe.clinica.domain.Medico;
import com.ifpe.clinica.enums.Especialidade;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MedicoTest extends GenericTest{

    @Test
    public void testPersist() {
        Medico m = new Medico();
        m.setNome("Dr. Roberto");
        m.setCrm("CRM789");
        m.setEspecialidade(Especialidade.PEDIATRA);

        em.persist(m);
        em.flush();

        Assertions.assertNotNull(m.getId());
    }
    
    @Test
    public void testAtualizarMedico() {
        TypedQuery<Medico> query = em.createQuery(
                "SELECT m FROM Medico m WHERE m.nome = :nome",
                Medico.class);
        
        query.setParameter("nome", "Dra. Ana");
        Medico medico = query.getSingleResult();
        Assertions.assertNotNull(medico);
        
        medico.setNome("Dra. Grazi");
        medico.setEspecialidade(Especialidade.ORTOPEDISTA);
        em.flush();
        
        query.setParameter("nome", "Dra. Grazi");
        Medico atualizado = query.getSingleResult();
        Assertions.assertNotNull(atualizado);
    }
    
    @Test
    public void testAtualizaMedicoMerge() {
        TypedQuery<Medico> query = em.createQuery(
                "SELECT m FROM Medico m WHERE m.nome = :nome",
                Medico.class);
        
        query.setParameter("nome", "Dr. Elton");
        Medico medico = query.getSingleResult();
        Assertions.assertNotNull(medico);
       
        medico.setNome("Dr. Paulo");
        em.clear();
        
        medico = (Medico) em.merge(medico);
        em.flush();
        
        query.setParameter("nome", "Dr. Elton");
        Assertions.assertEquals(0, query.getResultList().size());
        
        query.setParameter("nome", "Dr. Paulo");
        Medico atualizado = query.getSingleResult();
        Assertions.assertNotNull(atualizado);
    }
    
    @Test
    public void tesRemoverMedico() {
        TypedQuery<Medico> query = em.createQuery(
                "SELECT m FROM Medico m WHERE m.nome = :nome",
                Medico.class);
        
        query.setParameter("nome", "Dr. Johnes");
        Medico medico = query.getSingleResult();
        Assertions.assertNotNull(medico);
        
        em.remove(medico);
        em.flush();
        
        query.setParameter("nome", "Dr. Johnes");
        Assertions.assertEquals(0, query.getResultList().size());
    }

    @Test
    public void testFindById() {
        Medico m = em.find(Medico.class, 4L);
        Assertions.assertEquals("Dr. Carlos", m.getNome());
        Assertions.assertEquals("CRM123", m.getCrm());
    }
}
