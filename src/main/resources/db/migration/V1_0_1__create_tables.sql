--
-- Table structure for table `position`
--
CREATE TABLE IF NOT EXISTS `position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

--
-- Table structure for table `user`
--
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(45) NOT NULL,
  `status` varchar(255) NOT NULL,
  `Position_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Position_has_Users_Positions_idx` (`Position_id`),
    CONSTRAINT `fk_Position_has_Users_Positions`
    FOREIGN KEY (`Position_id`) REFERENCES `position` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Table structure for table `project`
--
CREATE TABLE IF NOT EXISTS `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_projects`
--
CREATE TABLE IF NOT EXISTS `user_projects` (
  `Users_id` int(11) NOT NULL,
  `Projects_id` int(11) NOT NULL,
  PRIMARY KEY (`Users_id`,`Projects_id`),
  KEY `fk_Users_has_Projects_Projects1_idx` (`Projects_id`),
  KEY `fk_Users_has_Projects_Users_idx` (`Users_id`),
  CONSTRAINT `fk_Users_has_Projects_Projects1`
    FOREIGN KEY (`Projects_id`) REFERENCES `project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Users_has_Projects_Users`
    FOREIGN KEY (`Users_id`) REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `salary`
--
CREATE TABLE IF NOT EXISTS `salary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Users_id` int(11),
  `Positions_id` int(11),
  `monthly` int(11) NOT NULL,
  `per_hour` int(11) NOT NULL,
  `date_from` date NOT NULL,
  `date_to` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Users_has_salary_idx` (`Users_id`),
  KEY `fk_Position_has_salary_idx` (`Positions_id`),
  CONSTRAINT `fk_Position_has_Salaries_Positions`
    FOREIGN KEY (`Positions_id`) REFERENCES `position` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Users_has_Salaries_Positions`
    FOREIGN KEY (`Users_id`) REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

