-- Скрипт сгенерирован Devart dbForge Studio for MySQL, Версия 5.0.50.1
-- Домашняя страница продукта: http://www.devart.com/ru/dbforge/mysql/studio
-- Дата скрипта: 01.05.2013 18:13:36
-- Версия сервера: 5.1.49-3
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
AUTO_INCREMENT = 2
AVG_ROW_LENGTH = 16384
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
-- Описание для таблицы page_hist
--
DROP TABLE IF EXISTS page_hist;
CREATE TABLE page_hist (
  id INT(11) NOT NULL AUTO_INCREMENT,
  `datetime` DATETIME NOT NULL,
  user_id INT(11) NOT NULL,
  diff TEXT NOT NULL,
  version INT(11) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
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
AUTO_INCREMENT = 3
AVG_ROW_LENGTH = 8192
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

-- При генерации скрипта для объектов типа 'UDF'ы' произошла ошибка:
-- SELECT command denied to user 'coursesdev'@'ws02.cde.ifmo.ru' for table 'func'

-- 
-- Вывод данных для таблицы courses
--
INSERT INTO courses VALUES 
  (1, 'DLC0001', 'Тестовый курс от ЦДО НИУ ИТМО');

-- 
-- Вывод данных для таблицы groups
--
INSERT INTO groups VALUES 
  (1, 'Admins'),
  (2, 'Users');

-- 
-- Вывод данных для таблицы page_hist
--
-- Таблица не содержит данных

-- 
-- Вывод данных для таблицы pages
--
INSERT INTO pages VALUES 
  (1, 1, 'main', 'Главная', 'cdo_test', 'Тестовы курс от ЦДО НИУ ИТМО\r\n2013 г.'),
  (2, 1, 'program', 'Программа курса', 'programma_kursa', 'Программа курса');

-- 
-- Вывод данных для таблицы users
--
INSERT INTO users VALUES 
  (1, 'root', '21232f297a57a5a743894a0e4a801fc3', 'Администратор', 'dima@cde.ifmo.ru', 1);

-- 
-- Включение внешних ключей
-- 
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;