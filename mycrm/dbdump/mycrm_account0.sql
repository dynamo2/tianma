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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  `agent_company_name` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  `notes` text,
  `language` varchar(45) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_UNIQUE` (`account`),
  KEY `fk_account_1` (`person_id`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'fan','111111',1,'','ROLE_ADMIN,ROLE_SALES,ROLE_SALES_DIRECTOR,ROLE_OUTSIDE_SALES,ROLE_AGENT,ROLE_CUSTOMER_SERVICE','','zh',NULL,NULL,'2012-05-27 23:59:19','fan'),(11,'yxin','111111',NULL,'亚星','ROLE_AGENT',NULL,NULL,NULL,NULL,'2012-04-03 13:45:03',NULL),(12,'bjiaer','111111',NULL,'贝加尔','ROLE_AGENT',NULL,NULL,NULL,NULL,'2012-04-03 13:45:03',NULL),(13,'zxiaopin','111111',NULL,'赵小平','ROLE_AGENT',NULL,NULL,NULL,NULL,'2012-04-03 13:45:03',NULL),(14,'ljianghong','111111',NULL,'李姜红','ROLE_AGENT',NULL,NULL,NULL,NULL,'2012-04-03 13:45:03',NULL),(15,'yshuihe','111111',NULL,'一水合','ROLE_AGENT',NULL,NULL,NULL,NULL,'2012-04-03 13:45:03',NULL),(16,'qjing','111111',NULL,'昆山全金','ROLE_AGENT',NULL,NULL,NULL,NULL,'2012-04-03 13:45:03',NULL),(17,'schuang','111111',NULL,'武汉塞创','ROLE_AGENT',NULL,NULL,NULL,NULL,'2012-04-03 13:45:03',NULL),(18,'bjing','111111',NULL,'北京办事处','ROLE_AGENT',NULL,NULL,NULL,NULL,'2012-04-03 13:45:03',NULL),(19,'qdao','111111',NULL,'青岛办事处','ROLE_AGENT',NULL,NULL,NULL,NULL,'2012-04-03 13:45:03',NULL),(20,'bchen','11111',NULL,'博成电子','ROLE_AGENT',NULL,NULL,NULL,NULL,'2012-04-04 04:41:49',NULL);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
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
