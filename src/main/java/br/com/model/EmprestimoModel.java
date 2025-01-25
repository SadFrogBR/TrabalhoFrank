package br.com.model;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "Emprestimo")
public class EmprestimoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmprestimo;

    @ManyToOne
    @JoinColumn(name = "idLivro", nullable = false)
    private LivroModel livro;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private UsuarioModel usuario;

    private Date dataEmprestimo;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucao;

    public EmprestimoModel(LivroModel livro, UsuarioModel usuario, String dataEmprestimo, String dataDevolucaoPrevista) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = parseDate(dataEmprestimo);
        this.dataDevolucaoPrevista = parseDate(dataDevolucaoPrevista);
    }

    public EmprestimoModel() {}

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public LivroModel getLivro() {
        return livro;
    }

    public void setLivro(LivroModel livro) {
        this.livro = livro;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = parseDate(dataEmprestimo);
    }

    public Date getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(String dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = parseDate(dataDevolucaoPrevista);
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = parseDate(dataDevolucao);
    }

    private Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Data deve estar no formato yyyy-MM-dd", e);
        }
    }

    @Override
    public String toString() {
        return "EmprestimoModel{" +
                "idEmprestimo=" + idEmprestimo +
                ", livro=" + livro +
                ", usuario=" + usuario +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucaoPrevista=" + dataDevolucaoPrevista +
                ", dataDevolucao=" + dataDevolucao +
                '}';
    }
}