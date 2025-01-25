package br.com.view.livro;

import br.com.controller.LivroController;
import br.com.model.LivroModel;

import javax.swing.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LivroEdicao {
    private JPanel mainPanel;
    private JTextField idField;
    private JButton buscarButton;
    private JTextField tituloField;
    private JTextField temaField;
    private JTextField autorField;
    private JTextField isbnField;
    private JTextField qtdDisponivelField;
    private JSpinner dataPublicacaoField;
    private JButton salvarButton;
    private JButton voltarButton;

    private final LivroController livroController;
    private LivroModel livroExistente; // Variável para armazenar o livro buscado

    public LivroEdicao() {
        livroController = new LivroController();

        // Configurar JSpinner para data de publicação
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dataPublicacaoField.setModel(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dataPublicacaoField, "yyyy-MM-dd");
        dataPublicacaoField.setEditor(dateEditor);

        // Configurar ação do botão "Buscar"
        buscarButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                livroExistente = livroController.consultarLivroPorId(id);
                if (livroExistente != null) {
                    preencheCampos(livroExistente);
                    JOptionPane.showMessageDialog(mainPanel, "Livro encontrado. Você pode editar os campos agora.");
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Livro com ID " + id + " não encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "ID inválido. Por favor, insira um número.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Erro ao buscar o livro: " + ex.getMessage());
            }
        });

        // Configurar ação do botão "Salvar"
        salvarButton.addActionListener(e -> {
            if (livroExistente == null) {
                JOptionPane.showMessageDialog(mainPanel, "Nenhum livro foi buscado para edição. Por favor, busque um livro primeiro.");
                return;
            }

            try {
                livroExistente.setTitulo(tituloField.getText());
                livroExistente.setTema(temaField.getText());
                livroExistente.setAutor(autorField.getText());
                livroExistente.setIsbn(Integer.parseInt(isbnField.getText()));
                Date dataPublicacao = (Date) dataPublicacaoField.getValue();
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(dataPublicacao);
                livroExistente.setDataPublicacao(formattedDate);
                livroExistente.setQtdDisponivel(Integer.parseInt(qtdDisponivelField.getText()));

                String resultado = livroController.atualizarLivro(livroExistente);
                JOptionPane.showMessageDialog(mainPanel, resultado);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
                if (frame != null) {
                    frame.dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Erro nos dados: " + ex.getMessage());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Erro ao salvar o livro: " + ex.getMessage());
            }
        });

        // Configurar ação do botão "Voltar"
        voltarButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
            if (frame != null) {
                frame.dispose();
            }
        });
    }

    private void preencheCampos(LivroModel livro) {
        tituloField.setText(livro.getTitulo());
        temaField.setText(livro.getTema());
        autorField.setText(livro.getAutor());
        isbnField.setText(String.valueOf(livro.getIsbn()));
        qtdDisponivelField.setText(String.valueOf(livro.getQtdDisponivel()));

        try {
            Date data = new SimpleDateFormat("yyyy-MM-dd").parse(livro.getDataPublicacao());
            dataPublicacaoField.setValue(data);
        } catch (Exception e) {
            dataPublicacaoField.setValue(new Date());
        }
    }

    public void exibir() {
        JFrame frame = new JFrame("Edição de Livro");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LivroEdicao livroEdicao = new LivroEdicao();
            livroEdicao.exibir();
        });
    }
}
