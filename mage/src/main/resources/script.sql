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
-- Tabela `MAGE`.`funcionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`funcionario` (
  `id_funcionario` INT NOT NULL AUTO_INCREMENT,
  `nome_funcionario` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_funcionario`)
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Tabela `MAGE`.`maquina`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`maquina` (
  `id_maquina` INT  AUTO_INCREMENT NOT NULL,
  `cod_patrimonial` VARCHAR(45) NOT NULL,
  `num_serie` VARCHAR(45) NOT NULL,
  `valor` DOUBLE NOT NULL,
  `id_responsavel` INT,
  `localizacao` VARCHAR(45),
  PRIMARY KEY (`id_maquina`),
  INDEX `id_responsavel_idx` (`id_responsavel` ASC) VISIBLE,
  CONSTRAINT `id_responsavel`
    FOREIGN KEY (`id_responsavel`)
    REFERENCES `MAGE`.`funcionario` (`id_funcionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `MAGE`.`historico_de_manutencoes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`historico_de_manutencoes` (
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
    REFERENCES `MAGE`.`maquina` (`id_maquina`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_funcionario`
    FOREIGN KEY (`id_funcionario`)
    REFERENCES `MAGE`.`funcionario` (`id_funcionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `MAGE`.`registro_de_movimentacoes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`registro_de_movimentacoes` (
  `id_registro_de_movimentacoes` INT NOT NULL,
  `id_responsavel` INT NOT NULL,
  `tipo_de_movimentacao` VARCHAR(45) NOT NULL,
  `origem` VARCHAR(45) NOT NULL,
  `destino` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_registro_de_movimentacoes`),
  INDEX `id_responsavel_idx` (`id_responsavel` ASC) VISIBLE,
  CONSTRAINT `fk_registro_responsavel`
    FOREIGN KEY (`id_responsavel`)
    REFERENCES `MAGE`.`funcionario` (`id_funcionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `MAGE`.`log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`log` (
  `id_log` INT NOT NULL AUTO_INCREMENT,
  `operacao` VARCHAR(45) NOT NULL,
  `dados_antigos` TEXT NOT NULL,
  `dados_novos` TEXT NOT NULL,
  `data_movimentacao` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_log`))
ENGINE = InnoDB;

-- Triggers para a tabela `funcionario`
DELIMITER $$

CREATE TRIGGER log_insert_funcionario
AFTER INSERT ON `MAGE`.`funcionario`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('INSERT', '', CONCAT('id_funcionario: ', NEW.id_funcionario, ', nome_funcionario: ', NEW.nome_funcionario));
END $$

CREATE TRIGGER log_update_funcionario
AFTER UPDATE ON `MAGE`.`funcionario`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('UPDATE',
            CONCAT('id_funcionario: ', OLD.id_funcionario, ', nome_funcionario: ', OLD.nome_funcionario),
            CONCAT('id_funcionario: ', NEW.id_funcionario, ', nome_funcionario: ', NEW.nome_funcionario));
END $$

CREATE TRIGGER log_delete_funcionario
AFTER DELETE ON `MAGE`.`funcionario`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('DELETE', CONCAT('id_funcionario: ', OLD.id_funcionario, ', nome_funcionario: ', OLD.nome_funcionario), '');
END $$

DELIMITER ;

-- Triggers para a tabela `maquina`
DELIMITER $$

CREATE TRIGGER log_insert_maquina
AFTER INSERT ON `MAGE`.`maquina`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('INSERT', '', CONCAT('id_maquina: ', NEW.id_maquina, ', cod_patrimonial: ', NEW.cod_patrimonial, ', num_serie: ', NEW.num_serie, ', valor: ', NEW.valor, ', id_responsavel: ', COALESCE(NEW.id_responsavel, 'NULL')));
END $$

CREATE TRIGGER log_update_maquina
BEFORE UPDATE ON `MAGE`.`maquina`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('UPDATE',
            CONCAT('id_maquina: ', OLD.id_maquina, ', cod_patrimonial: ', OLD.cod_patrimonial, ', num_serie: ', OLD.num_serie, ', valor: ', OLD.valor, ', id_responsavel: ', OLD.id_responsavel),
            CONCAT('id_maquina: ', NEW.id_maquina, ', cod_patrimonial: ', NEW.cod_patrimonial, ', num_serie: ', NEW.num_serie, ', valor: ', NEW.valor, ', id_responsavel: ', NEW.id_responsavel, 'NULL'));
END $$

CREATE TRIGGER log_delete_maquina
BEFORE DELETE ON `MAGE`.`maquina`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('DELETE', CONCAT('id_maquina: ', OLD.id_maquina, ', cod_patrimonial: ', OLD.cod_patrimonial, ', num_serie: ', OLD.num_serie, ', valor: ', OLD.valor, ', id_responsavel: ', IFNULL(OLD.id_responsavel,'NULL')),'');
END $$

DELIMITER ;

-- Triggers para a tabela `historico_de_manutencoes`
DELIMITER $$

CREATE TRIGGER log_insert_historico_manutencao
AFTER INSERT ON `MAGE`.`historico_de_manutencoes`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('INSERT', '', CONCAT('id_historico_de_manutencoes: ', NEW.id_historico_de_manutencoes, ', data: ', NEW.data, ', tipo_de_manutencao: ', NEW.tipo_de_manutencao, ', procedimentos_realizados: ', NEW.procedimentos_realizados, ', id_maquina: ', NEW.id_maquina, ', id_funcionario: ', NEW.id_funcionario));
END $$

CREATE TRIGGER log_update_historico_manutencao
AFTER UPDATE ON `MAGE`.`historico_de_manutencoes`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('UPDATE',
            CONCAT('id_historico_de_manutencoes: ', OLD.id_historico_de_manutencoes, ', data: ', OLD.data, ', tipo_de_manutencao: ', OLD.tipo_de_manutencao, ', procedimentos_realizados: ', OLD.procedimentos_realizados, ', id_maquina: ', OLD.id_maquina, ', id_funcionario: ', OLD.id_funcionario),
            CONCAT('id_historico_de_manutencoes: ', NEW.id_historico_de_manutencoes, ', data: ', NEW.data, ', tipo_de_manutencao: ', NEW.tipo_de_manutencao, ', procedimentos_realizados: ', NEW.procedimentos_realizados, ', id_maquina: ', NEW.id_maquina, ', id_funcionario: ', NEW.id_funcionario));
END $$

CREATE TRIGGER log_delete_historico_manutencao
AFTER DELETE ON `MAGE`.`historico_de_manutencoes`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('DELETE', CONCAT('id_historico_de_manutencoes: ', OLD.id_historico_de_manutencoes, ', data: ', OLD.data, ', tipo_de_manutencao: ', OLD.tipo_de_manutencao, ', procedimentos_realizados: ', OLD.procedimentos_realizados, ', id_maquina: ', OLD.id_maquina, ', id_funcionario: ', OLD.id_funcionario), '');
END $$

DELIMITER ;

-- Triggers para a tabela `registro_de_movimentacoes`
DELIMITER $$

CREATE TRIGGER log_insert_registro_movimentacoes
AFTER INSERT ON `MAGE`.`registro_de_movimentacoes`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('INSERT', '', CONCAT('id_registro_de_movimentacoes: ', NEW.id_registro_de_movimentacoes, ', id_responsavel: ', NEW.id_responsavel, ', tipo_de_movimentacao: ', NEW.tipo_de_movimentacao, ', origem: ', NEW.origem, ', destino: ', NEW.destino));
END $$

CREATE TRIGGER log_update_registro_movimentacoes
AFTER UPDATE ON `MAGE`.`registro_de_movimentacoes`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('UPDATE',
            CONCAT('id_registro_de_movimentacoes: ', OLD.id_registro_de_movimentacoes, ', id_responsavel: ', OLD.id_responsavel, ', tipo_de_movimentacao: ', OLD.tipo_de_movimentacao, ', origem: ', OLD.origem, ', destino: ', OLD.destino),
            CONCAT('id_registro_de_movimentacoes: ', NEW.id_registro_de_movimentacoes, ', id_responsavel: ', NEW.id_responsavel, ', tipo_de_movimentacao: ', NEW.tipo_de_movimentacao, ', origem: ', NEW.origem, ', destino: ', NEW.destino));
END $$

CREATE TRIGGER log_delete_registro_movimentacoes
AFTER DELETE ON `MAGE`.`registro_de_movimentacoes`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('DELETE', CONCAT('id_registro_de_movimentacoes: ', OLD.id_registro_de_movimentacoes, ', id_responsavel: ', OLD.id_responsavel, ', tipo_de_movimentacao: ', OLD.tipo_de_movimentacao, ', origem: ', OLD.origem, ', destino: ', OLD.destino), '');
END $$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;