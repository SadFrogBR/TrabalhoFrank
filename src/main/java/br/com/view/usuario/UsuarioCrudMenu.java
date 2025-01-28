package br.com.view.usuario;

import br.com.view.usuario.UsuarioCadastro;
import br.com.view.usuario.UsuarioEdicao;
import br.com.view.usuario.UsuarioExibicao;
import br.com.view.usuario.UsuarioRemover;

import javax.swing.*;

public class UsuarioCrudMenu {
    private JPanel mainPainel;
    private JButton cadastrarButton;
    private JButton consultarButton;
    private JButton removerButton;
    private JButton editarButton;
    private JButton voltarButton;

    public UsuarioCrudMenu() {

        cadastrarButton.addActionListener(e -> {
            UsuarioCadastro usuarioCadastro = new UsuarioCadastro();
            usuarioCadastro.exibeFormulario();
        });

        consultarButton.addActionListener(e -> {
            UsuarioExibicao usuarioExibicao = new UsuarioExibicao();
            usuarioExibicao.exibir();
        });

        removerButton.addActionListener(e -> {
            UsuarioRemover usuarioRemover = new UsuarioRemover();
            usuarioRemover.exibir();
        });

        editarButton.addActionListener(e -> {
            UsuarioEdicao usuarioEdicao = new UsuarioEdicao();
            usuarioEdicao.exibir();
        });

        voltarButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPainel);
            if (frame != null) {
                frame.dispose();
            }
        });
    }

    public void exibirMenu() {
        JFrame frame = new JFrame("CRUD de Usuários");
        frame.setContentPane(mainPainel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioCrudMenu crudMenu = new UsuarioCrudMenu();
            crudMenu.exibirMenu();
        });
    }
}
