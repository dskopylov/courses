-- Скрипт сгенерирован Devart dbForge Studio for MySQL, Версия 4.50.315.1
-- Дата: 14.05.2013 22:45:08
-- Версия сервера: 5.5.30-1.1
-- Версия клиента: 4.1

-- 
-- Отключение внешних ключей
-- 
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

-- 
-- Установка кодировки, с использованием которой клиент будет посылать запросы на сервер
--
SET NAMES 'utf8';

-- 
-- Установка базы данных по умолчанию
--
USE coursesdev;

--
-- Описание для таблицы courses
--
DROP TABLE IF EXISTS courses;
CREATE TABLE courses (
  id INT(11) NOT NULL AUTO_INCREMENT,
  number TEXT DEFAULT NULL,
  name TEXT DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 14
AVG_ROW_LENGTH = 5461
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы groups
--
DROP TABLE IF EXISTS groups;
CREATE TABLE groups (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name TEXT NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 3
AVG_ROW_LENGTH = 8192
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы pages
--
DROP TABLE IF EXISTS pages;
CREATE TABLE pages (
  id INT(11) NOT NULL AUTO_INCREMENT,
  course_id INT(11) NOT NULL,
  type TEXT NOT NULL,
  short_name TEXT NOT NULL,
  short_name_eng TEXT NOT NULL,
  content TEXT DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX FK_pages_courses_id (course_id),
  CONSTRAINT FK_pages_courses_id FOREIGN KEY (course_id)
    REFERENCES courses(id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 24
AVG_ROW_LENGTH = 862
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы users
--
DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id INT(11) NOT NULL AUTO_INCREMENT,
  login TEXT NOT NULL,
  `password` TEXT DEFAULT NULL,
  name TEXT DEFAULT NULL,
  email TEXT DEFAULT NULL,
  group_id INT(11) DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX FK_users_groups_id (group_id),
  CONSTRAINT FK_users_groups_id FOREIGN KEY (group_id)
    REFERENCES groups(id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 2
AVG_ROW_LENGTH = 16384
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы page_hist
--
DROP TABLE IF EXISTS page_hist;
CREATE TABLE page_hist (
  id INT(11) NOT NULL AUTO_INCREMENT,
  date_time DATETIME NOT NULL,
  user_id INT(11) NOT NULL,
  diff TEXT NOT NULL,
  version INT(11) NOT NULL,
  page_id INT(11) NOT NULL,
  PRIMARY KEY (id),
  INDEX FK_page_hist_pages_id (page_id),
  CONSTRAINT FK_page_hist_pages_id FOREIGN KEY (page_id)
    REFERENCES pages(id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;

-- 
-- Вывод данных для таблицы courses
--
INSERT INTO courses VALUES 
  (1, 'DLC0001', 'Тестовый курс от ЦДО НИУ ИТМО'),
  (12, 'DLC0002', 'Тестовый 2'),
  (13, '1', '1');

-- 
-- Вывод данных для таблицы groups
--
INSERT INTO groups VALUES 
  (1, 'Admins'),
  (2, 'Users');

-- 
-- Вывод данных для таблицы pages
--
INSERT INTO pages VALUES 
  (1, 1, 'home', 'Главная', 'cdo_test', 'Добро пожаловать на Тестовый курс от ЦДО НИУ ИТМО\r\n2013 г.'),
  (2, 1, 'program', 'Программа', 'programma_kursa', 'Программа курса'),
  (3, 1, 'literature', 'Литература', '', ''),
  (4, 1, 'exercise', 'Упражнения', '', ''),
  (5, 1, 'training', 'Практикумы', '', ''),
  (6, 1, 'infoResources', 'Информационные ресурсы', '', ''),
  (7, 1, 'lection1', 'Лекция 1', '', ' '),
  (12, 12, 'home', 'Главная', '', ''),
  (13, 12, 'program', 'Программа курса', '', ''),
  (14, 12, 'literature', 'Литература', '', ''),
  (15, 12, 'exercise', 'Упражнения', '', ''),
  (16, 12, 'training', 'Практикумы', '', ''),
  (17, 12, 'infoResources', 'Информационные ресурсы', '', ''),
  (18, 13, 'home', 'Главная', '', ''),
  (19, 13, 'program', 'Программа курса', '', ''),
  (20, 13, 'literature', 'Литература', '', ''),
  (21, 13, 'exercise', 'Упражнения', '', ''),
  (22, 13, 'training', 'Практикумы', '', ''),
  (23, 13, 'infoResources', 'Информационные ресурсы', '', '');

-- 
-- Вывод данных для таблицы users
--
INSERT INTO users VALUES 
  (1, 'root', '21232f297a57a5a743894a0e4a801fc3', 'Администратор', 'dima@cde.ifmo.ru', 1);

-- 
-- Вывод данных для таблицы page_hist
--
-- Таблица не содержит данных

-- 
-- Включение внешних ключей
-- 
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;