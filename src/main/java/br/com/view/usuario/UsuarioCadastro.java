package br.com.view.usuario;

import br.com.controller.UsuarioController;
import br.com.model.UsuarioModel;

import javax.swing.*;
import java.sql.SQLException;

public class UsuarioCadastro {
    private JPanel mainPanel;
    private JTextField nomeField;
    private JComboBox<String> sexoComboBox;
    private JTextField numeroCelularField;
    private JTextField emailField;
    private JTextField numeroIdentificacaoField;
    private JButton salvarButton;
    private JButton voltarButton;

    private final UsuarioController usuarioController;

    public UsuarioCadastro() {
        usuarioController = new UsuarioController();


        sexoComboBox.addItem("Masculino");
        sexoComboBox.addItem("Feminino");
        sexoComboBox.addItem("Outro");


        salvarButton.addActionListener(e -> {
            UsuarioModel usuario = coletaDados();
            if (usuario != null) {
                try {
                    String resultado = usuarioController.salvarUsuario(usuario);
                    JOptionPane.showMessageDialog(mainPanel, resultado);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
                    if (frame != null) {
                        frame.dispose();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Erro ao salvar o usuário: " + ex.getMessage());
                }
            }
        });


        voltarButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
            if (frame != null) {
                frame.dispose();
            }
        });
    }

    private UsuarioModel coletaDados() {
        UsuarioModel usuario = new UsuarioModel();


        String nome = nomeField.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "O campo Nome é obrigatório.");
            return null;
        }
        usuario.setNome(nome);


        String sexo = (String) sexoComboBox.getSelectedItem();
        usuario.setSexo(sexo);


        String numeroCelular = numeroCelularField.getText().trim();
        if (!numeroCelular.matches("\\(\\d{2}\\) \\d{5}-\\d{4}")) {
            JOptionPane.showMessageDialog(mainPanel, "Número de celular inválido. O formato correto é (xx) xxxxx-xxxx.");
            return null;
        }
        usuario.setNumeroCelular(numeroCelular);


        String email = emailField.getText().trim();
        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            JOptionPane.showMessageDialog(mainPanel, "E-mail inválido.");
            return null;
        }
        usuario.setEmail(email);


        try {
            int numeroIdentificacao = Integer.parseInt(numeroIdentificacaoField.getText().trim());
            usuario.setNumeroIdentificacao(numeroIdentificacao);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainPanel, "Número de identificação inválido. Deve ser um número.");
            return null;
        }

        return usuario;
    }

    public void exibeFormulario() {
        JFrame frame = new JFrame("Cadastro de Usuário");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioCadastro usuarioCadastro = new UsuarioCadastro();
            usuarioCadastro.exibeFormulario();
        });
    }
}
