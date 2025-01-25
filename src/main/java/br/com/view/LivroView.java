package br.com.view;

import br.com.controller.LivroController;
import br.com.model.LivroModel;

import javax.swing.*;
import java.sql.SQLException;

public class LivroView {

    public void apresentaMenuLivro() throws SQLException {
        LivroController livroController = new LivroController();
        int opcao;

        do {
            opcao = menu();
            switch (opcao) {
                case 1:
                    LivroModel novoLivro = coletaDados(null);
                    if (novoLivro != null) {
                        String resultado = livroController.salvarLivro(novoLivro);
                        JOptionPane.showMessageDialog(null, resultado);
                    } else {
                        JOptionPane.showMessageDialog(null, "Não foi possível salvar o livro.");
                    }
                    break;
                case 2:
                    try {
                        int idConsulta = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o ID do livro a ser consultado:"));
                        LivroModel livroConsultado = livroController.consultarLivroPorId(idConsulta);
                        if (livroConsultado != null) {
                            exibeLivro(livroConsultado);
                        } else {
                            JOptionPane.showMessageDialog(null, "Livro não encontrado.");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Entrada inválida. O ID deve ser um número.");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Erro ao consultar livro: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        int idRemover = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o ID do livro a ser removido:"));
                        String resultadoRemocao = livroController.removerLivro(idRemover);
                        JOptionPane.showMessageDialog(null, resultadoRemocao);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Entrada inválida. O ID deve ser um número.");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Erro ao remover livro: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        int idEditar = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o ID do livro a ser editado:"));
                        LivroModel livroExistente = livroController.consultarLivroPorId(idEditar);
                        if (livroExistente != null) {
                            LivroModel livroEditado = coletaDados(livroExistente);
                            if (livroEditado != null) {
                                livroExistente.setTitulo(livroEditado.getTitulo());
                                livroExistente.setTema(livroEditado.getTema());
                                livroExistente.setAutor(livroEditado.getAutor());
                                livroExistente.setIsbn(livroEditado.getIsbn());
                                livroExistente.setDataPublicacao(livroEditado.getDataPublicacao());
                                livroExistente.setQtdDisponivel(livroEditado.getQtdDisponivel());
                                String resultadoEdicao = livroController.atualizarLivro(livroExistente);
                                JOptionPane.showMessageDialog(null, resultadoEdicao);
                            } else {
                                JOptionPane.showMessageDialog(null, "Edição cancelada.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Livro não encontrado.");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Entrada inválida. O ID deve ser um número.");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Erro ao editar livro: " + e.getMessage());
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
                "1 - Cadastrar Livro\n" +
                "2 - Consultar Livro por ID\n" +
                "3 - Remover Livro\n" +
                "4 - Editar Livro\n" +
                "0 - Voltar ao menu principal";

        String opt = JOptionPane.showInputDialog(null, menu);

        try {
            return Integer.parseInt(opt);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, insira um número válido.");
            return -1;
        }
    }

    public LivroModel coletaDados(LivroModel livroExistente) {
        LivroModel livro = (livroExistente == null) ? new LivroModel() : livroExistente;

        String titulo = JOptionPane.showInputDialog(null, "Digite o título do livro:", livro.getTitulo());
        if (titulo != null) livro.setTitulo(titulo);

        String tema = JOptionPane.showInputDialog(null, "Digite o tema do livro:", livro.getTema());
        if (tema != null) livro.setTema(tema);

        String autor = JOptionPane.showInputDialog(null, "Digite o nome do autor do livro:", livro.getAutor());
        if (autor != null) livro.setAutor(autor);

        try {
            String isbn = JOptionPane.showInputDialog(null, "Digite o ISBN do livro:", String.valueOf(livro.getIsbn()));
            if (isbn != null) livro.setIsbn(Integer.parseInt(isbn));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ISBN inválido. Deve ser um número.");
            return null;
        }

        String dataPublicacao = JOptionPane.showInputDialog(null, "Digite a data de publicação (yyyy-MM-dd):", livro.getDataPublicacao());
        try {
            if (dataPublicacao != null) livro.setDataPublicacao(dataPublicacao);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }

        try {
            String qtdDisponivel = JOptionPane.showInputDialog(null, "Digite a quantidade disponível:", String.valueOf(livro.getQtdDisponivel()));
            if (qtdDisponivel != null) livro.setQtdDisponivel(Integer.parseInt(qtdDisponivel));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida. Deve ser um número.");
            return null;
        }

        return livro;
    }

    public void exibeLivro(LivroModel livro) {
        if (livro != null) {
            JOptionPane.showMessageDialog(null, "Detalhes do Livro:\n" +
                    "Título: " + livro.getTitulo() + "\n" +
                    "Tema: " + livro.getTema() + "\n" +
                    "Autor: " + livro.getAutor() + "\n" +
                    "ISBN: " + livro.getIsbn() + "\n" +
                    "Data de Publicação: " + livro.getDataPublicacao() + "\n" +
                    "Quantidade Disponível: " + livro.getQtdDisponivel());
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao exibir informações do livro.");
        }
    }
}