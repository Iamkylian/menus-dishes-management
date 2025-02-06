-- MariaDB dump 10.19  Distrib 10.8.3-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: prjspring2025
-- ------------------------------------------------------
-- Server version	10.8.3-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categorie`
--

DROP TABLE IF EXISTS `categorie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categorie` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorie`
--

LOCK TABLES `categorie` WRITE;
/*!40000 ALTER TABLE `categorie` DISABLE KEYS */;
INSERT INTO `categorie` VALUES
(28,'Entrée'),
(29,'Plat principal'),
(30,'Dessert');
/*!40000 ALTER TABLE `categorie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prix` double NOT NULL CHECK (`prix` >= 0),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES
(46,'Menu composé de deux entrées, d\'un plat et d\'un dessert','Menu Découverte',25.9),
(47,'Tout en sucre','Menu Gourmand',32.5),
(48,'Menu composé d\'une entrée, d\'un plat et d\'un dessert','Menu Végétarien',28.9),
(49,'Menu composé d\'une entrée, d\'un plat et d\'un dessert','Menu Tradition',29.9),
(50,'Parcours du gourmand','Menu Dégustation',59.95);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_plat`
--

DROP TABLE IF EXISTS `menu_plat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu_plat` (
  `menu_id` bigint(20) NOT NULL,
  `plat_id` bigint(20) NOT NULL,
  PRIMARY KEY (`menu_id`,`plat_id`),
  KEY `FK5wijsn1ol92rwunymd51dlfwn` (`plat_id`),
  CONSTRAINT `FK5wijsn1ol92rwunymd51dlfwn` FOREIGN KEY (`plat_id`) REFERENCES `plat` (`id`),
  CONSTRAINT `FKtm1ww2v3ygjp8fi35v3yc2u4d` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_plat`
--

LOCK TABLES `menu_plat` WRITE;
/*!40000 ALTER TABLE `menu_plat` DISABLE KEYS */;
INSERT INTO `menu_plat` VALUES
(46,279),
(46,280),
(46,290),
(46,292),
(47,293),
(47,294),
(47,295),
(47,301),
(48,273),
(48,290),
(48,297),
(49,278),
(49,286),
(49,300),
(50,274),
(50,276),
(50,277),
(50,278),
(50,284),
(50,288),
(50,289),
(50,294),
(50,295),
(50,300);
/*!40000 ALTER TABLE `menu_plat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plat`
--

DROP TABLE IF EXISTS `plat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nb_calories` int(11) NOT NULL CHECK (`nb_calories` >= 0),
  `nb_glucides` int(11) NOT NULL CHECK (`nb_glucides` >= 0),
  `nb_lipides` int(11) NOT NULL CHECK (`nb_lipides` >= 0),
  `nb_proteines` int(11) NOT NULL CHECK (`nb_proteines` >= 0),
  `nom` varchar(255) DEFAULT NULL,
  `categorie_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqk8rf3sky00fc8avq2uymm84p` (`categorie_id`),
  CONSTRAINT `FKqk8rf3sky00fc8avq2uymm84p` FOREIGN KEY (`categorie_id`) REFERENCES `categorie` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=302 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plat`
--

LOCK TABLES `plat` WRITE;
/*!40000 ALTER TABLE `plat` DISABLE KEYS */;
INSERT INTO `plat` VALUES
(272,390,21,18,10,'Salade César',28),
(273,288,22,17,13,'Soupe à l\'oignon',28),
(274,225,28,5,9,'Carpaccio de boeuf',28),
(275,338,28,12,13,'Foie gras',28),
(276,335,11,13,10,'Crevettes grillées',28),
(277,363,21,8,8,'Tartare de saumon',28),
(278,354,29,8,12,'Bruschetta',28),
(279,399,20,13,9,'Velouté de potiron',28),
(280,262,11,18,8,'Asperges gratinées',28),
(281,252,29,8,9,'Salade niçoise',28),
(282,722,56,27,29,'Steak frites',29),
(283,537,53,25,31,'Poulet rôti',29),
(284,583,38,21,20,'Saumon grillé',29),
(285,743,37,24,37,'Lasagnes',29),
(286,767,55,21,34,'Couscous',29),
(287,419,35,16,25,'Paëlla',29),
(288,551,59,32,25,'Magret de canard',29),
(289,657,33,17,31,'Risotto aux champignons',29),
(290,461,50,23,24,'Ratatouille',29),
(291,666,33,30,21,'Boeuf bourguignon',29),
(292,349,47,16,3,'Tarte au citron',30),
(293,434,38,22,7,'Crème brûlée',30),
(294,483,40,19,7,'Mousse au chocolat',30),
(295,458,46,11,6,'Tiramisu',30),
(296,232,33,14,5,'Tarte Tatin',30),
(297,263,25,12,3,'Profiteroles',30),
(298,316,41,19,6,'Île flottante',30),
(299,353,48,23,3,'Paris-Brest',30),
(300,238,47,12,7,'Fondant au chocolat',30),
(301,464,47,21,5,'Mille-feuille',30);
/*!40000 ALTER TABLE `plat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-04  1:32:21
