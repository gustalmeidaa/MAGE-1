package MAGE.mage.dto;

import java.time.LocalDateTime;

public record ManutencaoDTO(
        LocalDateTime data,
        String tipoManutencao,
        String procedimentos,
        Integer idMaquina,
        Integer idFuncionario
) {
}
