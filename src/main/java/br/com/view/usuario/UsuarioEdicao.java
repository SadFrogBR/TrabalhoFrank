package br.com.view.usuario;

import br.com.controller.UsuarioController;
import br.com.model.UsuarioModel;

import javax.swing.*;
import java.sql.SQLException;

public class UsuarioEdicao {
    private JPanel mainPanel;
    private JTextField idField;
    private JButton buscarButton;
    private JTextField nomeField;
    private JComboBox<String> sexoComboBox;
    private JTextField numeroCelularField;
    private JTextField emailField;
    private JTextField numeroIdentificacaoField;
    private JButton salvarButton;
    private JButton voltarButton;

    private final UsuarioController usuarioController;
    private UsuarioModel usuarioExistente; // Para armazenar o usuário buscado

    public UsuarioEdicao() {
        usuarioController = new UsuarioController();

        // Adicionar opções ao JComboBox
        sexoComboBox.addItem("Masculino");
        sexoComboBox.addItem("Feminino");
        sexoComboBox.addItem("Outro");

        // Configurar ação do botão "Buscar"
        buscarButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                usuarioExistente = usuarioController.consultarUsuarioPorId(id);
                if (usuarioExistente != null) {
                    preencheCampos(usuarioExistente);
                    JOptionPane.showMessageDialog(mainPanel, "Usuário encontrado. Você pode editar os campos agora.");
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Usuário com ID " + id + " não encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "ID inválido. Por favor, insira um número.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Erro ao buscar o usuário: " + ex.getMessage());
            }
        });

        // Configurar ação do botão "Salvar"
        salvarButton.addActionListener(e -> {
            if (usuarioExistente == null) {
                JOptionPane.showMessageDialog(mainPanel, "Nenhum usuário foi buscado para edição. Por favor, busque um usuário primeiro.");
                return;
            }

            try {
                usuarioExistente.setNome(nomeField.getText());
                usuarioExistente.setSexo((String) sexoComboBox.getSelectedItem());
                String numeroCelular = numeroCelularField.getText().trim();
                if (!numeroCelular.matches("\\(\\d{2}\\) \\d{5}-\\d{4}")) {
                    JOptionPane.showMessageDialog(mainPanel, "Número de celular inválido. O formato correto é (xx) xxxxx-xxxx.");
                    return;
                }
                usuarioExistente.setNumeroCelular(numeroCelular);

                String email = emailField.getText().trim();
                if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                    JOptionPane.showMessageDialog(mainPanel, "E-mail inválido.");
                    return;
                }
                usuarioExistente.setEmail(email);

                usuarioExistente.setNumeroIdentificacao(Integer.parseInt(numeroIdentificacaoField.getText().trim()));

                String resultado = usuarioController.atualizarUsuario(usuarioExistente);
                JOptionPane.showMessageDialog(mainPanel, resultado);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
                if (frame != null) {
                    frame.dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Erro nos dados: " + ex.getMessage());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Erro ao salvar o usuário: " + ex.getMessage());
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

    private void preencheCampos(UsuarioModel usuario) {
        nomeField.setText(usuario.getNome());
        sexoComboBox.setSelectedItem(usuario.getSexo());
        numeroCelularField.setText(usuario.getNumeroCelular());
        emailField.setText(usuario.getEmail());
        numeroIdentificacaoField.setText(String.valueOf(usuario.getNumeroIdentificacao()));
    }

    public void exibir() {
        JFrame frame = new JFrame("Edição de Usuário");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioEdicao usuarioEdicao = new UsuarioEdicao();
            usuarioEdicao.exibir();
        });
    }
}
