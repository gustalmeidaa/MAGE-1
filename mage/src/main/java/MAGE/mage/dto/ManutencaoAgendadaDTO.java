package MAGE.mage.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ManutencaoAgendadaDTO(
        LocalDateTime dataAgendada,
        String tipoManutencao,
        String procedimentos,
        Integer idMaquina,
        BigDecimal custoManutencao
) {
}
