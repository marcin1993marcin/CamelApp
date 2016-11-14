
-- -----------------------------------------------------
-- Table `library`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`customer` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(255) NOT NULL,
  `last_name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `status` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `library`.`customer_projects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`customer_projects` (
  `Customer_id` INT(11) NOT NULL,
  `Project_id` INT(11) NOT NULL,
  PRIMARY KEY (`Customer_id`, `Project_id`),
  CONSTRAINT `fk_customer_projects_customer`
    FOREIGN KEY `Customer_id_fk` (`Customer_id`)
    REFERENCES `library`.`customer` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_customer_projects_project`
    FOREIGN KEY `Project_id_fk` (`Project_id`)
    REFERENCES `library`.`project` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;