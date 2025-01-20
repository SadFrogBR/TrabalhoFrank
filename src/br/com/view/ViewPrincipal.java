package br.com.view;

import br.com.controller.LivroController;
import br.com.controller.UsuarioController;
import br.com.model.LivroModel;
import br.com.model.UsuarioModel;

import javax.swing.*;
import java.sql.SQLException;

public class ViewPrincipal {

    public void apresentaMenuPrincipal() throws SQLException {
        int op;
        LivroController livroController = new LivroController();
        UsuarioController usuarioController = new UsuarioController();
        do {
            op = menu();
            switch (op) {
                case 1:
                    LivroModel livro = new LivroModel();
                    LivroView livroView = new LivroView();
                    livro = livroView.coletaDados(livro);
                    String retorno = livroController.salvarLivro(livro);
                    JOptionPane.showMessageDialog(null, retorno);
                    break;
                case 2:
                    UsuarioModel usuario = new UsuarioModel();
                    UsuarioView usuarioView = new UsuarioView();
                    usuario = usuarioView.coletaDadosUsuarios(usuario);
                    String retornoUsuario = usuarioController.salvarUsuario(usuario);
                    JOptionPane.showMessageDialog(null, retornoUsuario);
                    break;
                case 3:

                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Fechando Programa");
                    break;
                default:
                    System.out.println("numero invalido");
            }
        } while (op != 0);
    }

    public int menu(){
        String menu = "\nDigite o número correspondente\n1 - para cadastrar um Livro\n";
        menu += "2 - para cadastrar um usuario";
//        menu += "4 - para buscar por ID\n5 - remover\n";
//        menu += "6 - Editar\n0 - para fechar o programa";

        String opt = JOptionPane.showInputDialog(null, menu);

        return Integer.parseInt(opt);
    }



}
