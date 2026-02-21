package domain;

import java.math.BigDecimal;
import exception.SaldoInsuficienteException;

public class Conta {

    private String id;

    public Conta(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    private BigDecimal saldo = BigDecimal.ZERO;
    public BigDecimal getSaldo() {
        return saldo;
    }

    public void depositar(BigDecimal valor){
        if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0 ){
            throw new IllegalArgumentException("O valor depositado não pode ser R$0,00 ou inferior.");
        }
        saldo = saldo.add(valor);
    }

    public void sacar(BigDecimal valor){
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("O valor de saque não pode ser R$0,00 ou inferior.");
        }

        if (saldo.compareTo(valor) < 0){
            throw new SaldoInsuficienteException(saldo);
        }

        saldo = saldo.subtract(valor);
    }

}
