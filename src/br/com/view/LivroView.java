package br.com.view;

import br.com.model.LivroModel;

import javax.swing.*;

public class LivroView {

    public LivroModel coletaDados(LivroModel livro) {

        livro.setTitulo(JOptionPane.showInputDialog(null, "Digite o Titulo do livro"));
        livro.setAutor(JOptionPane.showInputDialog(null, "Digite o nome do Autor do livro"));
        livro.setIsbn(Integer.parseInt(JOptionPane.showInputDialog(null, "Digite ISBN do livro")));
        livro.setQtdDisponivel(Integer.parseInt(JOptionPane.showInputDialog(null, "Digite a quantidade disponivel")));

        return livro;
    }

}
