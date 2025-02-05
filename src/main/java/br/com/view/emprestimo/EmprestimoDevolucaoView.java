package br.com.view.emprestimo;

import br.com.controller.EmprestimoController;
import br.com.model.EmprestimoModel;

import javax.swing.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EmprestimoDevolucaoView {
    private JPanel mainPanel;
    private JTextField emprestimoIdField;
    private JButton buscarButton;
    private JTextArea resultadoArea;
    private JButton devolverButton;
    private JButton voltarButton;

    private final EmprestimoController emprestimoController;
    private EmprestimoModel emprestimoSelecionado;

    public EmprestimoDevolucaoView() {
        emprestimoController = new EmprestimoController();

        // Iniciar o botão de devolução desativado
        devolverButton.setEnabled(false);

        // Buscar empréstimo
        buscarButton.addActionListener(e -> {
            try {
                int emprestimoId = Integer.parseInt(emprestimoIdField.getText());
                emprestimoSelecionado = emprestimoController.consultarEmprestimoPorId(emprestimoId);
                if (emprestimoSelecionado != null) {
                    resultadoArea.setText("Empréstimo encontrado:\n" +
                            "Usuário: " + emprestimoSelecionado.getUsuario().getNome() + "\n" +
                            "Livro: " + emprestimoSelecionado.getLivro().getTitulo() + "\n" +
                            "Data de Empréstimo: " + emprestimoSelecionado.getDataEmprestimo() + "\n" +
                            "Data de Devolução Prevista: " + emprestimoSelecionado.getDataDevolucaoPrevista());
                    devolverButton.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Empréstimo não encontrado.");
                    devolverButton.setEnabled(false);
                }
            } catch (NumberFormatException | SQLException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Erro ao buscar empréstimo: " + ex.getMessage());
                devolverButton.setEnabled(false);
            }
        });

        // Devolver livro
        devolverButton.addActionListener(e -> {
            if (emprestimoSelecionado == null) {
                JOptionPane.showMessageDialog(mainPanel, "Nenhum empréstimo selecionado.");
                return;
            }

            try {
                String resultado = emprestimoController.realizarDevolucao(emprestimoSelecionado.getIdEmprestimo());
                JOptionPane.showMessageDialog(mainPanel, resultado);

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
                if (frame != null) {
                    frame.dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Erro ao devolver livro: " + ex.getMessage());
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
        JFrame frame = new JFrame("Devolução de Livro");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmprestimoDevolucaoView devolucaoView = new EmprestimoDevolucaoView();
            devolucaoView.exibir();
        });
    }
}
