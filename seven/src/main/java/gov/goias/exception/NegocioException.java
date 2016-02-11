package gov.goias.exception;

public class NegocioException extends RuntimeException {

    public NegocioException(final String mensagem) {
        super(mensagem);
    }

    public NegocioException(final String mensagem,final Throwable causa) {
        super(mensagem, causa);
    }

    public NegocioException(final Throwable causa) {
        super(causa);
    }

}