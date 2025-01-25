package br.com.view;

import br.com.controller.LivroController;
import br.com.controller.UsuarioController;

import javax.swing.*;
import java.sql.SQLException;

public class ViewPrincipal {

    public void apresentaMenuPrincipal() throws SQLException {
        int op;
        do {
            op = menu();
            switch (op) {
                case 1:
                    // Navegar para CRUD de Livros
                    LivroController livroController = new LivroController();
                    livroController.apresentaMenuLivro();
                    break;
                case 2:
                    // Navegar para CRUD de Usuários
                    UsuarioController usuarioController = new UsuarioController();
                    usuarioController.apresentaMenuUsuario();
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Fechando Programa");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha novamente.");
            }
        } while (op != 0);
    }

    public int menu() {
        String menu = "\nDigite o número correspondente:\n" +
                "1 - Acessar CRUD de Livros\n" +
                "2 - Acessar CRUD de Usuários\n" +
                "0 - Fechar o programa";

        String opt = JOptionPane.showInputDialog(null, menu);

        try {
            return Integer.parseInt(opt);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, insira um número válido.");
            return -1;
        }
    }
}