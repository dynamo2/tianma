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
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `for_id` bigint(20) DEFAULT NULL,
  `for_type` varchar(255) DEFAULT NULL,
  `notes` text,
  `created` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notes`
--

LOCK TABLES `notes` WRITE;
/*!40000 ALTER TABLE `notes` DISABLE KEYS */;
INSERT INTO `notes` VALUES (9,9,'Customer','oooooooooo',NULL,NULL,'2012-03-25 13:36:45',NULL),(10,9,'customer','kkkkkkkk',NULL,NULL,'2012-03-25 13:44:33',NULL),(11,9,'customer','llll',NULL,NULL,'2012-03-25 13:54:49',NULL),(12,14,'customer','jjj','2012-03-27 22:32:35','fan','2012-03-27 14:32:35','fan'),(13,14,'customer','mmm','2012-03-27 22:35:54','fan','2012-03-27 14:35:54','fan'),(14,14,'customer',';lkjl;kj;ljl;kj','2012-03-27 22:49:00','fan','2012-03-27 14:49:00','fan'),(15,14,'customer','kkkkkkkkkkkk','2012-03-27 23:00:49','fan','2012-03-27 15:00:49','fan'),(16,14,'customer','jjjjjjjjjjjj','2012-03-27 23:04:32','fan','2012-03-27 15:04:33','fan'),(17,14,'customer',' hhhhhhhhhhhhh','2012-03-27 23:07:01','fan','2012-03-27 15:07:01','fan'),(18,14,'customer','ttttttttttt','2012-03-27 23:08:49','fan','2012-03-27 15:08:49','fan'),(19,14,'customer','llllllllllll','2012-03-27 23:09:02','fan','2012-03-27 15:09:02','fan'),(20,14,'customer','jjjjjjjjjk','2012-03-27 23:10:23','fan','2012-03-27 15:10:23','fan'),(21,14,'customer','jjjjjjjjjk;;;;;;;;','2012-03-27 23:10:42','fan','2012-03-27 15:10:42','fan'),(22,14,'customer','llllllllll','2012-03-27 23:11:09','fan','2012-03-27 15:11:09','fan'),(23,14,'customer','llllllll','2012-03-27 23:13:58','fan','2012-03-27 15:13:58','fan'),(24,14,'customer','看看看看看看看看看看看','2012-03-28 22:57:41','fan','2012-03-28 14:57:41','fan'),(25,14,'customer','lllllllll','2012-03-29 19:11:31','fan','2012-03-29 11:11:31','fan'),(26,15,'customer','kkkkk','2012-03-30 10:19:32','fan','2012-03-30 02:19:32','fan'),(27,14,'customer','eeeeeeeeeeeee','2012-03-30 21:04:25','fan','2012-03-30 13:04:25','fan'),(28,9,'customer','反反复复反反复复方法','2012-03-31 23:07:00','fan','2012-03-31 15:07:00','fan'),(29,9,'customer','方法凡','2012-03-31 23:24:49','fan','2012-03-31 15:24:49','fan'),(30,9,'customer','cccccccccc','2012-03-31 23:30:14','fan','2012-03-31 15:30:14','fan'),(31,14,'customer','fffffffff','2012-03-31 23:31:48','fan','2012-03-31 15:31:48','fan'),(32,14,'customer','vfffffff','2012-03-31 23:32:07','fan','2012-03-31 15:32:07','fan'),(33,13,'customer','fff','2012-03-31 23:33:41','fan','2012-03-31 15:33:42','fan'),(34,9,'customer','dddddddd','2012-03-31 23:36:16','fan','2012-03-31 15:36:16','fan');
/*!40000 ALTER TABLE `notes` ENABLE KEYS */;
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
