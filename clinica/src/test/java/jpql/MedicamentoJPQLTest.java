package jpql;

import com.ifpe.clinica.domain.Medicamento;
import domain.GenericTest;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class MedicamentoJPQLTest extends GenericTest {

    @Test
    public void testBuscarMedicamentoPorDose() {
        String jpql = "SELECT m FROM Medicamento m WHERE m.dose = '20mg'";

        Medicamento resultado = em.createQuery(jpql, Medicamento.class).getSingleResult();

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Omeprazol", resultado.getNome());
    }

    @Test
    public void testAtualizarDoseMedicamento() {
        TypedQuery<Medicamento> query = em.createQuery(
                "SELECT m FROM Medicamento m WHERE m.nome = 'Alprazolam'", Medicamento.class);
        Medicamento medicamento = query.getSingleResult();

        medicamento.setDose("1000mg");
        em.flush();

        Assertions.assertEquals("1000mg", medicamento.getDose());
    }

    @Test
    public void testBuscarPorPrefixoDoNome() {
        String jpql = "SELECT m.nome FROM Medicamento m WHERE m.nome LIKE 'Ser%'";

        String nome = em.createQuery(jpql, String.class).getSingleResult();

        Assertions.assertEquals("Sertralina", nome);
    }

    @Test
    public void testBuscarMaiorDoseMedicamento() {
        String jpql = "SELECT MAX(m.dose) FROM Medicamento m";

        String maiorDose = em.createQuery(jpql, String.class).getSingleResult();

        Assertions.assertNotNull(maiorDose);
        Assertions.assertEquals("750mg", maiorDose);
    }
}
