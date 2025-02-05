package br.com.controller;

import br.com.model.LivroModel;
import br.com.repository.LivroRepository;

import java.sql.SQLException;
import java.util.List;

public class LivroController {
    LivroRepository livroRepository = new LivroRepository();

    public String salvarLivro(LivroModel livro) throws SQLException {
        Exception e = livroRepository.salvar(livro);
        if (e == null) {
            return "Livro salvo com sucesso!";
        } else {
            return "Erro ao salvar o livro: " + e.getMessage();
        }
    }

    public LivroModel consultarLivroPorId(int idLivro) throws SQLException {
        LivroModel livro = livroRepository.consultarPorId(idLivro);
        if (livro != null) {
            return livro;
        } else {
            throw new SQLException("Livro com ID " + idLivro + " não encontrado.");
        }
    }

    public String removerLivro(int idLivro) throws SQLException {
        Exception e = livroRepository.remover(idLivro);
        if (e == null) {
            return "Livro removido com sucesso!";
        } else {
            return "Erro ao remover o livro: " + e.getMessage();
        }
    }

    public String atualizarLivro(LivroModel livro) throws SQLException {
        Exception e = livroRepository.atualizar(livro);
        if (e == null) {
            return "Livro atualizado com sucesso!";
        } else {
            return "Erro ao atualizar o livro: " + e.getMessage();
        }
    }

    public List<LivroModel> listarLivrosDisponiveis() throws SQLException {
        return livroRepository.listarLivrosDisponiveis();
    }
}