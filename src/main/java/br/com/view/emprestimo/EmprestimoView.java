package br.com.view.emprestimo;

import br.com.controller.EmprestimoController;
import br.com.controller.LivroController;
import br.com.controller.UsuarioController;
import br.com.model.EmprestimoModel;
import br.com.model.LivroModel;
import br.com.model.UsuarioModel;

import javax.swing.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EmprestimoView {
    private JPanel mainPanel;
    private JTextField usuarioIdField;
    private JTextField livroIdField;
    private JButton buscarUsuarioButton;
    private JButton buscarLivroButton;
    private JTextArea resultadoArea;
    private JButton emprestarButton;
    private JButton voltarButton;
    private JSpinner dataEmprestimoSpinner;
    private JSpinner dataDevolucaoPrevistaSpinner;

    private final UsuarioController usuarioController;
    private final LivroController livroController;
    private final EmprestimoController emprestimoController;

    private UsuarioModel usuarioSelecionado;
    private LivroModel livroSelecionado;

    public EmprestimoView() {
        usuarioController = new UsuarioController();
        livroController = new LivroController();
        emprestimoController = new EmprestimoController();

        // Configuração dos Spinners de Data
        SpinnerDateModel dateModelEmprestimo = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        dataEmprestimoSpinner.setModel(dateModelEmprestimo);
        dataEmprestimoSpinner.setEditor(new JSpinner.DateEditor(dataEmprestimoSpinner, "yyyy-MM-dd"));

        SpinnerDateModel dateModelDevolucao = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        dataDevolucaoPrevistaSpinner.setModel(dateModelDevolucao);
        dataDevolucaoPrevistaSpinner.setEditor(new JSpinner.DateEditor(dataDevolucaoPrevistaSpinner, "yyyy-MM-dd"));

        // Iniciar o botão de empréstimo desativado
        emprestarButton.setEnabled(false);

        // Buscar usuário
        buscarUsuarioButton.addActionListener(e -> {
            try {
                int usuarioId = Integer.parseInt(usuarioIdField.getText());
                usuarioSelecionado = usuarioController.consultarUsuarioPorId(usuarioId);
                if (usuarioSelecionado != null) {
                    resultadoArea.setText("Usuário encontrado:\n" + usuarioSelecionado.toString());
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Usuário não encontrado.");
                    usuarioSelecionado = null;
                }
                atualizarEstadoBotaoEmprestar();
            } catch (NumberFormatException | SQLException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Erro ao buscar usuário: " + ex.getMessage());
                usuarioSelecionado = null;
                atualizarEstadoBotaoEmprestar();
            }
        });

        // Buscar livro
        buscarLivroButton.addActionListener(e -> {
            try {
                int livroId = Integer.parseInt(livroIdField.getText());
                livroSelecionado = livroController.consultarLivroPorId(livroId);
                if (livroSelecionado != null) {
                    if (livroSelecionado.getQtdDisponivel() > 0) {
                        resultadoArea.append("\nLivro encontrado:\n" + livroSelecionado.toString());
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Não há exemplares disponíveis para empréstimo.");
                        livroSelecionado = null;
                    }
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Livro não encontrado.");
                    livroSelecionado = null;
                }
                atualizarEstadoBotaoEmprestar();
            } catch (NumberFormatException | SQLException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Erro ao buscar livro: " + ex.getMessage());
                livroSelecionado = null;
                atualizarEstadoBotaoEmprestar();
            }
        });

        // Realizar empréstimo
        emprestarButton.addActionListener(e -> {
            if (usuarioSelecionado == null || livroSelecionado == null) {
                JOptionPane.showMessageDialog(mainPanel, "Usuário ou livro não selecionado.");
                return;
            }

            try {
                if (emprestimoController.contarEmprestimosAtivos(usuarioSelecionado.getIdUsuario()) >= 5) {
                    JOptionPane.showMessageDialog(mainPanel, "Usuário já possui 5 livros emprestados.");
                    return;
                }

                Date dataEmprestimo = (Date) dataEmprestimoSpinner.getValue();
                Date dataDevolucaoPrevista = (Date) dataDevolucaoPrevistaSpinner.getValue();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                EmprestimoModel emprestimo = new EmprestimoModel(
                        livroSelecionado, usuarioSelecionado,
                        sdf.format(dataEmprestimo), sdf.format(dataDevolucaoPrevista));

                String resultado = emprestimoController.realizarEmprestimo(emprestimo);
                JOptionPane.showMessageDialog(mainPanel, resultado);

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
                if (frame != null) {
                    frame.dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Erro ao realizar empréstimo: " + ex.getMessage());
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

    /**
     * Habilita o botão "Emprestar" apenas se um usuário e um livro foram selecionados.
     */
    private void atualizarEstadoBotaoEmprestar() {
        emprestarButton.setEnabled(usuarioSelecionado != null && livroSelecionado != null);
    }

    public void exibir() {
        JFrame frame = new JFrame("Realizar Empréstimo");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(960, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmprestimoView emprestimoView = new EmprestimoView();
            emprestimoView.exibir();
        });
    }
}
