package gov.goias.seven.modelo;

import java.util.Date;
import java.util.List;

public class Turma {
    private Long id;
    private Evento evento;
    private Date inicio;
    private Date fim;
    private List<Inscricao> inscricoes;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(final Evento evento) {
        this.evento = evento;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(final Date data) {
        this.inicio = data;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(final Date data) {
        this.fim = data;
    }

    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public void setInscricoes(final List<Inscricao> lista) {
        this.inscricoes = lista;
    }
}