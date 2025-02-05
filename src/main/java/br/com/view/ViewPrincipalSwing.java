package br.com.view;

import br.com.view.emprestimo.EmprestimoDevolucaoView;
import br.com.view.emprestimo.EmprestimoUsuarioView;
import br.com.view.emprestimo.EmprestimoView;
import br.com.view.livro.LivroCrudMenu;
import br.com.view.livro.LivroDisponivelView;
import br.com.view.usuario.UsuarioCrudMenu;

import javax.swing.*;

public class ViewPrincipalSwing extends JFrame{
    private JPanel mainPanel;
    private JButton livroButton;
    private JButton usuarioButton;
    private JButton sairButton;
    private JButton fazerEmprestimoButton;
    private JButton fazerDevolucaoButton;
    private JButton listarLivrosDisponiveisButton;
    private JButton listarEmprestimosAtivosButton;

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

        listarLivrosDisponiveisButton.addActionListener(e -> {
            LivroDisponivelView livroDisponivelView = new LivroDisponivelView();
            livroDisponivelView.exibir();
        });

        listarEmprestimosAtivosButton.addActionListener(e -> {
            EmprestimoUsuarioView emprestimoUsuarioView = new EmprestimoUsuarioView();
            emprestimoUsuarioView.exibir();
        });

        fazerEmprestimoButton.addActionListener(e -> {
            EmprestimoView emprestimoView = new EmprestimoView();
            emprestimoView.exibir();
        });

        fazerDevolucaoButton.addActionListener(e -> {
            EmprestimoDevolucaoView emprestimoDevolucaoView = new EmprestimoDevolucaoView();
            emprestimoDevolucaoView.exibir();
        });


        sairButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Fechando Programa.");
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
            frame.dispose();
        });
    }

}
