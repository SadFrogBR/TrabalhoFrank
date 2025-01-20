package br.com.view;

import br.com.model.UsuarioModel;

import javax.swing.*;

public class UsuarioView {

    public UsuarioModel coletaDadosUsuarios(UsuarioModel usuario) {

        usuario.setNome(JOptionPane.showInputDialog(null, "Digite o nome do usuario"));
        usuario.setEmail(JOptionPane.showInputDialog(null, "Digite o email do usuario"));
        usuario.setNumeroIdentificacao(Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o numero de identificação")));

        return usuario;

    }
}
