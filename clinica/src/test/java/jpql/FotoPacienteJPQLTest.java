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
    public void testBuscarPorCategoriaETitulo() {
        String jpql = "SELECT f FROM FotoPaciente f WHERE f.categoria = :cat AND f.titulo = :titulo";

        FotoPaciente foto = em.createQuery(jpql, FotoPaciente.class)
                .setParameter("cat", CategoriaFoto.DOCUMENTO_CONVENIO)
                .setParameter("titulo", "Carteira Bradesco")
                .getSingleResult();

        Assertions.assertNotNull(foto);
        Assertions.assertEquals("Comprovante do plano de saúde", foto.getDescricao());
    }

    @Test
    public void testAtualizarDescricaoPorTitulo() {
        TypedQuery<FotoPaciente> query = em.createQuery(
                "SELECT f FROM FotoPaciente f WHERE f.titulo = :titulo", FotoPaciente.class);
        query.setParameter("titulo", "RG Digitalizado");

        FotoPaciente foto = query.getSingleResult();

        foto.setDescricao("RG Digitalizado e Verificado");
        em.flush();

        Assertions.assertEquals("RG Digitalizado e Verificado", foto.getDescricao());
    }

    @Test
    public void testRemoverArquivoPesado() {
        String jpql = "SELECT f FROM FotoPaciente f WHERE f.tamanho = '102400'";

        FotoPaciente foto = em.createQuery(jpql, FotoPaciente.class).getSingleResult();
        Assertions.assertNotNull(foto);

        em.remove(foto);
        em.flush();

        Long contagem = em.createQuery("SELECT COUNT(f) FROM FotoPaciente f WHERE f.tamanho = '102400'", Long.class)
                .getSingleResult();
        Assertions.assertEquals(0L, contagem);
    }

    @Test
    public void testContarImagensPNG() {
        String jpql = "SELECT COUNT(f) FROM FotoPaciente f WHERE f.tipoImagem = 'PNG'";

        Long total = em.createQuery(jpql, Long.class).getSingleResult();

        Assertions.assertTrue(total >= 2);
    }
}
