package gov.goias.seven.modelo;

import java.util.List;

public class Evento {
    private Long id;
    private String descricao;
    private List<Turma> turmas;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(final List<Turma> lista) {
        this.turmas = lista;
    }

}