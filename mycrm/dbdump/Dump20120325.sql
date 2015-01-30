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
-- Current Database: `mycrm`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `mycrm` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mycrm`;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(45) DEFAULT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `last_modified_by` varchar(45) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_account_1` (`person_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'fan',1,'ROLE_ADMIN,ROLE_SALES,ROLE_SALES_DIRECTOR',NULL,NULL,NULL,'2012-03-25 14:45:34','111111'),(2,'test',7,'ROLE_SALES',NULL,NULL,NULL,'2012-03-24 08:43:32','wangfan'),(3,'dddd',8,'',NULL,NULL,NULL,'2012-03-24 16:39:56','111111'),(4,'dddd',NULL,'[]',NULL,NULL,NULL,'2012-03-24 02:45:39','wangfan'),(5,'dddd',NULL,'[]',NULL,NULL,NULL,'2012-03-24 02:47:24','wangfan'),(6,'fff',6,'[]',NULL,NULL,NULL,'2012-03-24 02:53:05','wangfan'),(7,'jjj',9,'ROLE_ADMIN',NULL,NULL,NULL,'2012-03-25 12:34:16','jjj'),(8,'kk',10,'',NULL,NULL,NULL,'2012-03-25 12:39:27','kk');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `person_id` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (9,11,9,NULL,NULL,'2012-03-25 12:42:36',NULL),(10,12,9,NULL,NULL,'2012-03-25 12:42:48',NULL),(11,13,9,NULL,NULL,'2012-03-25 12:43:19',NULL),(12,14,12,NULL,NULL,'2012-03-25 12:56:06',NULL);
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `sales_account` bigint(20) DEFAULT NULL,
  `address1` varchar(45) DEFAULT NULL,
  `address2` varchar(45) DEFAULT NULL,
  `address3` varchar(45) DEFAULT NULL,
  `fax1` varchar(45) DEFAULT NULL,
  `fax2` varchar(45) DEFAULT NULL,
  `fax3` varchar(45) DEFAULT NULL,
  `website` varchar(45) DEFAULT NULL,
  `quality` varchar(45) DEFAULT NULL,
  `industry` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `annual_avenue` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `hear_from` varchar(45) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (9,'kkk',1,'kkjjj','','','','','','','','','kkk','','','','','','',NULL,NULL,'2012-03-25 14:52:17','fan'),(10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2012-03-25 12:21:40',NULL),(11,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2012-03-25 12:24:35',NULL),(12,'888',1,'','','','','','','','','','','','','','','','',NULL,NULL,'2012-03-25 12:55:23',NULL),(13,'ppp',1,'','','','','','','','','','','','','','','','',NULL,NULL,'2012-03-25 12:55:06',NULL);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notes`
--

LOCK TABLES `notes` WRITE;
/*!40000 ALTER TABLE `notes` DISABLE KEYS */;
INSERT INTO `notes` VALUES (9,9,'Customer','oooooooooo',NULL,NULL,'2012-03-25 13:36:45',NULL),(10,9,'customer','kkkkkkkk',NULL,NULL,'2012-03-25 13:44:33',NULL),(11,9,'customer','llll',NULL,NULL,'2012-03-25 13:54:49',NULL);
/*!40000 ALTER TABLE `notes` ENABLE KEYS */;
UNLOCK TABLES;

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
  `assigneer_account` bigint(20) DEFAULT NULL,
  `assigner_account` bigint(20) DEFAULT NULL,
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
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `todo`
--

LOCK TABLES `todo` WRITE;
/*!40000 ALTER TABLE `todo` DISABLE KEYS */;
INSERT INTO `todo` VALUES (9,9,'customer',NULL,NULL,'55555','2012-03-15 00:00:00','2012-03-13 00:00:00',NULL,NULL,NULL,NULL,'2012-03-25 14:28:12',NULL);
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

-- Dump completed on 2012-03-25 23:35:46
