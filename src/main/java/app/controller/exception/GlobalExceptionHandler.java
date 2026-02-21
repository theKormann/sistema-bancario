package app.controller.exception;

import domain.exception.ContaInexistenteException;
import domain.exception.SaldoInsuficienteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErroRespostaDTO(String erro, String detalhe, String dataHora) {}

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<ErroRespostaDTO> handleSaldoInsuficiente(SaldoInsuficienteException ex) {
        ErroRespostaDTO erro = new ErroRespostaDTO(
                "Regra de Negócio Violada",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
    }

    @ExceptionHandler(ContaInexistenteException.class)
    public ResponseEntity<ErroRespostaDTO> handleContaInexistente(ContaInexistenteException ex) {
        ErroRespostaDTO erro = new ErroRespostaDTO(
                "Recurso Não Encontrado",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroRespostaDTO> handleIllegalArgument(IllegalArgumentException ex) {
        ErroRespostaDTO erro = new ErroRespostaDTO(
                "Dados Inválidos",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}