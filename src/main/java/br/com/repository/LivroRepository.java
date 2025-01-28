package br.com.repository;

import br.com.model.LivroModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

public class LivroRepository {

    private static LivroRepository instance;
    protected EntityManager entityManager;

    public LivroRepository() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public static LivroRepository getInstance() {
        if (instance == null) {
            instance = new LivroRepository();
        }
        return instance;
    }

    public Exception salvar(LivroModel livro) throws SQLException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(livro);
            entityManager.getTransaction().commit();
            return null;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return e;
        }
    }

    public LivroModel consultarPorId(int idLivro) {
        try {
            return entityManager.find(LivroModel.class, idLivro);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Exception remover(int idLivro) {
        try {
            entityManager.getTransaction().begin();
            LivroModel livro = entityManager.find(LivroModel.class, idLivro);
            if (livro != null) {
                entityManager.remove(livro);
                entityManager.getTransaction().commit();
                return null;
            } else {
                entityManager.getTransaction().rollback();
                return new Exception("Livro não encontrado.");
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return e;
        }
    }

    public Exception atualizar(LivroModel livro) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(livro);
            entityManager.getTransaction().commit();
            return null;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return e;
        }
    }

    public List<LivroModel> listarTodos() {
        try {
            return entityManager.createQuery("FROM LivroModel", LivroModel.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}