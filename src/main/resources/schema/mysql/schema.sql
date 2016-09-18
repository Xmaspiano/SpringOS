DROP TABLE IF EXISTS NOTE_USER;
CREATE TABLE NOTE_USER(
  id BIGINT auto_increment,
	login_name VARCHAR(64) NOT NULL UNIQUE,
	name VARCHAR(64) NOT NULL,
	plain_password VARCHAR(255) not null,
	password VARCHAR(255) NOT NULL,
	salt VARCHAR(64) NOT NULL,
	register_date TIMESTAMP NOT NULL,
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS NOTE_DEPARTMENT;
CREATE TABLE NOTE_DEPARTMENT(
id BIGINT auto_increment,
dept_name VARCHAR(64) NOT NULL,
PRIMARY KEY (id)
);

DROP TABLE IF EXISTS NOTE_BLOG;
CREATE TABLE NOTE_BLOG(
id BIGINT auto_increment,
title VARCHAR(128) NOT null,
subhead VARCHAR(256) ,
content VARCHAR(1024),
user_id BIGINT not null,
issue_date TIMESTAMP not null,
PRIMARY KEY (id)
);

DROP TABLE IF EXISTS NOTE_notice;
CREATE TABLE NOTE_NOTICE(
id BIGINT auto_increment,
title VARCHAR(128) NOT null,
subhead VARCHAR(256),
content VARCHAR(1024),
user_id BIGINT not null,
issue_dept_id BIGINT not null,
issue_date TIMESTAMP not null,
PRIMARY KEY (id)
);

DROP TABLE IF EXISTS NOTE_SESSION_TAKEN;
CREATE TABLE `NOTE_SESSION_TAKEN` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `taken` varchar(36) COLLATE utf8_bin NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `creatdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`taken`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;
