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
-- Table structure for table `form_metadata`
--

DROP TABLE IF EXISTS `form_metadata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `form_metadata` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `form_name` varchar(255) NOT NULL,
  `form_label` varchar(255) NOT NULL,
  `form_language` varchar(45) NOT NULL,
  `form_status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `form_name_UNIQUE` (`form_name`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_metadata`
--

LOCK TABLES `form_metadata` WRITE;
/*!40000 ALTER TABLE `form_metadata` DISABLE KEYS */;
INSERT INTO `form_metadata` VALUES (1,'dd','ddd','zh','NEW'),(2,'uuy','yyui','zh','NEW'),(3,'lll','uuuuu','zh','NEW'),(4,'test1','测试1','zh','NEW'),(5,'test2','测试2','zh','NEW'),(6,'test3','测试3','zh','PUBLISHED');
/*!40000 ALTER TABLE `form_metadata` ENABLE KEYS */;
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
