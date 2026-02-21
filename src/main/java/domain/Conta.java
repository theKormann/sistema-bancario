package domain;

import java.math.BigDecimal;
import domain.exception.SaldoInsuficienteException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "conta")
public class Conta {

    @Id
    private String id;

    private String contaOrigem;
    private String contaDestino;

    protected Conta() {
    }

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

    public void transferir(Conta contaDestino, BigDecimal valor){
        if(contaDestino == null){
            throw new IllegalArgumentException("A conta de destino não é valida.");
        }

        this.sacar(valor);
        contaDestino.depositar(valor);
    }
}