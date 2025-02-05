package br.com.view.livro;

import br.com.controller.LivroController;
import br.com.model.LivroModel;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class LivroDisponivelView {
    private JPanel mainPanel;
    private JTextArea resultadoArea;
    private JButton voltarButton;

    private final LivroController livroController;

    public LivroDisponivelView() {
        livroController = new LivroController();

        // Buscar e exibir livros disponíveis
        try {
            List<LivroModel> livrosDisponiveis = livroController.listarLivrosDisponiveis();
            if (livrosDisponiveis == null || livrosDisponiveis.isEmpty()) {
                resultadoArea.setText("Nenhum livro disponível no momento.");
            } else {
                StringBuilder listaLivros = new StringBuilder("Livros disponíveis:\n");
                for (LivroModel livro : livrosDisponiveis) {
                    listaLivros.append("ID: ").append(livro.getIdLivro())
                            .append(" - ").append(livro.getTitulo())
                            .append(" (").append(livro.getQtdDisponivel()).append(" disponíveis)\n");
                }
                resultadoArea.setText(listaLivros.toString());
            }
        } catch (SQLException e) {
            resultadoArea.setText("Erro ao buscar livros disponíveis: " + e.getMessage());
        }

        // Configurar ação do botão "Voltar"
        voltarButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
            if (frame != null) {
                frame.dispose();
            }
        });
    }

    public void exibir() {
        JFrame frame = new JFrame("Livros Disponíveis");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LivroDisponivelView livroDisponivelView = new LivroDisponivelView();
            livroDisponivelView.exibir();
        });
    }
}
