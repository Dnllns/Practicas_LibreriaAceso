/**
 * Author:  n1tr0
 * Created: 15-ene-2018
 */

# MOD PARA SAKILA

use sakila;

create table cinema (
    cinema_id tinyint(3) unsigned auto_increment PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    adress VARCHAR(50) NOT NULL,
    lang_cod tinyint(3) unsigned not null,
    foreign key (lang_cod) references language (language_id)

);

CREATE TABLE cinema_film (
    cinema_film_id tinyINT(3) unsigned AUTO_INCREMENT PRIMARY KEY,
    cinema_cod tinyint(3) unsigned not null,
    film_cod smallint(3) unsigned not null,
    FOREIGN KEY (cinema_cod) REFERENCES cinema (cinema_id),
    FOREIGN KEY (film_cod) REFERENCES film (film_id)
);

INSERT INTO `sakila`.`language` (`name`, `last_update`) VALUES ('Español', '2018-01-15 19:48:00');
INSERT INTO `sakila`.`cinema` (`name`, `adress`, `lang_cod`) VALUES ('Cine 1', 'Calle 1', '7');
INSERT INTO `sakila`.`cinema` (`name`, `adress`, `lang_cod`) VALUES ('Cine 2', 'Calle 2', '7');
INSERT INTO `sakila`.`cinema_film` (`cinema_cod`, `film_cod`) VALUES ('1', '1');
INSERT INTO `sakila`.`cinema_film` (`cinema_cod`, `film_cod`) VALUES ('1', '2');
INSERT INTO `sakila`.`cinema_film` (`cinema_cod`, `film_cod`) VALUES ('2', '2');


