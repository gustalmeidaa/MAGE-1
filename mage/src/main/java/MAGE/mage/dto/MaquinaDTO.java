package MAGE.mage.dto;

import java.math.BigDecimal;

public record MaquinaDTO(
        String codPatrimonial,
        String numSerie,
        BigDecimal valor,
        Integer idResponsavel,
        String localizacao
) {
}
