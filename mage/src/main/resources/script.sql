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
  `descricao` VARCHAR(255),
  status ENUM('ATIVA', 'INATIVA', 'EM_MANUTENCAO') NOT NULL DEFAULT 'ATIVA',
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
  `login_usuario` VARCHAR(45) NULL,
  PRIMARY KEY (`id_log`),
  CONSTRAINT `fk_usuario`
    FOREIGN KEY (`login_usuario`)
    REFERENCES `MAGE`.`administrador` (`login`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

-- primeiro admin
INSERT INTO `MAGE`.`administrador` (login, senha)
VALUES ('admin0', '$2a$10$mplL5EDiuJtA0MO5EbmtZu7dIIHv88mIrLS0EKO79yLFy2GcMoV7a');
-- {
--       "login":"admin0",
--       "senha":"senhaSegura123"
--   }



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;