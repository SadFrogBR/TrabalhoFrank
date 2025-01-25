package br.com.view.livro;

import javax.swing.*;
import java.awt.*;

public class LivroCrudMenu {
    private JPanel mainPainel;
    private JButton cadastrarButton;
    private JButton consultarButton;
    private JButton removerButton;
    private JButton editarButton;
    private JButton voltarButton;

    public LivroCrudMenu() {

        cadastrarButton.addActionListener(e -> {
            LivroCadastro livroCadastro = new LivroCadastro();
            livroCadastro.exibeFormulario();
        });


        consultarButton.addActionListener(e -> {
            LivroExibicao livroExibicao = new LivroExibicao();
            livroExibicao.exibir();
        });


        removerButton.addActionListener(e -> {
            LivroRemover livroRemover = new LivroRemover();
            livroRemover.exibir();
        });


        editarButton.addActionListener(e -> {
            LivroEdicao livroEdicao = new LivroEdicao();
            livroEdicao.exibir();
        });


        voltarButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPainel);
            if (frame != null) {
                frame.dispose();
            }
        });
    }

    public void exibirMenu() {
        JFrame frame = new JFrame("CRUD de Livros");
        frame.setContentPane(mainPainel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LivroCrudMenu crudMenu = new LivroCrudMenu();
            crudMenu.exibirMenu();
        });
    }
}
