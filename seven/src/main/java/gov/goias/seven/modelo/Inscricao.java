package gov.goias.seven.modelo;

public class Inscricao {
    private Long id;
    private Evento evento;
    private String cpf;

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

    public String getCPF() {
        return cpf;
    }

    public void setCPF(final String numero) {
        this.cpf = numero;
    }

}