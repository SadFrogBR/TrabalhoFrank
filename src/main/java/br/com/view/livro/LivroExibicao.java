package br.com.view.livro;

import br.com.controller.LivroController;
import br.com.model.LivroModel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LivroExibicao {
    private JPanel mainPanel;
    private JTextField idField;
    private JButton consultarButton;
    private JTextArea resultadoArea;
    private JButton voltarButton;

    private final LivroController livroController;

    public LivroExibicao() {
        livroController = new LivroController();


        consultarButton.addActionListener(e -> {
            String idTexto = idField.getText();
            try {
                int id = Integer.parseInt(idTexto);
                LivroModel livro = livroController.consultarLivroPorId(id);
                if (livro != null) {
                    exibeLivro(livro);
                } else {
                    resultadoArea.setText("Livro com ID " + id + " não encontrado.");
                }
            } catch (NumberFormatException ex) {
                resultadoArea.setText("ID inválido. Por favor, insira um número.");
            } catch (SQLException ex) {
                resultadoArea.setText("Erro ao consultar o livro: " + ex.getMessage());
            }
        });


        voltarButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
            if (frame != null) {
                frame.dispose();
            }
        });
    }

    private void exibeLivro(LivroModel livro) {
        resultadoArea.setText("Detalhes do Livro:\n" +
                "ID: " + livro.getIdLivro() + "\n" +
                "Título: " + livro.getTitulo() + "\n" +
                "Tema: " + livro.getTema() + "\n" +
                "Autor: " + livro.getAutor() + "\n" +
                "ISBN: " + livro.getIsbn() + "\n" +
                "Data de Publicação: " + livro.getDataPublicacao() + "\n" +
                "Quantidade Disponível: " + livro.getQtdDisponivel());
    }

    public void exibir() {
        JFrame frame = new JFrame("Exibição de Livro");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LivroExibicao livroExibicao = new LivroExibicao();
            livroExibicao.exibir();
        });
    }
}
