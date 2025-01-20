package br.com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Usuario")
public class UsuarioModel {
    @Id
    @GeneratedValue
    private int idUsuario;
    private String nome;
    private String email;
    private int numeroIdentificacao;

    public UsuarioModel(String nome, String email, int numeroIdentificacao) {
        this.nome = nome;
        this.email = email;
        this.numeroIdentificacao = numeroIdentificacao;
    }

    public UsuarioModel(){}

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumeroIdentificacao() {
        return numeroIdentificacao;
    }

    public void setNumeroIdentificacao(int numeroIdentificacao) {
        this.numeroIdentificacao = numeroIdentificacao;
    }

    @Override
    public String toString() {
        return "UsuarioModel{" +
                "idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", numeroIdentificacao=" + numeroIdentificacao +
                '}';
    }
}
