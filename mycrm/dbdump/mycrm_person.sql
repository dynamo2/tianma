CREATE DATABASE  IF NOT EXISTS `mycrm` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mycrm`;
-- MySQL dump 10.13  Distrib 5.1.58, for redhat-linux-gnu (x86_64)
--
-- Host: localhost    Database: mycrm
-- ------------------------------------------------------
-- Server version	5.1.58

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `id_type` varchar(45) DEFAULT NULL,
  `id_num` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'王','fan',NULL,'Male','','','22222','','aa@fan.com',NULL,NULL,NULL,'2012-03-25 14:45:34',NULL),(2,'','',NULL,'','','',NULL,NULL,NULL,NULL,NULL,NULL,'2012-03-23 14:24:36',NULL),(3,'ddddd','',NULL,'','','',NULL,NULL,NULL,NULL,NULL,NULL,'2012-03-24 02:44:24',NULL),(4,'ddd','',NULL,'','','',NULL,NULL,NULL,NULL,NULL,NULL,'2012-03-24 02:45:39',NULL),(5,'dddd','',NULL,'','','',NULL,NULL,NULL,NULL,NULL,NULL,'2012-03-24 02:47:24',NULL),(6,'fff','',NULL,'','','',NULL,NULL,NULL,NULL,NULL,NULL,'2012-03-24 02:53:05',NULL),(7,'test','test','2012-03-16 00:00:00','','','',NULL,NULL,NULL,NULL,NULL,NULL,'2012-03-24 08:43:32',NULL),(8,'sss','sss','2012-03-15 00:00:00','Female','','','','','',NULL,NULL,NULL,'2012-03-24 16:39:56',NULL),(9,'jjjj','',NULL,'','','','','','',NULL,NULL,NULL,'2012-03-25 12:34:16',NULL),(10,'kkk','kk',NULL,'','','','','','',NULL,NULL,NULL,'2012-03-25 12:39:27',NULL),(11,'kkkk','',NULL,'','','','','','',NULL,NULL,NULL,'2012-03-25 12:42:35',NULL),(12,'oooo','',NULL,'','','','','','',NULL,NULL,NULL,'2012-03-25 12:42:48',NULL),(13,'ll','8888','2012-03-05 00:00:00','Female','8888888','888888','888','88888888','88888',NULL,NULL,NULL,'2012-03-25 12:43:19',NULL),(14,'ooo','',NULL,'','','','','','',NULL,NULL,NULL,'2012-03-25 12:56:06',NULL);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-03-25 23:34:58
