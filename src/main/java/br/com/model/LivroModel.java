package br.com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Livro")
public class LivroModel {

    @Id
    @GeneratedValue
    private int idLivro;

    private String titulo;

    private String tema;

    private String autor;

    private int isbn;

    private String dataPublicacao;

    private int qtdDisponivel;

    public LivroModel(String titulo, String tema, String autor, int isbn, String dataPublicacao, int qtdDisponivel) {
        this.titulo = titulo;
        this.tema = tema;
        this.autor = autor;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.qtdDisponivel = qtdDisponivel;
    }

    public LivroModel() {}

    public int getIdLivro() {
        return idLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        if (dataPublicacao.matches("\\d{4}-\\d{2}-\\d{2}")) {
            this.dataPublicacao = dataPublicacao;
        } else {
            throw new IllegalArgumentException("Data deve estar no formato yyyy-MM-dd");
        }
    }

    public int getQtdDisponivel() {
        return qtdDisponivel;
    }

    public void setQtdDisponivel(int qtdDisponivel) {
        this.qtdDisponivel = qtdDisponivel;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "idLivro=" + idLivro +
                ", titulo='" + titulo + '\'' +
                ", tema='" + tema + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn=" + isbn +
                ", dataPublicacao='" + dataPublicacao + '\'' +
                ", qtdDisponivel=" + qtdDisponivel +
                '}';
    }
}