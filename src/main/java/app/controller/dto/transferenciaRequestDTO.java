package app.controller.dto;

import java.math.BigDecimal;

public record transferenciaRequestDTO (
        String idOrigem,
        String idDestino,
        BigDecimal valor
)
{}
