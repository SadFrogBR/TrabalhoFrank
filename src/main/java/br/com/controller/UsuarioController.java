package br.com.controller;

import br.com.model.UsuarioModel;
import br.com.repository.UsuarioRepository;
import br.com.view.UsuarioView;

import java.sql.SQLException;

public class UsuarioController {
    UsuarioRepository usuarioRepository = new UsuarioRepository();

    public void apresentaMenuUsuario() {
        UsuarioView usuarioView = new UsuarioView();
        try {
            usuarioView.apresentaMenuUsuario();
        } catch (Exception e) {
            System.out.println("Erro ao apresentar o menu de usuários: " + e.getMessage());
        }
    }

    public String salvarUsuario(UsuarioModel usuario) throws SQLException {
        Exception e = usuarioRepository.salvar(usuario);
        if (e == null) {
            return "Usuário salvo com sucesso!";
        } else {
            return "Erro ao salvar o usuário: " + e.getMessage();
        }
    }

    public UsuarioModel consultarUsuarioPorId(int idUsuario) throws SQLException {
        UsuarioModel usuario = usuarioRepository.consultarPorId(idUsuario);
        if (usuario != null) {
            return usuario;
        } else {
            throw new SQLException("Usuário com ID " + idUsuario + " não encontrado.");
        }
    }

    public String removerUsuario(int idUsuario) throws SQLException {
        Exception e = usuarioRepository.remover(idUsuario);
        if (e == null) {
            return "Usuário removido com sucesso!";
        } else {
            return "Erro ao remover o usuário: " + e.getMessage();
        }
    }

    public String atualizarUsuario(UsuarioModel usuario) throws SQLException {
        Exception e = usuarioRepository.atualizar(usuario);
        if (e == null) {
            return "Usuário atualizado com sucesso!";
        } else {
            return "Erro ao atualizar o usuário: " + e.getMessage();
        }
    }
}
