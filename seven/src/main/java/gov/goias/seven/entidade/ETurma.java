package gov.goias.seven.entidade;

import gov.goias.seven.modelo.Evento;
import gov.goias.seven.modelo.Inscricao;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TB_TURMA")
public class ETurma {

    @Id
    @Column(name = "TURM_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "TURM_INICIO")
    private Date inicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "TURM_FIM")
    private Date fim;

    //private List<Inscricao> inscricoes;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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

//    public List<Inscricao> getInscricoes() {
//        return inscricoes;
//    }
//
//    public void setInscricoes(final List<Inscricao> lista) {
//        this.inscricoes = lista;
//    }

    @Override
    public boolean equals(final Object obj) {
        boolean retorno = false;
        if(obj != null && obj instanceof ETurma){
            retorno = this.getId().longValue() == ((ETurma) obj).getId().longValue();
        }
        return retorno;
    }

}