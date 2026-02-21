package domain.exception;

import java.math.BigDecimal;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(BigDecimal saldo){
        super("O seu saldo atual é " + saldo + " Não é possível realizar.");
    }
}
