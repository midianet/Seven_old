package gov.goias.seven.modelo;

public class Inscricao {
    private Long id;
    private Evento evento;
    private Aluno aluno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(final Evento evento) {
        this.evento = evento;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(final Aluno aluno) {
        this.aluno = aluno;
    }

}