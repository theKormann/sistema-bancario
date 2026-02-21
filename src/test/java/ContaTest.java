import domain.Conta;
import exception.SaldoInsuficienteException;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    @Test
    void deveRealizarSaqueComSucessoQuandoSaldoForSuficiente() {
        // Arrange (Preparação: Criamos a conta e botamos R$ 100)
        Conta conta = new Conta("12345");
        conta.depositar(new BigDecimal("100.00"));

        // Act (Ação: Tentamos sacar R$ 50)
        conta.sacar(new BigDecimal("50.00"));

        // Assert (Verificação: O saldo final deve ser R$ 50)
        assertEquals(new BigDecimal("50.00"), conta.getSaldo());
    }

    @Test
    void deveLancarExcecaoQuandoSaqueForMaiorQueSaldo() {
        // Arrange (Preparação: Criamos a conta e botamos R$ 50)
        Conta conta = new Conta("12345");
        conta.depositar(new BigDecimal("50.00"));

        // Act & Assert (Ação e Verificação: Tentar sacar R$ 100 deve estourar um erro)
        assertThrows(SaldoInsuficienteException.class, () -> {
            conta.sacar(new BigDecimal("100.00"));
        });

        // O saldo deve permanecer intacto após a falha
        assertEquals(new BigDecimal("50.00"), conta.getSaldo());
    }

    @Test
    void deveLancarExcecaoQuandoDepositoForNegativo() {
        // Arrange
        Conta conta = new Conta("12345");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            conta.depositar(new BigDecimal("-50.00"));
        });
    }

    @Test
    void deveLancarExcecaoQuandoDepositoForNulo() {
        // Arrange
        Conta conta = new Conta("12345");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            conta.depositar(null);
        });
    }

    @Test
    void deveTransferirValorComSucessoEntreDuasContas() {
        // Arrange (Preparação: Criamos duas contas e colocamos R$ 100 na conta de origem)
        Conta contaOrigem = new Conta("123");
        contaOrigem.depositar(new BigDecimal("100.00"));

        Conta contaDestino = new Conta("456");

        // Act (Ação: Transferimos R$ 40 da origem para o destino)
        contaOrigem.transferir(contaDestino, new BigDecimal("40.00"));

        // Assert (Verificação: A origem deve ficar com 60 e o destino com 40)
        assertEquals(new BigDecimal("60.00"), contaOrigem.getSaldo());
        assertEquals(new BigDecimal("40.00"), contaDestino.getSaldo());
    }
}