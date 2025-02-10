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

    public int contarEmprestimosAtivos(int usuarioId) throws SQLException {
        return emprestimoRepository.contarEmprestimosAtivos(usuarioId);
    }

    public String realizarEmprestimo(EmprestimoModel emprestimo) throws SQLException {
        // Verificar se o usuário já tem 5 livros emprestados
        int emprestimosAtivos = contarEmprestimosAtivos(emprestimo.getUsuario().getIdUsuario());
        if (emprestimosAtivos >= 5) {
            return "Usuário já atingiu o limite de 5 livros emprestados.";
        }

        LivroModel livro = livroRepository.consultarPorId(emprestimo.getLivro().getIdLivro());
        if (livro == null) {
            return "Livro não encontrado.";
        }
        if (livro.getQtdDisponivel() <= 0) {
            return "Não há exemplares disponíveis para empréstimo.";
        }

        livro.setQtdDisponivel(livro.getQtdDisponivel() - 1);
        livroRepository.atualizar(livro);

        Exception e = emprestimoRepository.salvar(emprestimo);
        if (e == null) {
            return "Empréstimo realizado com sucesso!";
        } else {
            return "Erro ao registrar o empréstimo: " + e.getMessage();
        }
    }

    public String realizarDevolucao(int emprestimoId) throws SQLException {
        EmprestimoModel emprestimo = emprestimoRepository.consultarPorId(emprestimoId);
        if (emprestimo == null) {
            return "Empréstimo não encontrado.";
        }

        Date dataAtual = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dataPrevista = emprestimo.getDataDevolucaoPrevista();

        long diferencaMillis = dataAtual.getTime() - dataPrevista.getTime();
        long diasAtraso = TimeUnit.DAYS.convert(diferencaMillis, TimeUnit.MILLISECONDS);

        double multa = 0;
        if (diasAtraso > 0) {
            multa = diasAtraso * 2.0;
        }

        emprestimo.setDataDevolucao(sdf.format(dataAtual));
        Exception e = emprestimoRepository.atualizar(emprestimo);
        if (e != null) {
            return "Erro ao registrar a devolução: " + e.getMessage();
        }

        LivroModel livro = emprestimo.getLivro();
        livro.setQtdDisponivel(livro.getQtdDisponivel() + 1);
        livroRepository.atualizar(livro);

        if (diasAtraso > 0) {
            return "Livro devolvido com atraso de " + diasAtraso + " dias. Multa a pagar: R$ " + multa;
        } else {
            return "Livro devolvido dentro do prazo. Sem multa!";
        }
    }

    public EmprestimoModel consultarEmprestimoPorId(int emprestimoId) throws SQLException {
        return emprestimoRepository.consultarPorId(emprestimoId);
    }

    public List<EmprestimoModel> listarEmprestimosAtivos(int usuarioId) throws SQLException {
        return emprestimoRepository.listarEmprestimosAtivos(usuarioId);
    }

    public List<EmprestimoModel> listarEmprestimosAtivosPorNomeUsuario(String nomeUsuario) throws SQLException {
        return emprestimoRepository.listarEmprestimosAtivosPorNomeUsuario(nomeUsuario);
    }

}
