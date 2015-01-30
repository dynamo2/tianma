CREATE DATABASE  IF NOT EXISTS `mycrm` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mycrm`;
-- MySQL dump 10.13  Distrib 5.1.60, for redhat-linux-gnu (x86_64)
--
-- Host: localhost    Database: mycrm
-- ------------------------------------------------------
-- Server version	5.1.60

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
-- Table structure for table `todo`
--

DROP TABLE IF EXISTS `todo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `todo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `for_id` bigint(20) DEFAULT NULL,
  `for_type` varchar(255) DEFAULT NULL,
  `assigneer_account` varchar(45) DEFAULT NULL,
  `assigner_account` varchar(45) DEFAULT NULL,
  `description` text,
  `plan_start` datetime DEFAULT NULL,
  `plan_end` datetime DEFAULT NULL,
  `actual_start` datetime DEFAULT NULL,
  `actual_end` datetime DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `todo`
--

LOCK TABLES `todo` WRITE;
/*!40000 ALTER TABLE `todo` DISABLE KEYS */;
INSERT INTO `todo` VALUES (9,9,'customer',NULL,NULL,'55555','2012-03-15 00:00:00','2012-03-13 00:00:00',NULL,NULL,NULL,NULL,'2012-03-25 14:28:12',NULL),(10,14,'customer',NULL,NULL,'kkk','2012-03-20 00:00:00','2012-03-27 22:32:00',NULL,NULL,'2012-03-27 22:32:21','fan','2012-03-27 14:32:21','fan'),(11,14,'customer',NULL,NULL,'llllllllllll','2012-03-27 23:26:00','2012-03-27 23:26:00',NULL,NULL,'2012-03-27 23:26:52','fan','2012-03-27 15:26:52','fan'),(12,14,'customer',NULL,NULL,'。。。。。。','2012-03-28 10:59:00','2012-03-19 04:12:00',NULL,NULL,'2012-03-28 23:00:55','fan','2012-03-28 15:00:55','fan'),(13,15,'customer',NULL,NULL,'uuuu',NULL,NULL,NULL,NULL,'2012-03-30 10:19:45','fan','2012-03-30 02:19:45','fan'),(14,14,'customer',NULL,NULL,'ddddddddd','2012-03-12 00:00:00','2012-03-20 04:12:00',NULL,NULL,'2012-03-30 21:11:32','fan','2012-03-30 13:11:32','fan'),(15,9,'customer','fan','fan','ssssss','2012-04-09 00:00:00','2012-04-04 00:00:00',NULL,NULL,'2012-04-01 00:09:58','fan','2012-03-31 16:09:58','fan'),(16,1698,'customer','fan','fan','test','2012-04-16 00:00:00','2012-04-26 00:00:00',NULL,NULL,'2012-04-15 17:57:05','fan','2012-04-15 09:57:05','fan'),(17,1698,'customer','fan','fan','mmmmm','2012-04-03 00:00:00','2012-04-21 00:00:00',NULL,NULL,'2012-04-15 18:28:37','fan','2012-04-15 10:28:37','fan');
/*!40000 ALTER TABLE `todo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-05-28  8:41:18
