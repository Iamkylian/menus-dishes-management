
DROP TABLE IF EXISTS article_categorie;
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS categorie;

CREATE TABLE article (
 id         INT( 11 )     NOT NULL AUTO_INCREMENT ,
 nom        VARCHAR(30)   NOT NULL ,
 PRIMARY KEY ( id )
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE categorie (
 id         INT( 11 )     NOT NULL AUTO_INCREMENT ,
 nom        VARCHAR(30)   NOT NULL ,
 PRIMARY KEY ( id )
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE article_categorie (
 id_article INT(11) NOT NULL,
 id_categorie INT(11) NOT NULL,
 CONSTRAINT fk_id_article    
    FOREIGN KEY (id_article)  
    REFERENCES article(id),    
 CONSTRAINT fk_id_categorie    
    FOREIGN KEY (id_categorie)  
    REFERENCES categorie(id)
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO article VALUES( 1, 'un');
INSERT INTO article VALUES( 2, 'deux');
INSERT INTO article VALUES( 3, 'trois');
INSERT INTO article VALUES( 4, 'quatre');
INSERT INTO article VALUES( 5, 'cinq');
INSERT INTO article VALUES( 6, 'six');
INSERT INTO article VALUES( 7, 'sept');
INSERT INTO article VALUES( 8, 'huit');
INSERT INTO article VALUES( 9, 'neuf');

INSERT INTO categorie VALUES( 1, 'impair');
INSERT INTO categorie VALUES( 2, 'pair');
INSERT INTO categorie VALUES( 3, 'triple');

INSERT INTO article_categorie VALUES( 1 , 1 );
INSERT INTO article_categorie VALUES( 3 , 1 );
INSERT INTO article_categorie VALUES( 5 , 1 );
INSERT INTO article_categorie VALUES( 7 , 1 );
INSERT INTO article_categorie VALUES( 9 , 1 );
INSERT INTO article_categorie VALUES( 2 , 2 );
INSERT INTO article_categorie VALUES( 4 , 2 );
INSERT INTO article_categorie VALUES( 6 , 2 );
INSERT INTO article_categorie VALUES( 8 , 2 );
INSERT INTO article_categorie VALUES( 3 , 3 );
INSERT INTO article_categorie VALUES( 6 , 3 );
INSERT INTO article_categorie VALUES( 9 , 3 );
