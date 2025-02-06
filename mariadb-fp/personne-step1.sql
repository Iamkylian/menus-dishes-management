-- --------------------------------------------------------

-- ----------------------------------------------------------
-- Step 1 : personnes seulement
-- ----------------------------------------------------------

DROP TABLE IF EXISTS personne;

CREATE TABLE personne (
  id int(11) NOT NULL auto_increment,
  nom varchar(50) default NULL,
  prenom varchar(50) default NULL,
  age int(11) default NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO personne VALUES (1, 'Dupond', 'Jean', 42);
INSERT INTO personne VALUES (2, 'Durand', 'Pierre', 24);
INSERT INTO personne VALUES (3, 'Moulin', 'Marie', 50);
INSERT INTO personne VALUES (4, 'O''Brien', 'Damien', 22);
INSERT INTO personne VALUES (5, 'Dumoulin', 'Martin', 33);
INSERT INTO personne VALUES(6, 'Leupoisson', 'Benoit', 27);
INSERT INTO personne VALUES(7, 'Alor', 'Marthe', 10);
INSERT INTO personne VALUES(8, 'Tabaniol', 'Alphonse', 27);
INSERT INTO personne VALUES(9, 'Diratamair', 'Oswald', 69);
INSERT INTO personne VALUES(10, 'Couvert', 'Armelle', 38);

-- ----------------------------------------------------------
