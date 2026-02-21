package app.service;

import domain.Conta;
import domain.exception.ContaInexistenteException;
import infra.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferenciaService {

    private final ContaRepository contaRepository;

    public TransferenciaService(ContaRepository contaRepository){
        this.contaRepository = contaRepository;
    }

    @Transactional
    public void realizarTransferencia(String idDestino,String idOrigem, BigDecimal valor){

        Conta origem = contaRepository.findByIdWithLock(idOrigem)
                .orElseThrow(() -> new ContaInexistenteException(idOrigem));

        Conta destino = contaRepository.findByIdWithLock(idDestino)
                .orElseThrow(() -> new ContaInexistenteException(idDestino));

        origem.transferir(destino, valor);
        contaRepository.save(origem);
        contaRepository.save(destino);
    }

}
