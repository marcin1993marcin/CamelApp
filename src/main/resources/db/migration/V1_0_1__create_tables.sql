
-- -----------------------------------------------------
-- Table `library`.`project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`project` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `project_name` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `library`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(255) NOT NULL,
  `last_name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `status` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `library`.`user_projects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`user_projects` (
  `Users_id` INT(11) NOT NULL,
  `Projects_id` INT(11) NOT NULL,
  PRIMARY KEY (`Users_id`, `Projects_id`),
  INDEX `fk_Users_has_Projects_Projects1_idx` (`Projects_id` ASC),
  INDEX `fk_Users_has_Projects_Users_idx` (`Users_id` ASC),
  CONSTRAINT `fk_Users_has_Projects_Projects1`
    FOREIGN KEY (`Projects_id`)
    REFERENCES `library`.`project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Users_has_Projects_Users`
    FOREIGN KEY (`Users_id`)
    REFERENCES `library`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
