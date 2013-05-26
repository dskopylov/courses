-- Скрипт сгенерирован Devart dbForge Studio for MySQL, Версия 4.50.315.1
-- Дата: 26.05.2013 18:57:44
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
AUTO_INCREMENT = 17
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
AUTO_INCREMENT = 36
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
AUTO_INCREMENT = 5
AVG_ROW_LENGTH = 5461
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
AUTO_INCREMENT = 22
AVG_ROW_LENGTH = 963
CHARACTER SET utf8
COLLATE utf8_general_ci;

-- 
-- Вывод данных для таблицы courses
--
INSERT INTO courses VALUES 
  (1, 'DLC0001', 'Тестовый курс от ЦДО НИУ ИТМО'),
  (12, 'DLC0002', 'Тестовый 2'),
  (16, 'TEST1', 'Еще ');

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
  (1, 1, 'home', 'Главная', 'cdo_test', 'Добро пожаловать на Тестовый курс от ЦДО НИУ ИТМО<br />\r\n2013 г.'),
  (2, 1, 'program', 'Программа', 'programma_kursa', 'Программа курса'),
  (3, 1, 'literature', 'Литература', '', 'Тест'),
  (4, 1, 'exercise', 'Упражнения', '', ''),
  (5, 1, 'training', 'Практикумы', '', ''),
  (6, 1, 'infoResources', 'Информационные ресурсы', '', ''),
  (7, 1, 'lection1', 'Лекция 1', '', ' '),
  (12, 12, 'home', 'Главная', '', '<h3>Добро пожаловать на главную страницу курса от ЦДО!</h3>\r\n\r\nЗдесь '),
  (13, 12, 'program', 'Программа курса', '', 'Здесь Вы узнаете о программе курса.'),
  (14, 12, 'literature', 'Литература', '', 'А здесь Вы узнаете о литературе.'),
  (15, 12, 'exercise', 'Упражнения', '', 'Упражнения курса.'),
  (16, 12, 'training', 'Практикумы', '', 'Практикумы курса.'),
  (17, 12, 'infoResources', 'Информационные ресурсы', '', 'И в конце - информационные ресурсы.'),
  (30, 16, 'home', 'Главная', '', 'Проверка!'),
  (31, 16, 'program', 'Программа курса', '', ''),
  (32, 16, 'literature', 'Литература', '', ''),
  (33, 16, 'exercise', 'Упражнения', '', ''),
  (34, 16, 'training', 'Практикумы', '', ''),
  (35, 16, 'infoResources', 'Информационные ресурсы', '', '');

-- 
-- Вывод данных для таблицы users
--
INSERT INTO users VALUES 
  (1, 'root', '21232f297a57a5a743894a0e4a801fc3', 'Администратор', 'dima@cde.ifmo.ru', 1),
  (3, 'dima', '0f5b25cd58319cde0e6e33715b66db4c', 'Дима', 'dima@dima.ru', 2),
  (4, 'd', '8277e0910d750195b448797616e091ad', 'd', 'dima1@dima.ru', 2);

-- 
-- Вывод данных для таблицы page_hist
--
INSERT INTO page_hist VALUES 
  (2, '2013-05-26 13:17:57', 1, '--- Before\n+++ After\n@@ -1,0 +1,1 @@\n+Test\n', 1, 12),
  (3, '2013-05-26 13:21:29', 1, '--- Before\n+++ After\n@@ -2,0 +2,2 @@\n+\n+Проверка\n', 2, 12),
  (5, '2013-05-26 13:26:28', 1, '--- Before\n+++ After\n@@ -4,0 +4,3 @@\n+\n+\n+Еще\n', 3, 12),
  (6, '2013-05-26 13:28:04', 1, '--- Before\n+++ After\n@@ -7,0 +7,3 @@\n+\n+\n+Еще\n', 4, 12),
  (9, '2013-05-26 14:37:06', 1, '--- Before\n+++ After\n@@ -1,0 +1,1 @@\n+<h1>Тест</h1>\n', 5, 12),
  (10, '2013-05-26 15:31:54', 1, '--- Before\n+++ After\n@@ -11,0 +11,3 @@\n+\n+\n+И что-нибудь еще!\n', 6, 12),
  (11, '2013-05-26 15:36:13', 1, '--- Before\n+++ After\n@@ -1,0 +1,1 @@\n+Тест\n', 1, 3),
  (12, '2013-05-26 15:36:56', 1, '--- Before\n+++ After\n@@ -3,0 +3,2 @@\n+\n+ааа\n', 1, 1),
  (13, '2013-05-26 17:12:57', 1, '--- Before\n+++ After\n@@ -3,1 +3,0 @@\n-\n@@ -5,2 +4,0 @@\n-\n-\n@@ -8,2 +5,0 @@\n-\n-\n@@ -11,2 +6,0 @@\n-\n-\n', 7, 12),
  (14, '2013-05-26 17:14:31', 1, '--- Before\n+++ After\n@@ -1,6 +1,3 @@\n-<h1>Тест</h1>\n-Test\n-Проверка\n-Еще\n-Еще\n-И что-нибудь еще!\n+<h3>Добро пожаловать на главную страницу курса от ЦДО!</h3>\n+\n+Здесь \n', 8, 12),
  (15, '2013-05-26 17:15:14', 1, '--- Before\n+++ After\n@@ -1,0 +1,1 @@\n+Здесь Вы узнаете о программе курса.\n', 1, 13),
  (16, '2013-05-26 17:15:29', 1, '--- Before\n+++ After\n@@ -1,1 +1,1 @@\n-Литература\n+А здесь Вы узнаете о литературе.\n', 1, 14),
  (17, '2013-05-26 17:15:41', 1, '--- Before\n+++ After\n@@ -1,0 +1,1 @@\n+Упражнения курса.\n', 1, 15),
  (18, '2013-05-26 17:15:48', 1, '--- Before\n+++ After\n@@ -1,0 +1,1 @@\n+Практикумы курса.\n', 1, 16),
  (19, '2013-05-26 17:16:03', 1, '--- Before\n+++ After\n@@ -1,0 +1,1 @@\n+И в конце - информационные ресурсы.\n', 1, 17),
  (20, '2013-05-26 17:16:39', 1, '--- Before\n+++ After\n@@ -1,1 +1,1 @@\n-Добро пожаловать на Тестовый курс от ЦДО НИУ ИТМО\n+Добро пожаловать на Тестовый курс от ЦДО НИУ ИТМО<br />\n@@ -3,2 +3,0 @@\n-\n-ааа\n', 2, 1),
  (21, '2013-05-26 18:48:39', 3, '--- Before\n+++ After\n@@ -1,0 +1,1 @@\n+Проверка!\n', 1, 30);

-- 
-- Включение внешних ключей
-- 
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;