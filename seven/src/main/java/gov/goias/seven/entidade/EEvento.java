package gov.goias.seven.entidade;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_evento")
public class EEvento {

    @Id
    @Column(name = "EVEN_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "EVEN_DESCRICAO")
    private String descricao;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "TB_TURMA")
//    @JoinColumn(name = "EVEN_ID")
//    private List<ETurma> turmas;

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

//    public List<ETurma> getTurmas() {
//        return turmas;
//    }
//
//    public void setTurmas(final List<ETurma> lista) {
//        this.turmas = lista;
//    }

}