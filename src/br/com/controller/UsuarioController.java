package br.com.controller;

import br.com.model.UsuarioModel;
import br.com.repository.UsuarioRepository;

import java.sql.SQLException;

public class UsuarioController {
    UsuarioRepository usuarioRepository = new UsuarioRepository();

    public String salvarUsuario(UsuarioModel usuario) throws SQLException {
        Exception e = usuarioRepository.salvar(usuario);
        if (e == null){
            return "Salvo com Sucesso!";
        }else{
            return e.getMessage();
        }
    }
}
