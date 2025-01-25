package br.com.controller;

import br.com.model.LivroModel;
import br.com.repository.LivroRepository;
import br.com.view.LivroView;

import java.sql.SQLException;

public class LivroController {
    LivroRepository livroRepository = new LivroRepository();

    public void apresentaMenuLivro() {
        LivroView livroView = new LivroView();
        try {
            livroView.apresentaMenuLivro();
        } catch (Exception e) {
            System.out.println("Erro ao apresentar o menu de livros: " + e.getMessage());
        }
    }


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
}