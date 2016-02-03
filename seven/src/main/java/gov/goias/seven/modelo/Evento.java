package gov.goias.seven.modelo;

import java.util.Date;

public class Evento {

    private Long id;
    private String descricao;
    private Date dataInicio;
    private Date dataFim;

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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(final Date data) {
        this.dataInicio = data;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(final Date data) {
        this.dataFim = data;
    }

}