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
-- Table structure for table `order_contract`
--

DROP TABLE IF EXISTS `order_contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_contract` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `form_name` varchar(255) NOT NULL,
  `attachment` blob,
  `created_date` datetime NOT NULL,
  `created_by` varchar(45) NOT NULL,
  `last_modified_date` datetime NOT NULL,
  `last_modified_by` varchar(45) NOT NULL,
  `assginee_account` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `custom_varchar_1` varchar(255) DEFAULT NULL,
  `custom_varchar_2` varchar(255) DEFAULT NULL,
  `custom_varchar_3` varchar(255) DEFAULT NULL,
  `custom_varchar_4` varchar(255) DEFAULT NULL,
  `custom_varchar_5` varchar(255) DEFAULT NULL,
  `custom_varchar_6` varchar(255) DEFAULT NULL,
  `custom_varchar_7` varchar(255) DEFAULT NULL,
  `custom_varchar_8` varchar(255) DEFAULT NULL,
  `custom_varchar_9` varchar(255) DEFAULT NULL,
  `custom_varchar_10` varchar(255) DEFAULT NULL,
  `custom_varchar_11` varchar(255) DEFAULT NULL,
  `custom_varchar_12` varchar(255) DEFAULT NULL,
  `custom_varchar_13` varchar(255) DEFAULT NULL,
  `custom_varchar_14` varchar(255) DEFAULT NULL,
  `custom_varchar_15` varchar(255) DEFAULT NULL,
  `custom_varchar_16` varchar(255) DEFAULT NULL,
  `custom_varchar_17` varchar(255) DEFAULT NULL,
  `custom_varchar_18` varchar(255) DEFAULT NULL,
  `custom_varchar_19` varchar(255) DEFAULT NULL,
  `custom_varchar_20` varchar(255) DEFAULT NULL,
  `custom_varchar_21` varchar(255) DEFAULT NULL,
  `custom_varchar_22` varchar(255) DEFAULT NULL,
  `custom_varchar_23` varchar(255) DEFAULT NULL,
  `custom_varchar_24` varchar(255) DEFAULT NULL,
  `custom_varchar_25` varchar(255) DEFAULT NULL,
  `custom_varchar_26` varchar(255) DEFAULT NULL,
  `custom_varchar_27` varchar(255) DEFAULT NULL,
  `custom_varchar_28` varchar(255) DEFAULT NULL,
  `custom_varchar_29` varchar(255) DEFAULT NULL,
  `custom_varchar_30` varchar(255) DEFAULT NULL,
  `custom_varchar_31` varchar(255) DEFAULT NULL,
  `custom_varchar_32` varchar(255) DEFAULT NULL,
  `custom_varchar_33` varchar(255) DEFAULT NULL,
  `custom_varchar_34` varchar(255) DEFAULT NULL,
  `custom_varchar_35` varchar(255) DEFAULT NULL,
  `custom_varchar_36` varchar(255) DEFAULT NULL,
  `custom_varchar_37` varchar(255) DEFAULT NULL,
  `custom_varchar_38` varchar(255) DEFAULT NULL,
  `custom_varchar_39` varchar(255) DEFAULT NULL,
  `custom_varchar_40` varchar(255) DEFAULT NULL,
  `custom_varchar_41` varchar(255) DEFAULT NULL,
  `custom_varchar_42` varchar(255) DEFAULT NULL,
  `custom_varchar_43` varchar(255) DEFAULT NULL,
  `custom_varchar_44` varchar(255) DEFAULT NULL,
  `custom_varchar_45` varchar(255) DEFAULT NULL,
  `custom_varchar_46` varchar(255) DEFAULT NULL,
  `custom_varchar_47` varchar(255) DEFAULT NULL,
  `custom_varchar_48` varchar(255) DEFAULT NULL,
  `custom_varchar_49` varchar(255) DEFAULT NULL,
  `custom_varchar_50` varchar(255) DEFAULT NULL,
  `custom_int_1` int(11) DEFAULT NULL,
  `custom_int_2` int(11) DEFAULT NULL,
  `custom_int_3` int(11) DEFAULT NULL,
  `custom_int_4` int(11) DEFAULT NULL,
  `custom_int_5` int(11) DEFAULT NULL,
  `custom_int_6` int(11) DEFAULT NULL,
  `custom_int_7` int(11) DEFAULT NULL,
  `custom_int_8` int(11) DEFAULT NULL,
  `custom_int_9` int(11) DEFAULT NULL,
  `custom_int_10` int(11) DEFAULT NULL,
  `custom_double_1` double DEFAULT NULL,
  `custom_double_2` double DEFAULT NULL,
  `custom_double_3` double DEFAULT NULL,
  `custom_double_4` double DEFAULT NULL,
  `custom_double_5` double DEFAULT NULL,
  `custom_datetime_1` datetime DEFAULT NULL,
  `custom_datetime_2` datetime DEFAULT NULL,
  `custom_datetime_3` datetime DEFAULT NULL,
  `custom_datetime_4` datetime DEFAULT NULL,
  `custom_datetime_5` datetime DEFAULT NULL,
  `custom_text_1` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_contract`
--

LOCK TABLES `order_contract` WRITE;
/*!40000 ALTER TABLE `order_contract` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_contract` ENABLE KEYS */;
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
