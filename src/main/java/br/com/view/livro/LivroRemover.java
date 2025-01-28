package br.com.view.livro;

import br.com.controller.LivroController;
import br.com.model.LivroModel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LivroRemover {
    private JPanel mainPanel;
    private JTextField idField;
    private JButton buscarButton;
    private JButton removerButton;
    private JButton cancelarButton;
    private JTextArea textArea;

    private final LivroController livroController;

    public LivroRemover() {
        livroController = new LivroController();


        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);


        buscarButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                LivroModel livro = livroController.consultarLivroPorId(id);

                if (livro != null) {
                    textArea.setText("Livro encontrado:\n"
                            + "Título: " + livro.getTitulo() + "\n"
                            + "Tema: " + livro.getTema() + "\n"
                            + "Autor: " + livro.getAutor() + "\n"
                            + "ISBN: " + livro.getIsbn() + "\n"
                            + "Data de Publicação: " + livro.getDataPublicacao() + "\n"
                            + "Quantidade Disponível: " + livro.getQtdDisponivel());
                    removerButton.setEnabled(true);
                } else {
                    textArea.setText("Livro com ID " + id + " não encontrado.");
                    removerButton.setEnabled(false);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "ID inválido. Por favor, insira um número.");
                removerButton.setEnabled(false);
                textArea.setText("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Erro ao buscar o livro: " + ex.getMessage());
                textArea.setText("");
            }
        });


        removerButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String resultado = livroController.removerLivro(id);
                JOptionPane.showMessageDialog(mainPanel, resultado);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
                if (frame != null) {
                    frame.dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "ID inválido. Por favor, insira um número.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Erro ao remover o livro: " + ex.getMessage());
            }
        });


        cancelarButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
            if (frame != null) {
                frame.dispose();
            }
        });


        removerButton.setEnabled(false);
    }

    public void exibir() {
        JFrame frame = new JFrame("Remover Livro");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LivroRemover livroRemover = new LivroRemover();
            livroRemover.exibir();
        });
    }
}
