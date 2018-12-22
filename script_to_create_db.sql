-- MySQL Workbench Synchronization
-- Generated: 2018-11-16 18:42
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Vlad

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `gym-v1` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;

CREATE TABLE IF NOT EXISTS `gym-v1`.`clients` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `middle_name` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(15) NOT NULL,
  `gender` ENUM('m', 'w') NOT NULL,
  `status` ENUM('in', 'out') NOT NULL,
  `comments` TEXT(200) NOT NULL,
  `birthday` DATE NOT NULL,
  `cart_number` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE TABLE IF NOT EXISTS `gym-v1`.`subscriptions` (
  `id` INT(10) UNSIGNED NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `guest_visits` INT(10) UNSIGNED NOT NULL,
  `day_count` INT(10) UNSIGNED NOT NULL,
  `training_count` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE TABLE IF NOT EXISTS `gym-v1`.`card` (
  `clients_id` INT(10) UNSIGNED NOT NULL,
  `subscriptions_id` INT(10) UNSIGNED NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `trainy_count` INT(10) UNSIGNED NOT NULL,
  `guest_count` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`clients_id`, `subscriptions_id`),
  INDEX `fk_clients_has_subscriptions_subscriptions1_idx` (`subscriptions_id` ASC),
  INDEX `fk_clients_has_subscriptions_clients_idx` (`clients_id` ASC),
  CONSTRAINT `fk_clients_has_subscriptions_clients`
    FOREIGN KEY (`clients_id`)
    REFERENCES `gym-v1`.`clients` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_clients_has_subscriptions_subscriptions1`
    FOREIGN KEY (`subscriptions_id`)
    REFERENCES `gym-v1`.`subscriptions` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE TABLE IF NOT EXISTS `gym-v1`.`statistic` (
  `id` INT(10) UNSIGNED NOT NULL,
  `data` DATE NOT NULL,
  `period` ENUM('1', '2', '3') NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE TABLE IF NOT EXISTS `gym-v1`.`trainy_history` (
  `clients_id` INT(10) UNSIGNED NOT NULL,
  `subscriptions_id` INT(10) UNSIGNED NOT NULL,
  `date_start` DATE NOT NULL,
  `date_end` DATE NOT NULL,
  PRIMARY KEY (`clients_id`, `subscriptions_id`),
  INDEX `fk_trainy_history_subscriptions1_idx` (`subscriptions_id` ASC),
  CONSTRAINT `fk_trainy_history_clients1`
    FOREIGN KEY (`clients_id`)
    REFERENCES `gym-v1`.`clients` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trainy_history_subscriptions1`
    FOREIGN KEY (`subscriptions_id`)
    REFERENCES `gym-v1`.`subscriptions` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
