package MAGE.mage.dto;

import MAGE.mage.model.Maquina;

import java.util.List;

public record FuncionarioDTO(
        String nomeFuncionario,
        String nomeSetor,
        List<Maquina> maquinas
) {
}
