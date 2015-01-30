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
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `person_id` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `notes` text,
  `title` varchar(45) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=287 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (9,11,9,NULL,NULL,NULL,NULL,NULL,'2012-03-25 12:42:36',NULL),(10,12,9,NULL,NULL,NULL,NULL,NULL,'2012-03-25 12:42:48',NULL),(11,13,9,NULL,NULL,NULL,NULL,NULL,'2012-03-25 12:43:19',NULL),(12,14,12,NULL,NULL,NULL,NULL,NULL,'2012-03-25 12:56:06',NULL),(13,15,14,'CUSTOMER_EMPLOYEE','llll','iiiii','2012-03-27 21:27:01','fan','2012-03-27 13:27:01','fan'),(14,16,14,'CUSTOMER_EMPLOYEE','','ggg','2012-03-28 00:39:13','fan','2012-03-27 16:39:13','fan'),(15,17,14,'CUSTOMER_EMPLOYEE','','gggggggg','2012-03-28 00:55:04','fan','2012-03-27 16:55:04','fan'),(16,18,14,'CUSTOMER_EMPLOYEE','222','222','2012-03-28 08:29:20','fan','2012-03-28 00:29:20','fan'),(17,19,14,'CUSTOMER_EMPLOYEE','uuu','uuu','2012-03-28 08:49:46','fan','2012-03-28 00:49:46','fan'),(18,20,14,'CUSTOMER_EMPLOYEE','ddd','ddd','2012-03-28 08:52:30','fan','2012-03-28 00:52:30','fan'),(19,21,14,'CUSTOMER','llllllllllk','llllllllll','2012-03-28 15:11:32','fan','2012-03-28 07:11:32','fan'),(20,22,14,'CUSTOMER','llllllllloo',';;;;;','2012-03-28 15:14:36','fan','2012-03-28 07:14:36','fan'),(21,23,14,'CUSTOMER','','ttttttt','2012-03-28 15:17:08','fan','2012-03-28 07:17:08','fan'),(22,24,0,'CUSTOMER','mm','','2012-03-28 15:21:47','fan','2012-03-28 07:21:47','fan'),(23,25,14,'CUSTOMER','kkk','kkk','2012-03-28 15:24:32','fan','2012-03-28 07:24:32','fan'),(24,26,14,'CUSTOMER','l;;;;;;','lll','2012-03-28 15:33:09','fan','2012-03-28 07:33:09','fan'),(25,27,14,'CUSTOMER','jj','jj','2012-03-28 15:38:26','fan','2012-03-28 07:38:26','fan'),(26,28,14,'CUSTOMER','hhhhhh','hhh','2012-03-28 15:38:37','fan','2012-03-28 07:38:37','fan'),(27,29,14,'CUSTOMER','','pp','2012-03-28 21:25:09','fan','2012-03-28 13:25:09','fan'),(28,30,14,'CUSTOMER','hhhhhhhhh','hhhhhh','2012-03-28 22:43:46','fan','2012-03-28 14:43:46','fan'),(29,31,14,'CUSTOMER','','','2012-03-28 22:45:21','fan','2012-03-28 14:45:21','fan'),(30,32,14,'CUSTOMER','','','2012-03-28 22:49:11','fan','2012-03-28 14:49:11','fan'),(31,33,14,'CUSTOMER','test-1','purchase manager','2012-03-29 19:09:09','fan','2012-03-29 11:09:09','fan'),(32,34,14,'CUSTOMER',NULL,NULL,'2012-03-29 19:10:18','fan','2012-03-29 11:10:18','fan'),(33,35,14,'CUSTOMER',NULL,NULL,'2012-03-29 19:10:49','fan','2012-03-29 11:10:49','fan'),(34,36,14,'CUSTOMER',NULL,NULL,'2012-03-29 19:12:21','fan','2012-03-29 11:12:21','fan'),(35,37,14,'CUSTOMER','','','2012-03-29 19:16:45','fan','2012-03-29 11:16:45','fan'),(36,38,9,'CUSTOMER','','','2012-03-29 22:08:01','fan','2012-03-29 14:08:01','fan'),(37,39,15,'CUSTOMER','kkkk','','2012-03-30 10:10:43','fan','2012-03-30 02:10:43','fan'),(38,43,9,'CUSTOMER','','','2012-03-31 23:44:02','fan','2012-03-31 15:44:02','fan'),(39,44,1689,NULL,NULL,'职务','2012-04-09 22:21:34','importer','2012-04-09 14:21:34','importer'),(40,45,1690,'CUSTOMER',NULL,'','2012-04-09 22:33:54','importer','2012-04-09 14:33:54','importer'),(41,46,1691,'CUSTOMER',NULL,'','2012-04-09 22:33:54','importer','2012-04-09 14:33:54','importer'),(42,47,1692,'CUSTOMER',NULL,'','2012-04-09 22:33:54','importer','2012-04-09 14:33:54','importer'),(43,48,1693,'CUSTOMER',NULL,'','2012-04-09 22:33:54','importer','2012-04-09 14:33:54','importer'),(44,49,1694,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(45,50,1695,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(46,51,1696,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(47,52,1697,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(284,289,1698,'CUSTOMER','','hhh','2012-04-14 19:38:33','fan','2012-04-14 11:38:33','fan'),(49,54,1699,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(50,55,1700,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(51,56,1701,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(52,57,1702,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(53,58,1703,'CUSTOMER',NULL,'经理','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(54,59,1704,'CUSTOMER',NULL,'总经理','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(55,60,1705,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(56,61,1706,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(57,62,1707,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(58,63,1708,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(59,64,1709,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(60,65,1710,'CUSTOMER',NULL,'经理','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(61,66,1711,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(62,67,1712,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(63,68,1713,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(64,69,1714,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(65,70,1715,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(66,71,1716,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(67,72,1717,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(68,73,1718,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(69,74,1719,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(70,75,1720,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(71,76,1721,'CUSTOMER',NULL,'总经理','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(72,77,1722,'CUSTOMER',NULL,'经理','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(73,78,1723,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(74,79,1724,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(75,80,1725,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(76,81,1726,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(77,82,1727,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(78,83,1728,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(79,84,1729,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(80,85,1730,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(81,86,1731,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(82,87,1732,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(83,88,1733,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(84,89,1734,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(85,90,1735,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(86,91,1736,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(87,92,1737,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(88,93,1738,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(89,94,1739,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(90,95,1740,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(91,96,1741,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(92,97,1742,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(93,98,1743,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(94,99,1744,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(95,100,1745,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(96,101,1746,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(97,102,1747,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(98,103,1748,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(99,104,1749,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(100,105,1750,'CUSTOMER',NULL,'经理','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(101,106,1751,'CUSTOMER',NULL,'部门 经理','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(102,107,1752,'CUSTOMER',NULL,'市场部经理','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(103,108,1753,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(104,109,1754,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(105,110,1755,'CUSTOMER',NULL,'厂长','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(106,111,1756,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:55','importer'),(107,112,1757,'CUSTOMER',NULL,'','2012-04-09 22:33:55','importer','2012-04-09 14:33:56','importer'),(108,113,1758,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(109,114,1759,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(110,115,1760,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(111,116,1761,'CUSTOMER',NULL,'业务经理','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(112,117,1762,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(113,118,1763,'CUSTOMER',NULL,'业务经理','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(114,119,1764,'CUSTOMER',NULL,'部门经理','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(115,120,1765,'CUSTOMER',NULL,'经理 ','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(116,121,1766,'CUSTOMER',NULL,'经理 ','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(117,122,1767,'CUSTOMER',NULL,'总工程师 ','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(118,123,1768,'CUSTOMER',NULL,'销售经理','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(119,124,1769,'CUSTOMER',NULL,'业务经理','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(120,125,1770,'CUSTOMER',NULL,'业务部','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(121,126,1771,'CUSTOMER',NULL,'销售经理 ','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(122,127,1772,'CUSTOMER',NULL,'销售经理 ','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(123,128,1773,'CUSTOMER',NULL,'业务员','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(124,129,1774,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(125,130,1775,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(126,131,1776,'CUSTOMER',NULL,' 销售助理 ','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(127,132,1777,'CUSTOMER',NULL,'市场部','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(128,133,1778,'CUSTOMER',NULL,'市场业务','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(129,134,1779,'CUSTOMER',NULL,'业务经理','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(130,135,1780,'CUSTOMER',NULL,'销售经理 ','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(131,136,1781,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(132,137,1782,'CUSTOMER',NULL,'销售经理 ','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(133,138,1783,'CUSTOMER',NULL,'副总经理','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(134,139,1784,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(135,140,1785,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(136,141,1786,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(137,142,1787,'CUSTOMER',NULL,'主管','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(138,143,1788,'CUSTOMER',NULL,'业务经理','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(139,144,1789,'CUSTOMER',NULL,'业务经理 ','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(140,145,1790,'CUSTOMER',NULL,'业务经理 ','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(141,146,1791,'CUSTOMER',NULL,'经理','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(142,147,1792,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(143,148,1793,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(144,149,1794,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(145,150,1795,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(146,151,1796,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(147,152,1797,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(148,153,1798,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(149,154,1799,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(150,155,1800,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(151,156,1801,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(152,157,1802,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(153,158,1803,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(154,159,1804,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(155,160,1805,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(156,161,1806,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(157,162,1807,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(158,163,1808,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(159,164,1809,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(160,165,1810,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(161,166,1811,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(162,167,1812,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(163,168,1813,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(164,169,1814,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(165,170,1815,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(166,171,1816,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(167,172,1817,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(168,173,1818,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(169,174,1819,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(170,175,1820,'CUSTOMER',NULL,'运营员','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(171,176,1821,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(172,177,1822,'CUSTOMER',NULL,'总经理','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(173,178,1824,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(174,179,1825,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(175,180,1826,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(176,181,1827,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(177,182,1828,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(178,183,1829,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(179,184,1830,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(180,185,1831,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(181,186,1832,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(182,187,1833,'CUSTOMER',NULL,'','2012-04-09 22:33:56','importer','2012-04-09 14:33:56','importer'),(183,188,1835,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(184,189,1836,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(185,190,1837,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(186,191,1838,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(187,192,1839,'CUSTOMER',NULL,'市场部总监','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(188,193,1840,'CUSTOMER',NULL,'分部经理','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(189,194,1841,'CUSTOMER',NULL,'分部经理','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(190,195,1842,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(191,196,1843,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(192,197,1844,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(193,198,1845,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(194,199,1846,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(195,200,1847,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(196,201,1848,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(197,202,1849,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(198,203,1850,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(199,204,1851,'CUSTOMER',NULL,'市场总监','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(200,205,1852,'CUSTOMER',NULL,'分部经理','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(201,206,1853,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(202,207,1854,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(203,208,1855,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(204,209,1856,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(205,210,1857,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(206,211,1858,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(207,212,1859,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(208,213,1860,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(209,214,1861,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(210,215,1862,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(211,216,1863,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(212,217,1864,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(213,218,1865,'CUSTOMER',NULL,'生产部业务员','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(214,219,1866,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(215,220,1867,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(216,221,1868,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(217,222,1869,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(218,223,1870,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(219,224,1871,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(220,225,1872,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(221,226,1873,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(222,227,1874,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(223,228,1875,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(224,229,1876,'CUSTOMER',NULL,'销售工程师','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(225,230,1877,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(226,231,1878,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(227,232,1880,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(228,233,1881,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(229,234,1882,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(230,235,1883,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(231,236,1884,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(232,237,1885,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(233,238,1886,'CUSTOMER',NULL,'业务经理','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(234,239,1887,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(235,240,1888,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(236,241,1889,'CUSTOMER',NULL,'营业部经理','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(237,242,1890,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(238,243,1891,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(239,244,1892,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(240,245,1893,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(241,246,1894,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(242,247,1895,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(243,248,1896,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(244,249,1897,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(245,250,1898,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(246,251,1899,'CUSTOMER',NULL,'高级工程师','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(247,252,1900,'CUSTOMER',NULL,'销售','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(248,253,1901,'CUSTOMER',NULL,'业务经理','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(249,254,1902,'CUSTOMER',NULL,'业务负责人','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(250,255,1903,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(251,256,1904,'CUSTOMER',NULL,'经理','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(252,257,1905,'CUSTOMER',NULL,'经理','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(253,258,1906,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(254,259,1907,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(255,260,1908,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(256,261,1909,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(257,262,1910,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(258,263,1911,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(259,264,1912,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(260,265,1913,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(261,266,1914,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(262,267,1915,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(263,268,1916,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(264,269,1917,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(265,270,1918,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(266,271,1919,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(267,272,1920,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(268,273,1921,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(269,274,1922,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(270,275,1923,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(271,276,1924,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(272,277,1925,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(273,278,1926,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(274,279,1927,'CUSTOMER',NULL,'','2012-04-09 22:33:57','importer','2012-04-09 14:33:57','importer'),(275,280,1928,'CUSTOMER',NULL,'','2012-04-09 22:33:58','importer','2012-04-09 14:33:58','importer'),(276,281,1929,'CUSTOMER',NULL,'','2012-04-09 22:33:58','importer','2012-04-09 14:33:58','importer'),(277,282,1930,'CUSTOMER',NULL,'','2012-04-09 22:33:58','importer','2012-04-09 14:33:58','importer'),(278,283,1931,'CUSTOMER',NULL,'','2012-04-09 22:33:58','importer','2012-04-09 14:33:58','importer'),(279,284,1932,'CUSTOMER',NULL,'','2012-04-09 22:33:58','importer','2012-04-09 14:33:58','importer'),(280,285,1933,'CUSTOMER',NULL,'','2012-04-09 22:33:58','importer','2012-04-09 14:33:58','importer'),(281,286,1934,'CUSTOMER',NULL,'市场部经理','2012-04-09 22:33:58','importer','2012-04-09 14:33:58','importer'),(282,287,1935,'CUSTOMER',NULL,'','2012-04-09 22:33:58','importer','2012-04-09 14:33:58','importer'),(283,288,1936,'CUSTOMER',NULL,'','2012-04-09 22:33:58','importer','2012-04-09 14:33:58','importer'),(285,290,1698,'CUSTOMER','','ddd','2012-04-14 19:40:41','fan','2012-04-14 11:40:41','fan'),(286,291,1698,'CUSTOMER','','kkk','2012-04-14 19:44:55','fan','2012-04-14 11:44:55','fan');
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
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