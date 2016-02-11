package gov.goias.exception;

public class  InfraException extends RuntimeException {

    public InfraException(final String mensagem) {
        super(mensagem);
    }

    public InfraException(final String mensagem,final Throwable causa) {
        super(mensagem, causa);
    }

    public InfraException(final Throwable causa) {
        super(causa);
    }

}