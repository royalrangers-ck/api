--
-- Table structure for table `authority`
--
CREATE TABLE `authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `subscriber`
--
CREATE TABLE `subscriber` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `country`
--
CREATE TABLE `country` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `city`
--
CREATE TABLE `city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_CITY_COUNTRY_ID` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `platoon`
--
CREATE TABLE `platoon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `history` varchar(255) DEFAULT NULL,
  `meet_time` datetime DEFAULT NULL,
  `logo_url` varchar(255) DEFAULT NULL,
  `city_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_PLATOON_CITY_ID` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `section`
--
CREATE TABLE `section` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `user_age_group` int(11) DEFAULT NULL,
  `platoon_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_SECTION_PLATOON_ID` FOREIGN KEY (`platoon_id`) REFERENCES `platoon` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user`
--
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `birth_date` bigint(20) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `telephone_number` varchar(255) DEFAULT NULL,
  `user_age_group` int(11) DEFAULT NULL,
  `user_rank` int(11) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  `city_id` bigint(20) DEFAULT NULL,
  `platoon_id` bigint(20) DEFAULT NULL,
  `section_id` bigint(20) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `approved` bit(1) DEFAULT NULL,
  `confirmed` bit(1) DEFAULT NULL,
  `rejected` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `last_password_reset_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_USER_COUNTRY_ID` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_USER_CITY_ID` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `FK_USER_PLATOON_ID` FOREIGN KEY (`platoon_id`) REFERENCES `platoon` (`id`),
  CONSTRAINT `FK_USER_SECTION_ID` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_authority`
--
CREATE TABLE `user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_id`),
  CONSTRAINT `FK_USER_AUTHORITY_ID` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
  CONSTRAINT `FK_AUTHORITY_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB;

--
-- Table structure for table `twelve_year_achievement`
--
CREATE TABLE `twelve_year_achievement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1200) NOT NULL,
  `user_age_group` int(11) DEFAULT NULL,
  `logo_url` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `three_year_achievement`
--
CREATE TABLE `three_year_achievement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1200) NOT NULL,
  `user_age_group` int(11) DEFAULT NULL,
  `logo_url` varchar(255) DEFAULT NULL,
  `twelve_year_achievement_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKivl8h306off48jw3mca58g79t` FOREIGN KEY (`twelve_year_achievement_id`) REFERENCES `twelve_year_achievement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `year_achievement`
--
CREATE TABLE `year_achievement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1200) NOT NULL,
  `user_age_group` int(11) DEFAULT NULL,
  `logo_url` varchar(255) DEFAULT NULL,
  `three_year_achievement_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK5wd9lpe2xwqean3lmoca3qye5` FOREIGN KEY (`three_year_achievement_id`) REFERENCES `three_year_achievement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `quarter_achievement`
--
CREATE TABLE `quarter_achievement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1200) NOT NULL,
  `user_age_group` int(11) DEFAULT NULL,
  `logo_url` varchar(255) DEFAULT NULL,
  `year_achievement_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKeybmly5fjivmxdtjbd49t2vdr` FOREIGN KEY (`year_achievement_id`) REFERENCES `year_achievement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `reward`
--
CREATE TABLE `reward` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1200) NOT NULL,
  `user_age_group` int(11) DEFAULT NULL,
  `reward_mark` int(11) DEFAULT NULL,
  `reward_type` int(11) DEFAULT NULL,
  `logo_url` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `test`
--
CREATE TABLE `test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `short_description` varchar(255) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `test_type` int(11) DEFAULT NULL,
  `logo_url` varchar(255) DEFAULT NULL,
  `quarter_achievement_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKqri9lej5vnkd7hchjuudlqke4` FOREIGN KEY (`quarter_achievement_id`) REFERENCES `quarter_achievement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `task`
--
CREATE TABLE `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `test_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKojhuohbslrwalcmrql2wvad8m` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `test_user_age_groups`
--
CREATE TABLE `test_user_age_groups` (
  `test_id` bigint(20) NOT NULL,
  `age_category` varchar(255) NOT NULL,
  CONSTRAINT `FKcudascoliks9fnv375l7fqxy3` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_twelve_year_achievement`
--
CREATE TABLE `user_twelve_year_achievement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `achievement_state` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `twelve_year_achievement_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKlo66fids9umqxi9a78lc48ftt` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK8lejciqedl23mgxeu9w2jy8n3` FOREIGN KEY (`twelve_year_achievement_id`) REFERENCES `twelve_year_achievement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_three_year_achievement`
--
CREATE TABLE `user_three_year_achievement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `achievement_state` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `three_year_achievement_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK7r01wa21vp9ufnijqy9eeqp3i` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKtif87r72kpubd3gv6x81hx4rd` FOREIGN KEY (`three_year_achievement_id`) REFERENCES `three_year_achievement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_year_achievement`
--
CREATE TABLE `user_year_achievement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `achievement_state` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `year_achievement_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKtes4181fy1ic1otqf9vf99w9p` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKiheiix877aiw4867fq43ykc23` FOREIGN KEY (`year_achievement_id`) REFERENCES `year_achievement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_quarter_achievement`
--
CREATE TABLE `user_quarter_achievement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `achievement_state` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `user_age_group` int(11) DEFAULT NULL,
  `quarter_achievement_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKdo3wdl0e5ymckg6x9lpk4k2si` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKe4smnfd1p3h88r57vnpc52ekf` FOREIGN KEY (`quarter_achievement_id`) REFERENCES `quarter_achievement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_reward`
--
CREATE TABLE `user_reward` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `achievement_state` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `reward_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKaipr0um0a8q2w6kie0rapkjkv` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKdl3mrw4v03cqj3g08g8h44x30` FOREIGN KEY (`reward_id`) REFERENCES `reward` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_task`
--
CREATE TABLE `user_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `achievement_state` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `task_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKr2jik008e3jx6r1fal5e9aq1n` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKvs34bjkmpbk2e54qlrol3ilt` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_test`
--
CREATE TABLE `user_test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `achievement_state` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `test_id` bigint(20) DEFAULT NULL,
  `user_quarter_achievement_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK54073ir8kqlk4cfahr56begn4` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKt0r12g1hnb4e9f0j12yn4ky7g` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`),
  CONSTRAINT `FKj71y3to5b51rn0htxq1bwe0e9` FOREIGN KEY (`user_quarter_achievement_id`) REFERENCES `user_quarter_achievement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

