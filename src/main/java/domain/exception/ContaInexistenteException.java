package domain.exception;

public class ContaInexistenteException extends RuntimeException {
    public ContaInexistenteException(String id) {
        super("Conta com id: " + id + " não encontrada no sistema.");
    }
}