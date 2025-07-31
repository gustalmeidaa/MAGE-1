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
-- Tabela `MAGE`.`setor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`setor` (
  `id_setor` INT NOT NULL AUTO_INCREMENT,
  `nome_setor` VARCHAR(45) NOT NULL UNIQUE,
  PRIMARY KEY (`id_setor`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `MAGE`.`funcionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`funcionario` (
  `id_funcionario` INT NOT NULL AUTO_INCREMENT,
  `nome_funcionario` VARCHAR(45) NOT NULL,
  `id_setor` INT NOT NULL,
  PRIMARY KEY (`id_funcionario`),
  INDEX `id_setor_idx` (`id_setor` ASC) VISIBLE,
  CONSTRAINT `id_setor`
    FOREIGN KEY (`id_setor`)
    REFERENCES `MAGE`.`setor` (`id_setor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `MAGE`.`administrador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`administrador` (
  `login` VARCHAR(45) NOT NULL UNIQUE,
  `senha` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `MAGE`.`maquina`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`maquina` (
  `id_maquina` INT AUTO_INCREMENT NOT NULL,
  `cod_patrimonial` VARCHAR(45) NOT NULL UNIQUE,
  `num_serie` VARCHAR(45) NOT NULL UNIQUE,
  `valor` DOUBLE NOT NULL,
  `id_responsavel` INT,
  `localizacao` VARCHAR(45),
  `status` ENUM('Ativa', 'Inativa', 'Em manutenção') NOT NULL DEFAULT 'Ativa',
  PRIMARY KEY (`id_maquina`),
  INDEX `id_responsavel_idx` (`id_responsavel` ASC) VISIBLE,
  CONSTRAINT `id_responsavel`
    FOREIGN KEY (`id_responsavel`)
    REFERENCES `MAGE`.`funcionario` (`id_funcionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `MAGE`.`historico_de_manutencoes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`historico_de_manutencoes` (
  `id_historico_de_manutencoes` INT NOT NULL AUTO_INCREMENT,
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
-- Tabela `MAGE`.`manutencoes_agendadas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`manutencoes_agendadas` (
  `id_manutencao_agendada` INT NOT NULL AUTO_INCREMENT,
  `data_agendada` DATETIME NOT NULL,
  `tipo_de_manutencao` VARCHAR(45) NOT NULL,
  `procedimentos` VARCHAR(255) NOT NULL,
  `id_maquina` INT,
  PRIMARY KEY (`id_manutencao_agendada`),
  INDEX `id_maquina_idx` (`id_maquina` ASC) VISIBLE,
  CONSTRAINT `fk_manutencao_maquina`
    FOREIGN KEY (`id_maquina`)
    REFERENCES `MAGE`.`maquina` (`id_maquina`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Tabela `MAGE`.`registro_de_movimentacoes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MAGE`.`registro_de_movimentacoes` (
  `id_registro_de_movimentacoes` INT NOT NULL AUTO_INCREMENT,
  `id_maquina_movimentada` INT NOT NULL,
  `tipo_de_movimentacao` VARCHAR(45) NOT NULL,
  `origem` VARCHAR(45) NOT NULL,
  `destino` VARCHAR(45) NOT NULL,
  `data` DATETIME NOT NULL,
  PRIMARY KEY (`id_registro_de_movimentacoes`),
  INDEX `id_responsavel_idx` (`id_maquina_movimentada` ASC) VISIBLE,
  CONSTRAINT `fk_maquina_movimentada`
    FOREIGN KEY (`id_maquina_movimentada`)
    REFERENCES `MAGE`.`maquina` (`id_maquina`)
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

-- Triggers para a tabela `setor`
DELIMITER $

CREATE TRIGGER log_insert_setor
AFTER INSERT ON `MAGE`.`setor`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('INSERT', '', CONCAT('id_setor: ', NEW.id_setor, ', nome_setor: ', NEW.nome_setor));
END $

CREATE TRIGGER log_update_setor
AFTER UPDATE ON `MAGE`.`setor`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('UPDATE',
            CONCAT('id_setor: ', OLD.id_setor, ', nome_setor: ', OLD.nome_setor),
            CONCAT('id_setor: ', NEW.id_setor, ', nome_setor: ', NEW.nome_setor));
END $

CREATE TRIGGER log_delete_setor
AFTER DELETE ON `MAGE`.`setor`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('DELETE', CONCAT('id_setor: ', OLD.id_setor, ', nome_setor: ', OLD.nome_setor), '');
END $

DELIMITER ;

-- Triggers para a tabela `funcionario`
DELIMITER $

CREATE TRIGGER log_insert_funcionario
AFTER INSERT ON `MAGE`.`funcionario`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('INSERT', '', CONCAT('id_funcionario: ', NEW.id_funcionario, ', nome_funcionario: ', NEW.nome_funcionario));
END $

CREATE TRIGGER log_update_funcionario
AFTER UPDATE ON `MAGE`.`funcionario`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('UPDATE',
            CONCAT('id_funcionario: ', OLD.id_funcionario, ', nome_funcionario: ', OLD.nome_funcionario),
            CONCAT('id_funcionario: ', NEW.id_funcionario, ', nome_funcionario: ', NEW.nome_funcionario));
END $

CREATE TRIGGER log_delete_funcionario
AFTER DELETE ON `MAGE`.`funcionario`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('DELETE', CONCAT('id_funcionario: ', OLD.id_funcionario, ', nome_funcionario: ', OLD.nome_funcionario), '');
END $

DELIMITER ;
DELIMITER $

-- Trigger para inserção na tabela `administrador`
CREATE TRIGGER log_insert_administrador
AFTER INSERT ON `MAGE`.`administrador`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('INSERT', '', CONCAT('id_adm: ', NEW.id_adm, ', login: ', NEW.login));
END $

-- Trigger para atualização na tabela `administrador`
CREATE TRIGGER log_update_administrador
AFTER UPDATE ON `MAGE`.`administrador`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('UPDATE',
            CONCAT('id_adm: ', OLD.id_adm, ', login: ', OLD.login),
            CONCAT('id_adm: ', NEW.id_adm, ', login: ', NEW.login));
END $

-- Trigger para exclusão na tabela `administrador`
CREATE TRIGGER log_delete_administrador
AFTER DELETE ON `MAGE`.`administrador`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('DELETE', CONCAT('id_adm: ', OLD.id_adm, ', login: ', OLD.login), '');
END $

DELIMITER ;

-- Triggers para a tabela `maquina`
DELIMITER $$

CREATE TRIGGER log_insert_maquina
AFTER INSERT ON `MAGE`.`maquina`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('INSERT', '', CONCAT('id_maquina: ', NEW.id_maquina, ', cod_patrimonial: ', NEW.cod_patrimonial, ', num_serie: ', NEW.num_serie, ', valor: ', NEW.valor, ', id_responsavel: ', COALESCE(NEW.id_responsavel, 'NULL'), ', status: ', NEW.status));
END $$

CREATE TRIGGER log_update_maquina
BEFORE UPDATE ON `MAGE`.`maquina`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('UPDATE',
            CONCAT('id_maquina: ', COALESCE(OLD.id_maquina, 'NULL'), ', cod_patrimonial: ', COALESCE(OLD.cod_patrimonial, 'NULL'), ', num_serie: ', COALESCE(OLD.num_serie, 'NULL'), ', valor: ', COALESCE(OLD.valor, 'NULL'), ', id_responsavel: ', COALESCE(OLD.id_responsavel, 'NULL'), ', status: ', COALESCE(OLD.status, 'NULL')),
            CONCAT('id_maquina: ', COALESCE(NEW.id_maquina, 'NULL'), ', cod_patrimonial: ', COALESCE(NEW.cod_patrimonial, 'NULL'), ', num_serie: ', COALESCE(NEW.num_serie, 'NULL'), ', valor: ', COALESCE(NEW.valor, 'NULL'), ', id_responsavel: ', COALESCE(NEW.id_responsavel, 'NULL'), ', status: ', COALESCE(NEW.status, 'NULL')));
END $$

CREATE TRIGGER log_delete_maquina
BEFORE DELETE ON `MAGE`.`maquina`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('DELETE', CONCAT('id_maquina: ', COALESCE(OLD.id_maquina, 'NULL'), ', cod_patrimonial: ', COALESCE(OLD.cod_patrimonial, 'NULL'), ', num_serie: ', COALESCE(OLD.num_serie, 'NULL'), ', valor: ', COALESCE(OLD.valor, 'NULL'), ', id_responsavel: ', COALESCE(OLD.id_responsavel, 'NULL'), ', status: ', COALESCE(OLD.status, 'NULL')), '');
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

DELIMITER $

-- Trigger para inserção na tabela `manutencoes_agendadas`
CREATE TRIGGER log_insert_manutencao_agendada
AFTER INSERT ON `MAGE`.`manutencoes_agendadas`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('INSERT', '', CONCAT('id_manutencao_agendada: ', NEW.id_manutencao_agendada, ', data_agendada: ', NEW.data_agendada, ', tipo_de_manutencao: ', NEW.tipo_de_manutencao, ', procedimentos: ', NEW.procedimentos, ', id_maquina: ', COALESCE(NEW.id_maquina, 'NULL')));
END $

-- Trigger para atualização na tabela `manutencoes_agendadas`
CREATE TRIGGER log_update_manutencao_agendada
AFTER UPDATE ON `MAGE`.`manutencoes_agendadas`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('UPDATE',
            CONCAT('id_manutencao_agendada: ', OLD.id_manutencao_agendada, ', data_agendada: ', OLD.data_agendada, ', tipo_de_manutencao: ', OLD.tipo_de_manutencao, ', procedimentos: ', OLD.procedimentos, ', id_maquina: ', COALESCE(OLD.id_maquina, 'NULL')),
            CONCAT('id_manutencao_agendada: ', NEW.id_manutencao_agendada, ', data_agendada: ', NEW.data_agendada, ', tipo_de_manutencao: ', NEW.tipo_de_manutencao, ', procedimentos: ', NEW.procedimentos, ', id_maquina: ', COALESCE(NEW.id_maquina, 'NULL')));
END $

-- Trigger para exclusão na tabela `manutencoes_agendadas`
CREATE TRIGGER log_delete_manutencao_agendada
AFTER DELETE ON `MAGE`.`manutencoes_agendadas`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('DELETE', CONCAT('id_manutencao_agendada: ', OLD.id_manutencao_agendada, ', data_agendada: ', OLD.data_agendada, ', tipo_de_manutencao: ', OLD.tipo_de_manutencao, ', procedimentos: ', OLD.procedimentos, ', id_maquina: ', COALESCE(OLD.id_maquina, 'NULL')), '');
END $

DELIMITER ;

-- Triggers para a tabela `registro_de_movimentacoes`
DELIMITER $$

CREATE TRIGGER log_insert_registro_movimentacoes
AFTER INSERT ON `MAGE`.`registro_de_movimentacoes`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('INSERT', '', CONCAT('id_registro_de_movimentacoes: ', NEW.id_registro_de_movimentacoes, ', id_maquina_movimentada: ', NEW.id_maquina_movimentada, ', tipo_de_movimentacao: ', NEW.tipo_de_movimentacao, ', origem: ', NEW.origem, ', destino: ', NEW.destino));
END $$

CREATE TRIGGER log_update_registro_movimentacoes
AFTER UPDATE ON `MAGE`.`registro_de_movimentacoes`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('UPDATE',
            CONCAT('id_registro_de_movimentacoes: ', OLD.id_registro_de_movimentacoes, ', id_maquina_movimentada: ', OLD.id_maquina_movimentada, ', tipo_de_movimentacao: ', OLD.tipo_de_movimentacao, ', origem: ', OLD.origem, ', destino: ', OLD.destino),
            CONCAT('id_registro_de_movimentacoes: ', NEW.id_registro_de_movimentacoes, ', id_maquina_movimentada: ', NEW.id_maquina_movimentada, ', tipo_de_movimentacao: ', NEW.tipo_de_movimentacao, ', origem: ', NEW.origem, ', destino: ', NEW.destino));
END $$

CREATE TRIGGER log_delete_registro_movimentacoes
AFTER DELETE ON `MAGE`.`registro_de_movimentacoes`
FOR EACH ROW
BEGIN
    INSERT INTO `MAGE`.`log` (operacao, dados_antigos, dados_novos)
    VALUES ('DELETE', CONCAT('id_registro_de_movimentacoes: ', OLD.id_registro_de_movimentacoes, ', id_maquina_movimentada: ', OLD.id_maquina_movimentada, ', tipo_de_movimentacao: ', OLD.tipo_de_movimentacao, ', origem: ', OLD.origem, ', destino: ', OLD.destino), '');
END $$

DELIMITER ;

-- primeiro admin
INSERT INTO `MAGE`.`administrador` (login, senha)
VALUES ('admin0', '$2a$10$xkksBclOMbTQqW90p5/YSuAkT8F711QR21oBLXA3Quj4BYAIO4ryS');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;