-- ----------------------------------------------------------
-- Step 2 : personnes et services
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

-- ----------------------------------------------------------

DROP TABLE IF EXISTS service;

CREATE TABLE service (
 id         INT( 11 )     NOT NULL AUTO_INCREMENT ,
 nom        VARCHAR(30)   NOT NULL ,
 id_chef    INT( 11 ) ,
 PRIMARY KEY ( id ) ,
 CONSTRAINT fk_id_chef    
    FOREIGN KEY (id_chef)  
    REFERENCES personne(id)  
    ON DELETE SET NULL    
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO service VALUES( 1, 'Marketing', 1 );
INSERT INTO service VALUES( 2, 'Comptabilite', 3 );
INSERT INTO service VALUES( 3, 'Ressources Humaines', 6 );
INSERT INTO service VALUES( 4, 'Achat', 2 );

