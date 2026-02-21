package app.controller.dto;

import java.math.BigDecimal;

public record TransferenciaRequestDTO(
        String idOrigem,
        String idDestino,
        BigDecimal valor
) {}