package br.com.view;

import br.com.view.livro.LivroCrudMenu;
import br.com.view.usuario.UsuarioCrudMenu;

import javax.swing.*;

public class ViewPrincipalSwing extends JFrame{
    private JPanel mainPanel;
    private JButton livroButton;
    private JButton usuarioButton;
    private JButton sairButton;

    public ViewPrincipalSwing() {
        this.setTitle("Menu Principal");
        this.setSize(640, 480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        livroButton.addActionListener(e -> {
            LivroCrudMenu menu = new LivroCrudMenu();
            menu.exibirMenu();
        });


        usuarioButton.addActionListener(e -> {
            UsuarioCrudMenu menu = new UsuarioCrudMenu();
            menu.exibirMenu();
        });


        sairButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Fechando Programa.");
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
            frame.dispose();
        });
    }

}
