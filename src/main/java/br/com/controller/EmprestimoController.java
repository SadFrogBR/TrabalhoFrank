package br.com.controller;

import br.com.model.EmprestimoModel;
import br.com.model.LivroModel;
import br.com.repository.EmprestimoRepository;
import br.com.repository.LivroRepository;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EmprestimoController {
    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;

    public EmprestimoController() {
        this.emprestimoRepository = new EmprestimoRepository();
        this.livroRepository = new LivroRepository();
    }

    /**
     * Conta a quantidade de empréstimos ativos de um usuário.
     * @param usuarioId ID do usuário
     * @return Número de livros atualmente emprestados
     * @throws SQLException Caso ocorra um erro no banco de dados
     */
    public int contarEmprestimosAtivos(int usuarioId) throws SQLException {
        return emprestimoRepository.contarEmprestimosAtivos(usuarioId);
    }

    /**
     * Realiza um empréstimo de um livro para um usuário.
     * @param emprestimo Dados do empréstimo
     * @return Mensagem de sucesso ou erro
     * @throws SQLException Caso ocorra um erro no banco de dados
     */
    public String realizarEmprestimo(EmprestimoModel emprestimo) throws SQLException {
        // Verificar se o usuário já tem 5 livros emprestados
        int emprestimosAtivos = contarEmprestimosAtivos(emprestimo.getUsuario().getIdUsuario());
        if (emprestimosAtivos >= 5) {
            return "Usuário já atingiu o limite de 5 livros emprestados.";
        }

        // Verificar se o livro tem exemplares disponíveis
        LivroModel livro = livroRepository.consultarPorId(emprestimo.getLivro().getIdLivro());
        if (livro == null) {
            return "Livro não encontrado.";
        }
        if (livro.getQtdDisponivel() <= 0) {
            return "Não há exemplares disponíveis para empréstimo.";
        }

        // Reduzir a quantidade disponível do livro
        livro.setQtdDisponivel(livro.getQtdDisponivel() - 1);
        livroRepository.atualizar(livro);

        // Registrar o empréstimo no banco de dados
        Exception e = emprestimoRepository.salvar(emprestimo);
        if (e == null) {
            return "Empréstimo realizado com sucesso!";
        } else {
            return "Erro ao registrar o empréstimo: " + e.getMessage();
        }
    }

    /**
     * Realiza a devolução de um livro emprestado.
     * Se a devolução for feita após o prazo, calcula a multa.
     * @param emprestimoId ID do empréstimo
     * @return Mensagem indicando sucesso ou atraso com multa
     * @throws SQLException Caso ocorra um erro no banco de dados
     */
    public String realizarDevolucao(int emprestimoId) throws SQLException {
        EmprestimoModel emprestimo = emprestimoRepository.consultarPorId(emprestimoId);
        if (emprestimo == null) {
            return "Empréstimo não encontrado.";
        }

        // Obter datas
        Date dataAtual = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dataPrevista = emprestimo.getDataDevolucaoPrevista();

        // Calcular a diferença de dias
        long diferencaMillis = dataAtual.getTime() - dataPrevista.getTime();
        long diasAtraso = TimeUnit.DAYS.convert(diferencaMillis, TimeUnit.MILLISECONDS);

        // Calcular multa (R$ 2,00 por dia de atraso)
        double multa = 0;
        if (diasAtraso > 0) {
            multa = diasAtraso * 2.0;
        }

        // Registrar a devolução no banco de dados
        emprestimo.setDataDevolucao(sdf.format(dataAtual));
        Exception e = emprestimoRepository.atualizar(emprestimo);
        if (e != null) {
            return "Erro ao registrar a devolução: " + e.getMessage();
        }

        // Aumentar a quantidade disponível do livro
        LivroModel livro = emprestimo.getLivro();
        livro.setQtdDisponivel(livro.getQtdDisponivel() + 1);
        livroRepository.atualizar(livro);

        if (diasAtraso > 0) {
            return "Livro devolvido com atraso de " + diasAtraso + " dias. Multa a pagar: R$ " + multa;
        } else {
            return "Livro devolvido dentro do prazo. Sem multa!";
        }
    }

    /**
     * Consulta um empréstimo pelo ID.
     * @param emprestimoId ID do empréstimo
     * @return Objeto EmprestimoModel ou null se não encontrado
     * @throws SQLException Caso ocorra um erro no banco de dados
     */
    public EmprestimoModel consultarEmprestimoPorId(int emprestimoId) throws SQLException {
        return emprestimoRepository.consultarPorId(emprestimoId);
    }

    /**
     * Retorna todos os empréstimos ativos de um usuário.
     * @param usuarioId ID do usuário
     * @return Lista de empréstimos ativos
     * @throws SQLException Caso ocorra um erro no banco de dados
     */
    public List<EmprestimoModel> listarEmprestimosAtivos(int usuarioId) throws SQLException {
        return emprestimoRepository.listarEmprestimosAtivos(usuarioId);
    }

    public List<EmprestimoModel> listarEmprestimosPorNomeUsuario(String nomeUsuario) throws SQLException {
        return emprestimoRepository.listarEmprestimosPorNomeUsuario(nomeUsuario);
    }
}
