package br.com.view;

import br.com.controller.UsuarioController;
import br.com.model.UsuarioModel;

import javax.swing.*;
import java.sql.SQLException;

public class UsuarioView {

    public void apresentaMenuUsuario() throws SQLException {
        UsuarioController usuarioController = new UsuarioController();
        int opcao;

        do {
            opcao = menu();
            switch (opcao) {
                case 1:
                    UsuarioModel novoUsuario = coletaDados(null);
                    if (novoUsuario != null) {
                        String resultado = usuarioController.salvarUsuario(novoUsuario);
                        JOptionPane.showMessageDialog(null, resultado);
                    } else {
                        JOptionPane.showMessageDialog(null, "Não foi possível salvar o usuário.");
                    }
                    break;
                case 2:
                    try {
                        int idConsulta = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o ID do usuário a ser consultado:"));
                        UsuarioModel usuarioConsultado = usuarioController.consultarUsuarioPorId(idConsulta);
                        if (usuarioConsultado != null) {
                            exibeUsuario(usuarioConsultado);
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Entrada inválida. O ID deve ser um número.");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Erro ao consultar usuário: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        int idRemover = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o ID do usuário a ser removido:"));
                        String resultadoRemocao = usuarioController.removerUsuario(idRemover);
                        JOptionPane.showMessageDialog(null, resultadoRemocao);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Entrada inválida. O ID deve ser um número.");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Erro ao remover usuário: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        int idEditar = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o ID do usuário a ser editado:"));
                        UsuarioModel usuarioExistente = usuarioController.consultarUsuarioPorId(idEditar);
                        if (usuarioExistente != null) {
                            UsuarioModel usuarioEditado = coletaDados(usuarioExistente);
                            if (usuarioEditado != null) {
                                usuarioExistente.setNome(usuarioEditado.getNome());
                                usuarioExistente.setSexo(usuarioEditado.getSexo());
                                usuarioExistente.setNumeroCelular(usuarioEditado.getNumeroCelular());
                                usuarioExistente.setEmail(usuarioEditado.getEmail());
                                usuarioExistente.setNumeroIdentificacao(usuarioEditado.getNumeroIdentificacao());
                                String resultadoEdicao = usuarioController.atualizarUsuario(usuarioExistente);
                                JOptionPane.showMessageDialog(null, resultadoEdicao);
                            } else {
                                JOptionPane.showMessageDialog(null, "Edição cancelada.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Entrada inválida. O ID deve ser um número.");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Erro ao editar usuário: " + e.getMessage());
                    }
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Voltando ao menu principal.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, tente novamente.");
            }
        } while (opcao != 0);
    }

    public int menu() {
        String menu = "\nDigite o número correspondente:\n" +
                "1 - Cadastrar Usuário\n" +
                "2 - Consultar Usuário por ID\n" +
                "3 - Remover Usuário\n" +
                "4 - Editar Usuário\n" +
                "0 - Voltar ao menu principal";

        String opt = JOptionPane.showInputDialog(null, menu);

        try {
            return Integer.parseInt(opt);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, insira um número válido.");
            return -1;
        }
    }

    public UsuarioModel coletaDados(UsuarioModel usuarioExistente) {
        UsuarioModel usuario = (usuarioExistente == null) ? new UsuarioModel() : usuarioExistente;

        String nome = JOptionPane.showInputDialog(null, "Digite o nome do usuário:", usuario.getNome());
        if (nome != null) usuario.setNome(nome);

        String sexo = JOptionPane.showInputDialog(null, "Digite o sexo do usuário (M/F):", usuario.getSexo());
        if (sexo != null) usuario.setSexo(sexo);

        try {
            String numeroCelular = JOptionPane.showInputDialog(null, "Digite o número de celular no formato (xx) xxxxx-xxxx:", usuario.getNumeroCelular());
            if (numeroCelular != null) usuario.setNumeroCelular(numeroCelular);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }

        try {
            String email = JOptionPane.showInputDialog(null, "Digite o e-mail do usuário:", usuario.getEmail());
            if (email != null) usuario.setEmail(email);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }

        try {
            String numeroIdentificacao = JOptionPane.showInputDialog(null, "Digite o número de identificação do usuário:", String.valueOf(usuario.getNumeroIdentificacao()));
            if (numeroIdentificacao != null) usuario.setNumeroIdentificacao(Integer.parseInt(numeroIdentificacao));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número de identificação inválido. Deve ser um número.");
            return null;
        }

        return usuario;
    }

    public void exibeUsuario(UsuarioModel usuario) {
        if (usuario != null) {
            JOptionPane.showMessageDialog(null, "Detalhes do Usuário:\n" +
                    "Nome: " + usuario.getNome() + "\n" +
                    "Sexo: " + usuario.getSexo() + "\n" +
                    "Número de Celular: " + usuario.getNumeroCelular() + "\n" +
                    "E-mail: " + usuario.getEmail() + "\n" +
                    "Número de Identificação: " + usuario.getNumeroIdentificacao());
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao exibir informações do usuário.");
        }
    }
}
