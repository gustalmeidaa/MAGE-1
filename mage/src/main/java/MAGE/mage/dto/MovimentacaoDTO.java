package MAGE.mage.dto;

import java.time.LocalDateTime;

public record MovimentacaoDTO(
        Integer idMaquinaMovimentada,
        String tipo,
        String origem,
        String destino,
        LocalDateTime data
) {
}
