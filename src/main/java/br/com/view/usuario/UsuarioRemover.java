package br.com.view.usuario;

import br.com.controller.UsuarioController;
import br.com.model.UsuarioModel;

import javax.swing.*;
import java.sql.SQLException;

public class UsuarioRemover {
    private JPanel mainPanel;
    private JTextField idField;
    private JButton buscarButton;
    private JButton removerButton;
    private JButton cancelarButton;
    private JTextArea textArea;

    private final UsuarioController usuarioController;

    public UsuarioRemover() {
        usuarioController = new UsuarioController();

        // Configurar o JTextArea para exibir os detalhes
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Configurar ação do botão "Buscar"
        buscarButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                UsuarioModel usuario = usuarioController.consultarUsuarioPorId(id);

                if (usuario != null) {
                    textArea.setText("Usuário encontrado:\n"
                            + "Nome: " + usuario.getNome() + "\n"
                            + "Sexo: " + usuario.getSexo() + "\n"
                            + "Número de Celular: " + usuario.getNumeroCelular() + "\n"
                            + "E-mail: " + usuario.getEmail() + "\n"
                            + "Número de Identificação: " + usuario.getNumeroIdentificacao());
                    removerButton.setEnabled(true);
                } else {
                    textArea.setText("Usuário com ID " + id + " não encontrado.");
                    removerButton.setEnabled(false);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "ID inválido. Por favor, insira um número.");
                removerButton.setEnabled(false);
                textArea.setText("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Erro ao buscar o usuário: " + ex.getMessage());
                textArea.setText("");
            }
        });

        // Configurar ação do botão "Remover"
        removerButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String resultado = usuarioController.removerUsuario(id);
                JOptionPane.showMessageDialog(mainPanel, resultado);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
                if (frame != null) {
                    frame.dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "ID inválido. Por favor, insira um número.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Erro ao remover o usuário: " + ex.getMessage());
            }
        });

        // Configurar ação do botão "Cancelar"
        cancelarButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
            if (frame != null) {
                frame.dispose();
            }
        });

        // Inicialmente desativar o botão "Remover"
        removerButton.setEnabled(false);
    }

    public void exibir() {
        JFrame frame = new JFrame("Remover Usuário");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioRemover usuarioRemover = new UsuarioRemover();
            usuarioRemover.exibir();
        });
    }
}
