package br.com.repository;

import br.com.model.UsuarioModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

public class UsuarioRepository {
    private static UsuarioRepository instance;
    protected EntityManager entityManager;

    public UsuarioRepository() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public static UsuarioRepository getInstance() {
        if (instance == null) {
            instance = new UsuarioRepository();
        }
        return instance;
    }

    public Exception salvar(UsuarioModel usuario) throws SQLException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
            return null;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return e;
        }
    }

    public UsuarioModel consultarPorId(int idUsuario) {
        try {
            return entityManager.find(UsuarioModel.class, idUsuario);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Exception remover(int idUsuario) {
        try {
            entityManager.getTransaction().begin();
            UsuarioModel usuario = entityManager.find(UsuarioModel.class, idUsuario);
            if (usuario != null) {
                entityManager.remove(usuario);
                entityManager.getTransaction().commit();
                return null;
            } else {
                entityManager.getTransaction().rollback();
                return new Exception("Usuário não encontrado.");
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return e;
        }
    }

    public Exception atualizar(UsuarioModel usuario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();
            return null;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return e;
        }
    }

    public List<UsuarioModel> listarTodos() {
        try {
            return entityManager.createQuery("FROM UsuarioModel", UsuarioModel.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}