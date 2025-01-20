package br.com.model;

import javax.persistence.*;
import java.sql.Date;

//gustavo é flameguista
@Entity
@Table(name = "Usuario")
public class EmprestimoModel {
    @Id
    @GeneratedValue
    private int idEmprestimo;

    @ManyToOne
    @JoinColumn(name = "idLivro", nullable = false)
    private LivroModel livro;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private UsuarioModel usuario;

    private Date dataEmprestimo;
    private Date dataDevolucaoPrevista;

    public EmprestimoModel(int idEmprestimo, LivroModel livro, UsuarioModel usuario, Date dataEmprestimo, Date dataDevolucaoPrevista) {
        this.idEmprestimo = idEmprestimo;
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public EmprestimoModel() {}

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public LivroModel getLivro() {
        return livro;
    }

    public void setLivro(LivroModel livro) {
        this.livro = livro;
    }

    public Date getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    @Override
    public String toString() {
        return "EmprestimoModel{" +
                "idEmprestimo=" + idEmprestimo +
                ", livro=" + livro +
                ", usuario=" + usuario +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucaoPrevista=" + dataDevolucaoPrevista +
                '}';
    }
}
