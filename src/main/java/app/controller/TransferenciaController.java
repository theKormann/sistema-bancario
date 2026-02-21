package app.controller;

import app.controller.dto.TransferenciaRequestDTO;
import app.service.TransferenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transferencia")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    public TransferenciaController(TransferenciaService transferenciaService){
        this.transferenciaService = transferenciaService;
    }

    @PostMapping
    public ResponseEntity<String> transferir(@RequestBody TransferenciaRequestDTO dto){
        transferenciaService.realizarTransferencia(
                dto.idOrigem(),
                dto.idDestino(),
                dto.valor()
        );
        return ResponseEntity.ok("Transferência realizada com sucesso.");
    }
}