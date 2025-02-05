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

    /**
     * Salva um novo empréstimo no banco de dados.
     * Agora inclui a data de empréstimo e a data de devolução prevista.
     * @param emprestimo Objeto do empréstimo
     * @return Exceção se houver erro, ou null se salvo com sucesso
     */
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

    /**
     * Conta quantos livros o usuário pegou emprestado e ainda não devolveu.
     * @param usuarioId ID do usuário
     * @return Número de empréstimos ativos
     */
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

    /**
     * Lista todos os empréstimos ativos de um usuário.
     * @param usuarioId ID do usuário
     * @return Lista de empréstimos ativos
     */
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



    /**
     * Consulta um empréstimo pelo ID.
     * @param emprestimoId ID do empréstimo
     * @return Objeto EmprestimoModel ou null se não encontrado
     */
    public EmprestimoModel consultarPorId(int emprestimoId) {
        try {
            return entityManager.find(EmprestimoModel.class, emprestimoId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Atualiza um empréstimo no banco de dados.
     * Isso é usado para registrar a devolução do livro.
     * @param emprestimo Objeto do empréstimo atualizado
     * @return Exceção se houver erro, ou null se salvo com sucesso
     */
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
    public List<EmprestimoModel> listarEmprestimosPorNomeUsuario(String nomeUsuario) {
        try {
            return entityManager.createQuery(
                            "SELECT e FROM EmprestimoModel e WHERE e.usuario.nome LIKE :nomeUsuario",
                            EmprestimoModel.class
                    )
                    .setParameter("nomeUsuario", "%" + nomeUsuario + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
