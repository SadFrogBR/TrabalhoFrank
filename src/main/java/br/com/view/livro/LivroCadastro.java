package br.com.view.livro;

import br.com.controller.LivroController;
import br.com.model.LivroModel;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LivroCadastro {
    private JPanel mainPanel;
    private JTextField tituloField;
    private JTextField temaField;
    private JTextField isbnField;
    private JTextField autorField;
    private JTextField qtdDisponivelField;
    private JSpinner dataPublicacaoField;
    private JButton salvarButton;
    private JButton voltarButton;

    private final LivroController livroController;

    public LivroCadastro() {
        livroController = new LivroController();

        // Configurar JSpinner para data
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dataPublicacaoField.setModel(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dataPublicacaoField, "yyyy-MM-dd");
        dataPublicacaoField.setEditor(dateEditor);

        // Configurar ação do botão salvar
        salvarButton.addActionListener(e -> {
            LivroModel livro = coletaDados();
            if (livro != null) {
                String resultado = null;
                try {
                    resultado = livroController.salvarLivro(livro);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(mainPanel, resultado);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
                if (frame != null) {
                    frame.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Erro ao salvar o livro. Verifique os dados informados.");
            }
        });

        // Configurar ação do botão voltar
        voltarButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
            if (frame != null) {
                frame.dispose();
            }
        });
    }

    public LivroModel coletaDados() {
        try {
            String titulo = tituloField.getText();
            String tema = temaField.getText();
            String autor = autorField.getText();
            int isbn = Integer.parseInt(isbnField.getText());
            Date dataPublicacao = (Date) dataPublicacaoField.getValue();
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(dataPublicacao);
            int qtdDisponivel = Integer.parseInt(qtdDisponivelField.getText());

            return new LivroModel(titulo, tema, autor, isbn, formattedDate, qtdDisponivel);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainPanel, "Erro nos dados: " + e.getMessage());
            return null;
        }
    }

    public void exibeFormulario() {
        JFrame frame = new JFrame("Cadastro de Livro");
        frame.setSize(640, 480);
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LivroCadastro livroCadastro = new LivroCadastro();
            livroCadastro.exibeFormulario();
        });
    }
}
