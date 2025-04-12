-- MySQL Script gerado pelo MySQL Workbench
-- Sex Mar 21 10:24:32 2025
-- Modelo: New Model    Versão: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema MAGE
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema MAGE
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `MAGE` DEFAULT CHARACTER SET utf8 ;
USE `MAGE` ;

-- -----------------------------------------------------
-- Tabela `MAGE`.`Cargo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`Cargo` (
  `id_cargo` INT NOT NULL,
  `nome_cargo` VARCHAR(45) NOT NULL,
  `salario` INT NOT NULL,
  `setor` VARCHAR(45) NOT NULL,
  `is_administrador` TINYINT NOT NULL,
  PRIMARY KEY (`id_cargo`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `MAGE`.`Funcionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`Funcionario` (
  `id_funcionario` INT NOT NULL,
  `nome_funcionario` VARCHAR(45) NOT NULL,
  `id_cargo` INT NOT NULL,
  PRIMARY KEY (`id_funcionario`),
  INDEX `id_cargo_idx` (`id_cargo` ASC) VISIBLE,
  CONSTRAINT `id_cargo`
    FOREIGN KEY (`id_cargo`)
    REFERENCES `MAGE`.`Cargo` (`id_cargo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `MAGE`.`Maquina`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`Maquina` (
  `id_maquina` BIGINT NOT NULL,
  `cod_patrimonial` INT NOT NULL,
  `num_serie` INT NOT NULL,
  `valor` DOUBLE NOT NULL,
  `id_responsavel` INT NOT NULL,
  PRIMARY KEY (`id_maquina`),
  INDEX `id_responsavel_idx` (`id_responsavel` ASC) VISIBLE,
  CONSTRAINT `id_responsavel`
    FOREIGN KEY (`id_responsavel`)
    REFERENCES `MAGE`.`Funcionario` (`id_funcionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `MAGE`.`Historico_de_Manutencoes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`Historico_de_Manutencoes` (
  `id_historico_de_manutencoes` INT NOT NULL,
  `data` DATETIME NOT NULL,
  `tipo_de_manutencao` VARCHAR(45) NOT NULL,
  `procedimentos_realizados` VARCHAR(45) NOT NULL,
  `id_maquina` INT NOT NULL,
  `id_funcionario` INT NOT NULL,
  PRIMARY KEY (`id_historico_de_manutencoes`),
  INDEX `id_maquina_idx` (`id_maquina` ASC) VISIBLE,
  INDEX `id_funcionario_idx` (`id_funcionario` ASC) VISIBLE,
  CONSTRAINT `id_maquina`
    FOREIGN KEY (`id_maquina`)
    REFERENCES `MAGE`.`Maquina` (`id_maquina`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_funcionario`
    FOREIGN KEY (`id_funcionario`)
    REFERENCES `MAGE`.`Funcionario` (`id_funcionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `MAGE`.`Registro_de_Movimentacoes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`Registro_de_Movimentacoes` (
  `id_registro_de_movimentacoes` INT NOT NULL,
  `id_responsavel` INT NOT NULL,
  `tipo_de_movimentacao` VARCHAR(45) NOT NULL,
  `origem` VARCHAR(45) NOT NULL,
  `destino` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_registro_de_movimentacoes`),
  INDEX `id_responsavel_idx` (`id_responsavel` ASC) VISIBLE,
  CONSTRAINT `id_responsavel`
    FOREIGN KEY (`id_responsavel`)
    REFERENCES `MAGE`.`Funcionario` (`id_funcionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `MAGE`.`Log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`Log` (
  `id_log` INT NOT NULL AUTO_INCREMENT,
  `operacao` VARCHAR(45) NOT NULL,
  `dados_antigos` TEXT NOT NULL,
  `dados_novos` TEXT NOT NULL,
  `data_movimentacao` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_log`))
ENGINE = InnoDB;

-- Triggers para a tabela `Cargo`
DELIMITER $$

CREATE TRIGGER log_insert_cargo
AFTER INSERT ON `MAGE`.`Cargo`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`Log` (operacao, dados_antigos, dados_novos)
    VALUES ('INSERT', '', CONCAT('id_cargo: ', NEW.id_cargo, ', nome_cargo: ', NEW.nome_cargo, ', salario: ', NEW.salario, ', setor: ', NEW.setor, ', is_administrador: ', NEW.is_administrador));
END $$

CREATE TRIGGER log_update_cargo
AFTER UPDATE ON `MAGE`.`Cargo`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`Log` (operacao, dados_antigos, dados_novos)
    VALUES ('UPDATE',
            CONCAT('id_cargo: ', OLD.id_cargo, ', nome_cargo: ', OLD.nome_cargo, ', salario: ', OLD.salario, ', setor: ', OLD.setor, ', is_administrador: ', OLD.is_administrador),
            CONCAT('id_cargo: ', NEW.id_cargo, ', nome_cargo: ', NEW.nome_cargo, ', salario: ', NEW.salario, ', setor: ', NEW.setor, ', is_administrador: ', NEW.is_administrador));
END $$

CREATE TRIGGER log_delete_cargo
AFTER DELETE ON `MAGE`.`Cargo`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`Log` (operacao, dados_antigos, dados_novos)
    VALUES ('DELETE', CONCAT('id_cargo: ', OLD.id_cargo, ', nome_cargo: ', OLD.nome_cargo, ', salario: ', OLD.salario, ', setor: ', OLD.setor, ', is_administrador: ', OLD.is_administrador), '');
END $$

DELIMITER ;

-- Triggers para a tabela `Funcionario`
DELIMITER $$

CREATE TRIGGER log_insert_funcionario
AFTER INSERT ON `MAGE`.`Funcionario`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`Log` (operacao, dados_antigos, dados_novos)
    VALUES ('INSERT', '', CONCAT('id_funcionario: ', NEW.id_funcionario, ', nome_funcionario: ', NEW.nome_funcionario, ', id_cargo: ', NEW.id_cargo));
END $$

CREATE TRIGGER log_update_funcionario
AFTER UPDATE ON `MAGE`.`Funcionario`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`Log` (operacao, dados_antigos, dados_novos)
    VALUES ('UPDATE',
            CONCAT('id_funcionario: ', OLD.id_funcionario, ', nome_funcionario: ', OLD.nome_funcionario, ', id_cargo: ', OLD.id_cargo),
            CONCAT('id_funcionario: ', NEW.id_funcionario, ', nome_funcionario: ', NEW.nome_funcionario, ', id_cargo: ', NEW.id_cargo));
END $$

CREATE TRIGGER log_delete_funcionario
AFTER DELETE ON `MAGE`.`Funcionario`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`Log` (operacao, dados_antigos, dados_novos)
    VALUES ('DELETE', CONCAT('id_funcionario: ', OLD.id_funcionario, ', nome_funcionario: ', OLD.nome_funcionario, ', id_cargo: ', OLD.id_cargo), '');
END $$

DELIMITER ;

-- Triggers para a tabela `Maquina`
DELIMITER $$

CREATE TRIGGER log_insert_maquina
AFTER INSERT ON `MAGE`.`Maquina`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`Log` (operacao, dados_antigos, dados_novos)
    VALUES ('INSERT', '', CONCAT('id_maquina: ', NEW.id_maquina, ', cod_patrimonial: ', NEW.cod_patrimonial, ', num_serie: ', NEW.num_serie, ', valor: ', NEW.valor, ', id_responsavel: ', NEW.id_responsavel));
END $$

CREATE TRIGGER log_update_maquina
AFTER UPDATE ON `MAGE`.`Maquina`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`Log` (operacao, dados_antigos, dados_novos)
    VALUES ('UPDATE',
            CONCAT('id_maquina: ', OLD.id_maquina, ', cod_patrimonial: ', OLD.cod_patrimonial, ', num_serie: ', OLD.num_serie, ', valor: ', OLD.valor, ', id_responsavel: ', OLD.id_responsavel),
            CONCAT('id_maquina: ', NEW.id_maquina, ', cod_patrimonial: ', NEW.cod_patrimonial, ', num_serie: ', NEW.num_serie, ', valor: ', NEW.valor, ', id_responsavel: ', NEW.id_responsavel));
END $$

CREATE TRIGGER log_delete_maquina
AFTER DELETE ON `MAGE`.`Maquina`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`Log` (operacao, dados_antigos, dados_novos)
    VALUES ('DELETE', CONCAT('id_maquina: ', OLD.id_maquina, ', cod_patrimonial: ', OLD.cod_patrimonial, ', num_serie: ', OLD.num_serie, ', valor: ', OLD.valor, ', id_responsavel: ', OLD.id_responsavel), '');
END $$

DELIMITER ;

-- Triggers para a tabela `Historico_de_Manutencoes`
DELIMITER $$

CREATE TRIGGER log_insert_historico_manutencao
AFTER INSERT ON `MAGE`.`Historico_de_Manutencoes`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`Log` (operacao, dados_antigos, dados_novos)
    VALUES ('INSERT', '', CONCAT('id_historico_de_manutencoes: ', NEW.id_historico_de_manutencoes, ', data: ', NEW.data, ', tipo_de_manutencao: ', NEW.tipo_de_manutencao, ', procedimentos_realizados: ', NEW.procedimentos_realizados, ', id_maquina: ', NEW.id_maquina, ', id_funcionario: ', NEW.id_funcionario));
END $$

CREATE TRIGGER log_update_historico_manutencao
AFTER UPDATE ON `MAGE`.`Historico_de_Manutencoes`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`Log` (operacao, dados_antigos, dados_novos)
    VALUES ('UPDATE',
            CONCAT('id_historico_de_manutencoes: ', OLD.id_historico_de_manutencoes, ', data: ', OLD.data, ', tipo_de_manutencao: ', OLD.tipo_de_manutencao, ', procedimentos_realizados: ', OLD.procedimentos_realizados, ', id_maquina: ', OLD.id_maquina, ', id_funcionario: ', OLD.id_funcionario),
            CONCAT('id_historico_de_manutencoes: ', NEW.id_historico_de_manutencoes, ', data: ', NEW.data, ', tipo_de_manutencao: ', NEW.tipo_de_manutencao, ', procedimentos_realizados: ', NEW.procedimentos_realizados, ', id_maquina: ', NEW.id_maquina, ', id_funcionario: ', NEW.id_funcionario));
END $$

CREATE TRIGGER log_delete_historico_manutencao
AFTER DELETE ON `MAGE`.`Historico_de_Manutencoes`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`Log` (operacao, dados_antigos, dados_novos)
    VALUES ('DELETE', CONCAT('id_historico_de_manutencoes: ', OLD.id_historico_de_manutencoes, ', data: ', OLD.data, ', tipo_de_manutencao: ', OLD.tipo_de_manutencao, ', procedimentos_realizados: ', OLD.procedimentos_realizados, ', id_maquina: ', OLD.id_maquina, ', id_funcionario: ', OLD.id_funcionario), '');
END $$

DELIMITER ;

-- Triggers para a tabela `Registro_de_Movimentacoes`
DELIMITER $$

CREATE TRIGGER log_insert_registro_movimentacoes
AFTER INSERT ON `MAGE`.`Registro_de_Movimentacoes`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`Log` (operacao, dados_antigos, dados_novos)
    VALUES ('INSERT', '', CONCAT('id_registro_de_movimentacoes: ', NEW.id_registro_de_movimentacoes, ', id_responsavel: ', NEW.id_responsavel, ', tipo_de_movimentacao: ', NEW.tipo_de_movimentacao, ', origem: ', NEW.origem, ', destino: ', NEW.destino));
END $$

CREATE TRIGGER log_update_registro_movimentacoes
AFTER UPDATE ON `MAGE`.`Registro_de_Movimentacoes`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`Log` (operacao, dados_antigos, dados_novos)
    VALUES ('UPDATE',
            CONCAT('id_registro_de_movimentacoes: ', OLD.id_registro_de_movimentacoes, ', id_responsavel: ', OLD.id_responsavel, ', tipo_de_movimentacao: ', OLD.tipo_de_movimentacao, ', origem: ', OLD.origem, ', destino: ', OLD.destino),
            CONCAT('id_registro_de_movimentacoes: ', NEW.id_registro_de_movimentacoes, ', id_responsavel: ', NEW.id_responsavel, ', tipo_de_movimentacao: ', NEW.tipo_de_movimentacao, ', origem: ', NEW.origem, ', destino: ', NEW.destino));
END $$

CREATE TRIGGER log_delete_registro_movimentacoes
AFTER DELETE ON `MAGE`.`Registro_de_Movimentacoes`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`Log` (operacao, dados_antigos, dados_novos)
    VALUES ('DELETE', CONCAT('id_registro_de_movimentacoes: ', OLD.id_registro_de_movimentacoes, ', id_responsavel: ', OLD.id_responsavel, ', tipo_de_movimentacao: ', OLD.tipo_de_movimentacao, ', origem: ', OLD.origem, ', destino: ', OLD.destino), '');
END $$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;