package MAGE.mage.dto;

public record MovimentacaoDTO(
        Integer idMaquinaMovimentada,
        String tipo,
        String origem,
        String destino
) {
}
