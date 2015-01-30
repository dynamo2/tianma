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
-- Table structure for table `form_field_metadata`
--

DROP TABLE IF EXISTS `form_field_metadata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `form_field_metadata` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `form_metadata_id` bigint(20) NOT NULL,
  `field_name` varchar(255) NOT NULL,
  `field_type` varchar(255) NOT NULL,
  `field_label` varchar(255) NOT NULL,
  `column_name` varchar(45) NOT NULL,
  `display_position_row` int(11) NOT NULL DEFAULT '0',
  `display_position_column` int(11) NOT NULL DEFAULT '0',
  `display_position_group` int(11) NOT NULL DEFAULT '0',
  `is_required` bit(1) NOT NULL DEFAULT b'0',
  `option_values` text,
  `input_columns` int(11) DEFAULT '20',
  `input_rows` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_fieldname` (`field_name`,`form_metadata_id`),
  UNIQUE KEY `unique_column` (`column_name`,`form_metadata_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_field_metadata`
--

LOCK TABLES `form_field_metadata` WRITE;
/*!40000 ALTER TABLE `form_field_metadata` DISABLE KEYS */;
INSERT INTO `form_field_metadata` VALUES (10,1,'kkupp','varchar','kko','custom_varchar_6',0,0,0,'\0',NULL,20,1),(9,1,'iu','varchar','88lu','custom_varchar_5',0,0,3,'\0',NULL,20,1),(3,1,'group2','varchar','g2r0c0','custom_varchar_3',0,0,2,'\0',NULL,20,1),(4,1,'group210','varchar','g2r1c0','custom_varchar_4',1,0,2,'\0',NULL,20,1),(8,1,'dd','varchar','llj','custom_varchar_2',0,0,0,'\0',NULL,20,1),(7,1,'oopi','varchar','ipplj','custom_varchar_1',0,0,0,'\0',NULL,20,1),(11,1,'kjhjki','text','kkkku','custom_text_1',0,0,4,'\0',NULL,100,5),(12,6,'11','varchar','22','custom_varchar_1',0,0,0,'\0',NULL,20,1);
/*!40000 ALTER TABLE `form_field_metadata` ENABLE KEYS */;
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
