package br.com.repository;

import br.com.model.EmprestimoModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

public class EmprestimoRepository {

    private static EmprestimoRepository instance;
    protected EntityManager entityManager;

    public EmprestimoRepository() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public static EmprestimoRepository getInstance() {
        if (instance == null) {
            instance = new EmprestimoRepository();
        }
        return instance;
    }

    public Exception salvar(EmprestimoModel emprestimo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(emprestimo);
            entityManager.getTransaction().commit();
            return null;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return e;
        }
    }

    public int contarEmprestimosAtivos(int usuarioId) {
        try {
            return ((Number) entityManager.createQuery(
                            "SELECT COUNT(e) FROM EmprestimoModel e WHERE e.usuario.idUsuario = :usuarioId AND e.dataDevolucao IS NULL"
                    )
                    .setParameter("usuarioId", usuarioId)
                    .getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<EmprestimoModel> listarEmprestimosAtivos(int usuarioId) {
        try {
            return entityManager.createQuery(
                            "SELECT e FROM EmprestimoModel e WHERE e.usuario.idUsuario = :usuarioId AND e.dataDevolucao IS NULL",
                            EmprestimoModel.class
                    )
                    .setParameter("usuarioId", usuarioId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public EmprestimoModel consultarPorId(int emprestimoId) {
        try {
            return entityManager.find(EmprestimoModel.class, emprestimoId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Exception atualizar(EmprestimoModel emprestimo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(emprestimo);
            entityManager.getTransaction().commit();
            return null;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return e;
        }
    }
    public List<EmprestimoModel> listarEmprestimosAtivosPorNomeUsuario(String nomeUsuario) {
        try {
            return entityManager.createQuery(
                            "SELECT e FROM EmprestimoModel e " +
                                    "WHERE e.usuario.nome LIKE :nomeUsuario " +
                                    "AND e.dataDevolucao IS NULL", EmprestimoModel.class)
                    .setParameter("nomeUsuario", "%" + nomeUsuario + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
