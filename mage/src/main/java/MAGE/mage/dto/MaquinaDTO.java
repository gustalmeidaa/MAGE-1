package MAGE.mage.dto;

import MAGE.mage.model.StatusMaquina;

import java.math.BigDecimal;

public record MaquinaDTO(
        String codPatrimonial,
        Integer idMaquina,
        String numSerie,
        BigDecimal valor,
        Integer idResponsavel,
        String localizacao,
        StatusMaquina status,
        String descricao
) {
}
