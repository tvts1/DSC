package domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import util.DbUnitUtil;

public abstract class GenericTest {
    
    protected EntityManagerFactory emf;
    protected EntityManager em;
    protected EntityTransaction et;

    @BeforeAll
    public void setUpClass() throws Exception{
        emf = Persistence.createEntityManagerFactory("clinicaPU");
        DbUnitUtil.insertData();
    }

    @AfterAll
    public void tearDownClass() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    public void setUp() {
        em = emf.createEntityManager();
        et = em.getTransaction();
        et.begin();
    }

    @AfterEach
    public void tearDown() {
        if (et != null && et.isActive()) {
            et.commit();
        }
        if (em != null) {
            em.close();
        }
    }
    
    protected void beginTransaction() {
        et = em.getTransaction();
        et.begin();
    }
    
    protected void commitTransaction() {
        if (!et.getRollbackOnly()) {
            et.commit();
        }
    }
}
