-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mealreview
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mealreview
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mealreview` DEFAULT CHARACTER SET utf8 ;
USE `mealreview` ;

-- -----------------------------------------------------
-- Table `mealreview`.`Account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mealreview`.`Account` (
  `idAccount` INT NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(45) NOT NULL,
  `AccountType` TINYINT(2) NOT NULL,
  `Email` VARCHAR(75) NOT NULL,
  `Password` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`idAccount`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mealreview`.`Restaurant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mealreview`.`Restaurant` (
  `idRestaurant` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Address` VARCHAR(45) NOT NULL,
  `PhoneNum` VARCHAR(45) NOT NULL,
  `Website` VARCHAR(83) NULL COMMENT '75 characters plus 8 more to pad “https://“, so you do not lose writable space or something. Not a database breaker but hey it’s here',
  PRIMARY KEY (`idRestaurant`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mealreview`.`Review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mealreview`.`Review` (
  `idReview` INT NOT NULL AUTO_INCREMENT,
  `ReviewBody` VARCHAR(45) NOT NULL,
  `idUser` INT NOT NULL,
  `idRestaurant` INT NOT NULL,
  `DateCreated` DATETIME NOT NULL,
  PRIMARY KEY (`idReview`),
  INDEX `idAuthor_idx` (`idUser` ASC),
  INDEX `idRestaurant_idx` (`idRestaurant` ASC),
  CONSTRAINT `idAuthor`
    FOREIGN KEY (`idAccount`)
    REFERENCES `mealreview`.`Account` (`idAccount`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idRestaurant`
    FOREIGN KEY (`idRestaurant`)
    REFERENCES `mealreview`.`Restaurant` (`idRestaurant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mealreview`.`Vote`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mealreview`.`Vote` (
  `idVote` INT NOT NULL AUTO_INCREMENT,
  `VoteValue` TINYINT(1) NOT NULL,
  `idVoterAccount` INT NOT NULL,
  `idVotedRestaurant` INT NOT NULL,
  PRIMARY KEY (`idVote`),
  INDEX `idRestaurant_idx` (`idVotedRestaurant` ASC),
  INDEX `idVoterAccount_idx` (`idVoterAccount` ASC),
  CONSTRAINT `idVoterAccount`
    FOREIGN KEY (`idVoterAccount`)
    REFERENCES `mealreview`.`Account` (`idAccount`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idVotedRestaurant`
    FOREIGN KEY (`idVotedRestaurant`)
    REFERENCES `mealreview`.`Restaurant` (`idRestaurant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
