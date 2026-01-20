package domain;

import com.ifpe.clinica.domain.FotoPaciente;
import com.ifpe.clinica.domain.Paciente;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FotoPacienteTest extends GenericTest {

    @Test
    public void testPersist() {
        FotoPaciente foto = new FotoPaciente();
        foto.setTitulo("Nova Foto");

        Paciente paciente = em.find(Paciente.class, 1L);
        foto.setPaciente(paciente);

        em.persist(foto);
        em.flush();

        Assertions.assertNotNull(foto.getId());
    }

    @Test
    public void testAtualizar() {
        TypedQuery<FotoPaciente> query = em.createQuery(
                "SELECT f FROM FotoPaciente f WHERE f.titulo = :titulo",
                FotoPaciente.class
        );

        query.setParameter("titulo", "Foto de Perfil");
        FotoPaciente foto = query.getSingleResult();
        Assertions.assertNotNull(foto);

        foto.setTitulo("Foto Atualizada");
        em.flush();

        query.setParameter("titulo", "Foto Atualizada");
        Assertions.assertNotNull(query.getSingleResult());
    }

    @Test
    public void testAtualizarMerge() {
        TypedQuery<FotoPaciente> query = em.createQuery(
                "SELECT f FROM FotoPaciente f WHERE f.titulo = :titulo",
                FotoPaciente.class
        );

        query.setParameter("titulo", "Foto de Perfil Atualizada");
        FotoPaciente foto = query.getSingleResult();
        Assertions.assertNotNull(foto);

        foto.setTitulo("Merge Foto");
        em.clear();

        foto = em.merge(foto);
        em.flush();

        query.setParameter("titulo", "Merge Foto");
        Assertions.assertNotNull(query.getSingleResult());
    }

    @Test
    public void testRemover() {
        TypedQuery<FotoPaciente> query = em.createQuery(
                "SELECT f FROM FotoPaciente f WHERE f.titulo = :titulo",
                FotoPaciente.class
        );

        query.setParameter("titulo", "Foto Antiga");
        FotoPaciente foto = query.getSingleResult();
        Assertions.assertNotNull(foto);

        em.remove(foto);
        em.flush();

        query.setParameter("titulo", "Foto Antiga");
        Assertions.assertEquals(0, query.getResultList().size());
    }

    @Test
    public void testFindById() {
        FotoPaciente f = em.find(FotoPaciente.class, 1L);

        Assertions.assertNotNull(f);
        Assertions.assertEquals("Foto de Perfil", f.getTitulo());
        Assertions.assertEquals("PNG", f.getTipoImagem());
    }

}
