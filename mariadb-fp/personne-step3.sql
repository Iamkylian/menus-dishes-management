-- ----------------------------------------------------------
-- Step 3 : personnes, societes et services
-- ----------------------------------------------------------

DROP TABLE IF EXISTS service;
DROP TABLE IF EXISTS societe;
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

CREATE TABLE societe (
 id         INT( 11 )     NOT NULL AUTO_INCREMENT ,
 nom        VARCHAR(30)   NOT NULL ,
 PRIMARY KEY ( id )
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO societe VALUES( 1, 'Youhaa');
INSERT INTO societe VALUES( 2, 'FaceHook');
INSERT INTO societe VALUES( 3, 'Glooble' );

-- ----------------------------------------------------------


CREATE TABLE service (
 id         INT( 11 )     NOT NULL AUTO_INCREMENT ,
 nom        VARCHAR(30)   NOT NULL ,
 id_chef    INT( 11 ) ,
 id_societe INT( 11 ) ,
 PRIMARY KEY ( id ) ,
 CONSTRAINT fk_id_chef    
    FOREIGN KEY (id_chef)  
    REFERENCES personne(id)  
    ON DELETE SET NULL,    
 CONSTRAINT fk_id_societe    
    FOREIGN KEY (id_societe)  
    REFERENCES societe(id)  
    ON DELETE SET NULL    
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO service VALUES( 1, 'Marketing', 1, 1 );
INSERT INTO service VALUES( 2, 'Comptabilite', 2, 1 );
INSERT INTO service VALUES( 3, 'Ressources Humaines', 3, 1 );
INSERT INTO service VALUES( 4, 'Achat', 4, 2 );
INSERT INTO service VALUES( 5, 'Marketing', 5, 2 );
INSERT INTO service VALUES( 6, 'Ressources Humaines', 6, 3 );
INSERT INTO service VALUES( 7, 'Marketing', 8, 3 );
INSERT INTO service VALUES( 8, 'Comptabilite', 9, 3 );
INSERT INTO service VALUES( 9, 'Ressources Humaines', 10, 3 );
