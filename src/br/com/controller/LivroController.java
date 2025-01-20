package br.com.controller;

import br.com.model.LivroModel;
import br.com.repository.LivroRepository;


import java.sql.SQLException;

public class LivroController {
    LivroRepository livroRepository = new LivroRepository();

    public String salvarLivro(LivroModel livro) throws SQLException {
        Exception e = livroRepository.salvar(livro);
        if (e == null){
            return "Salvo com Sucesso!";
        }else{
            return e.getMessage();
        }
    }
}
