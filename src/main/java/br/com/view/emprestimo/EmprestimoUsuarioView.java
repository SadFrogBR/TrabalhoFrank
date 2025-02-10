package br.com.view.emprestimo;

import br.com.controller.EmprestimoController;
import br.com.model.EmprestimoModel;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class EmprestimoUsuarioView {
    private JPanel mainPanel;
    private JTextField nomeUsuarioField;
    private JButton buscarButton;
    private JTextArea resultadoArea;
    private JButton voltarButton;

    private final EmprestimoController emprestimoController;

    public EmprestimoUsuarioView() {
        emprestimoController = new EmprestimoController();

        // Buscar empréstimos pelo nome do usuário
        buscarButton.addActionListener(e -> {
            String nomeUsuario = nomeUsuarioField.getText().trim();
            if (nomeUsuario.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "Digite um nome para buscar.");
                return;
            }

            try {
                List<EmprestimoModel> emprestimos = emprestimoController.listarEmprestimosAtivosPorNomeUsuario(nomeUsuario);
                if (emprestimos == null || emprestimos.isEmpty()) {
                    resultadoArea.setText("Nenhum empréstimo ativo encontrado para o usuário: " + nomeUsuario);
                } else {
                    StringBuilder listaEmprestimos = new StringBuilder("Empréstimos ativos encontrados:\n");
                    for (EmprestimoModel emprestimo : emprestimos) {
                        listaEmprestimos.append("ID do Empréstimo: ").append(emprestimo.getIdEmprestimo()).append("\n")
                                .append("Usuário: ").append(emprestimo.getUsuario().getNome()).append("\n")
                                .append("Livro: ").append(emprestimo.getLivro().getTitulo()).append("\n")
                                .append("Data de Empréstimo: ").append(emprestimo.getDataEmprestimo()).append("\n")
                                .append("Data de Devolução Prevista: ").append(emprestimo.getDataDevolucaoPrevista()).append("\n")
                                .append("--------------------------\n");
                    }
                    resultadoArea.setText(listaEmprestimos.toString());
                }
            } catch (SQLException ex) {
                resultadoArea.setText("Erro ao buscar empréstimos: " + ex.getMessage());
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

    public void exibir() {
        JFrame frame = new JFrame("Empréstimos por Usuário");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmprestimoUsuarioView emprestimoUsuarioView = new EmprestimoUsuarioView();
            emprestimoUsuarioView.exibir();
        });
    }
}
