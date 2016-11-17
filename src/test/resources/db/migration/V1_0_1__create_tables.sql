-- -----------------------------------------------------
-- Table `position`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `position` (
  `id`       INT(11)     NOT NULL AUTO_INCREMENT,
  `position` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project` (
  `id`           INT(11)      NOT NULL AUTO_INCREMENT,
  `project_name` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `first_name`  VARCHAR(255) NOT NULL,
  `last_name`   VARCHAR(255) NOT NULL,
  `email`       VARCHAR(45)  NOT NULL,
  `status`      VARCHAR(255) NOT NULL,
  `Position_id` INT(11)      NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Position_has_Users_Positions_idx` (`Position_id` ASC),
  CONSTRAINT `fk_Position_has_Users_Positions`
  FOREIGN KEY (`Position_id`)
  REFERENCES `position` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `salary`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salary` (
  `id`           INT(11) NOT NULL AUTO_INCREMENT,
  `Users_id`     INT(11) NULL     DEFAULT NULL,
  `Positions_id` INT(11) NULL     DEFAULT NULL,
  `monthly`      INT(11) NOT NULL,
  `per_hour`     INT(11) NOT NULL,
  `date_from`    DATE    NOT NULL,
  `date_to`      DATE    NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Users_has_salary_idx` (`Users_id` ASC),
  INDEX `fk_Position_has_salary_idx` (`Positions_id` ASC),
  CONSTRAINT `fk_Position_has_Salaries_Positions`
  FOREIGN KEY (`Positions_id`)
  REFERENCES `position` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Users_has_Salaries_Positions`
  FOREIGN KEY (`Users_id`)
  REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `user_projects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_projects` (
  `Users_id`    INT(11) NOT NULL,
  `Projects_id` INT(11) NOT NULL,
  PRIMARY KEY (`Users_id`, `Projects_id`),
  INDEX `fk_Users_has_Projects_Projects1_idx` (`Projects_id` ASC),
  INDEX `fk_Users_has_Projects_Users_idx` (`Users_id` ASC),
  CONSTRAINT `fk_Users_has_Projects_Projects1`
  FOREIGN KEY (`Projects_id`)
  REFERENCES `project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Users_has_Projects_Users`
  FOREIGN KEY (`Users_id`)
  REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Set Records for test
-- -----------------------------------------------------

INSERT INTO .`position`
(`position`)
VALUES
  ("Test position");

INSERT INTO .`position`
    (`id`, `position`)
VALUES
    (1,"Test position 2");


INSERT INTO `libraryfortest`.`user`
(`first_name`, `last_name`, `email`, `status`, `Position_id`)
VALUES
  ("Test name", "Test last name", "Email test", "Retired", 13),
  ("Jan", "Kowalski", "jk@j.k", "Retired", 13);
INSERT INTO `libraryfortest`.`project`
(`project_name`)
VALUES
  ("Project Name");

INSERT INTO `libraryfortest`.`salary`
    (`Users_id`, `Positions_id`, `monthly`, `per_hour`, `date_from`, `date_to`)
VALUES
    (3, 13, 100000, 500, '2010-01-15', '2012-06-02');