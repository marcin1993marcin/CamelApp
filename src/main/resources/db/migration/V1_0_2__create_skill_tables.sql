-- -----------------------------------------------------
-- Table `skill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `skill` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `parent_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_skill_skill1_idx` (`parent_id` ASC),
  CONSTRAINT `fk_skill_skill1`
    FOREIGN KEY (`parent_id`)
    REFERENCES `skill` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_skill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_skill` (
  `skill_id` INT NOT NULL,
  `user_id` INT(11) NOT NULL,
  `level` INT NOT NULL,
  `note` LONGTEXT NULL,
  PRIMARY KEY (`skill_id`, `user_id`),
  INDEX `fk_skill_has_user_user1_idx` (`user_id` ASC),
  INDEX `fk_skill_has_user_skill1_idx` (`skill_id` ASC),
  CONSTRAINT `fk_skill_has_user_skill1`
    FOREIGN KEY (`skill_id`)
    REFERENCES `skill` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_skill_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;