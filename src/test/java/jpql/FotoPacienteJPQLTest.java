package jpql;

import com.ifpe.clinica.domain.FotoPaciente;
import com.ifpe.clinica.enums.CategoriaFoto;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FotoPacienteJPQLTest extends GenericTest {

    @Test
    public void buscarFotosPorCategoriaOuTipoEspecifico() {
        String jpql = "SELECT f FROM FotoPaciente f WHERE f.categoria = :categoria OR f.tipoImagem = :tipo";
        
        TypedQuery<FotoPaciente> query = em.createQuery(jpql, FotoPaciente.class);
        query.setParameter("categoria", CategoriaFoto.FOTO_PERFIL);
        query.setParameter("tipo", "JPG");

        List<FotoPaciente> resultados = query.getResultList();

        Assertions.assertEquals(4, resultados.size());
    }

    @Test
    public void buscarFotosAcimaDeUmTamanhoPeloNomeDoPaciente() {
        String jpql = "SELECT f FROM FotoPaciente f JOIN f.paciente p " +
                      "WHERE p.nome = :nome AND f.tamanho > :tamanhoMin";
        
        TypedQuery<FotoPaciente> query = em.createQuery(jpql, FotoPaciente.class);
        query.setParameter("nome", "João Silva");
        query.setParameter("tamanhoMin", 25600L);

        List<FotoPaciente> resultados = query.getResultList();

        Assertions.assertEquals(0, resultados.size());
    }

    @Test
    public void contarFotosPorTipoDeArquivo() {
        String jpql = "SELECT f.tipoImagem, COUNT(f) FROM FotoPaciente f GROUP BY f.tipoImagem HAVING COUNT(f) > 1";
        
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        List<Object[]> resultados = query.getResultList();

        Assertions.assertEquals(2, resultados.size());
    }

    @Test
    public void listarFotosMaisRecentesComLimite() {
        String jpql = "SELECT f FROM FotoPaciente f ORDER BY f.dataUpload DESC";
        
        TypedQuery<FotoPaciente> query = em.createQuery(jpql, FotoPaciente.class);
        query.setMaxResults(1); 

        FotoPaciente resultado = query.getSingleResult();

        Assertions.assertEquals("Foto Antiga", resultado.getTitulo());
    }
}
