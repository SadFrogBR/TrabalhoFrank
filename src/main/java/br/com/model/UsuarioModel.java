package br.com.model;

import javax.persistence.*;

@Entity
@Table(name = "Usuario")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    private String nome;

    private String sexo;

    private String numeroCelular;

    private String email;

    private int numeroIdentificacao;

    public UsuarioModel(String nome, String sexo, String numeroCelular, String email, int numeroIdentificacao) {
        this.nome = nome;
        this.sexo = sexo;
        this.numeroCelular = numeroCelular;
        this.email = email;
        this.numeroIdentificacao = numeroIdentificacao;
    }

    public UsuarioModel() {}

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        if (numeroCelular.matches("\\(\\d{2}\\) \\d{5}-\\d{4}")) {
            this.numeroCelular = numeroCelular;
        } else {
            throw new IllegalArgumentException("Número de celular deve estar no formato (xx) xxxxx-xxxx");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("E-mail deve ser válido");
        }
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
                ", sexo='" + sexo + '\'' +
                ", numeroCelular='" + numeroCelular + '\'' +
                ", email='" + email + '\'' +
                ", numeroIdentificacao=" + numeroIdentificacao +
                '}';
    }
}