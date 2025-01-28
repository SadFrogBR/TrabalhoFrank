package br.com.view.usuario;

import br.com.controller.UsuarioController;
import br.com.model.UsuarioModel;

import javax.swing.*;
import java.sql.SQLException;

public class UsuarioExibicao {
    private JPanel mainPanel;
    private JTextField idField;
    private JButton consultarButton;
    private JTextArea resultadoArea;
    private JButton voltarButton;

    private final UsuarioController usuarioController;

    public UsuarioExibicao() {
        usuarioController = new UsuarioController();


        consultarButton.addActionListener(e -> {
            String idTexto = idField.getText();
            try {
                int id = Integer.parseInt(idTexto);
                UsuarioModel usuario = usuarioController.consultarUsuarioPorId(id);
                if (usuario != null) {
                    exibeUsuario(usuario);
                } else {
                    resultadoArea.setText("Usuário com ID " + id + " não encontrado.");
                }
            } catch (NumberFormatException ex) {
                resultadoArea.setText("ID inválido. Por favor, insira um número.");
            } catch (SQLException ex) {
                resultadoArea.setText("Erro ao consultar o usuário: " + ex.getMessage());
            }
        });


        voltarButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
            if (frame != null) {
                frame.dispose();
            }
        });
    }

    private void exibeUsuario(UsuarioModel usuario) {
        resultadoArea.setText("Detalhes do Usuário:\n" +
                "ID: " + usuario.getIdUsuario() + "\n" +
                "Nome: " + usuario.getNome() + "\n" +
                "Sexo: " + usuario.getSexo() + "\n" +
                "Número de Celular: " + usuario.getNumeroCelular() + "\n" +
                "E-mail: " + usuario.getEmail() + "\n" +
                "Número de Identificação: " + usuario.getNumeroIdentificacao());
    }

    public void exibir() {
        JFrame frame = new JFrame("Exibição de Usuário");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioExibicao usuarioExibicao = new UsuarioExibicao();
            usuarioExibicao.exibir();
        });
    }
}
