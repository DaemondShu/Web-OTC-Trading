CREATE DATABASE  IF NOT EXISTS `otc` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `otc`;
-- MySQL dump 10.13  Distrib 5.6.28, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: otc
-- ------------------------------------------------------
-- Server version	5.6.28-0ubuntu0.15.04.1

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
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `isSell` tinyint(1) DEFAULT '1',
  `status` varchar(45) NOT NULL COMMENT 'TODO DOING DONE CANCEL',
  `type` varchar(45) NOT NULL COMMENT 'MARKET,LIMIT,STOP,CANCEL',
  `time` varchar(45) NOT NULL,
  `condition` varchar(45) NOT NULL,
  `expectedVol` int(11) DEFAULT NULL COMMENT 'expected volume (total volume when create) to deal',
  `surplusVol` int(11) DEFAULT NULL COMMENT 'remain xx vol to deal',
  `price` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sellOrder_orderId_index` (`id`),
  KEY `sellOrder_isSell_index` (`isSell`),
  KEY `order_price_index` (`price`),
  KEY `sellOrder_productItem_id_fk` (`productId`),
  KEY `order_user_id_fk` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (6,1,4,1,'TODO','LIMIT','2016-05-30 00:11:35','500,2000',100,100,98.5),(7,1,4,1,'DOING','STOP','2016-05-30 00:11:37',',2000',100,70,100.5),(8,1,4,1,'DOING','MARKET','2016-05-30 00:22:02',',',100,100,120.5),(10,1,4,1,'CANCEL','MARKET','2016-05-30 03:25:34','0,2000',100,100,100.5),(11,1,4,1,'DOING','MARKET','2016-05-30 14:27:51',',',100,50,120),(12,1,4,1,'DONE','MARKET','2016-05-30 14:51:05',',1000',100,0,111),(13,1,4,1,'DOING','MARKET','2016-05-30 17:18:43','2000,',100,100,100.5),(14,1,4,1,'DOING','MARKET','2016-05-31 23:54:49',',',100,100,100.5),(15,2,4,1,'DOING','MARKET','2016-06-01 00:02:06',',',100,100,100.5),(16,2,4,1,'DOING','MARKET','2016-06-01 00:03:01',',',100,100,99.5),(17,2,4,1,'DOING','MARKET','2016-06-01 00:03:53',',',100,100,100.5),(18,2,4,1,'DOING','MARKET','2016-06-01 00:05:57',',',100,100,100.5),(19,3,4,1,'DOING','MARKET','2016-06-01 00:06:33',',',100,100,100.5),(20,1,5,0,'DONE','MARKET','2016-06-01 02:28:54',',',30,0,NULL),(21,1,5,0,'DOING','LIMIT','2016-06-01 02:40:23','200,500',30,30,NULL),(26,1,5,1,'DOING','MARKET','2016-06-01 02:48:32',',',100,100,100.5),(54,1,5,0,'DONE','LIMIT','2016-06-01 13:26:43','110,500',150,0,NULL);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `kind` varchar(45) NOT NULL,
  `brokerId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_id_uindex` (`id`),
  KEY `product_kind_index` (`kind`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Oil Liquids','Energy',2),(2,'Natural Gas','Energy',3),(3,'Natural Gas','Energy',2),(4,'Gold','Metal',2),(5,'Silver','Metal',2),(6,'Copper','Metal',3),(7,'Aluminum','Metal',3),(8,'Electricity','Energy',2),(14,'temp','temp',0),(15,'temp2','temp2',0),(16,'temp','temp',0),(17,'temp2','temp2',0);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trade`
--

DROP TABLE IF EXISTS `trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `buyOrderId` int(11) DEFAULT NULL,
  `sellOrderId` int(11) DEFAULT NULL,
  `time` varchar(45) NOT NULL COMMENT 'trade closing time',
  `quantity` int(11) DEFAULT '0',
  `productId` int(11) DEFAULT NULL,
  `buyerId` int(11) DEFAULT NULL,
  `sellerId` int(11) DEFAULT NULL,
  `brokerId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `trade_id_uindex` (`id`),
  KEY `trade_time_index` (`time`),
  KEY `trade_order_id_fk` (`sellOrderId`),
  KEY `trade_sellOrder_id_fk` (`buyOrderId`),
  KEY `trade_productId_index` (`productId`),
  KEY `trade_brokerId_index` (`brokerId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade`
--

LOCK TABLES `trade` WRITE;
/*!40000 ALTER TABLE `trade` DISABLE KEYS */;
INSERT INTO `trade` VALUES (1,20,7,'2016-06-01 02:29:54',30,1,5,4,2),(5,29,12,'2016-06-01 02:55:26',20,1,5,4,2),(10,54,12,'2016-06-01 13:26:43',100,1,5,4,2),(11,54,11,'2016-06-01 13:26:43',50,1,5,4,2);
/*!40000 ALTER TABLE `trade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  `company` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_uindex` (`username`),
  UNIQUE KEY `user_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin','ADMIN','root'),(2,'brokerM','brokerM','BROKER','M'),(3,'brokerT','brokerT','BROKER','T'),(4,'trader1','trader1','TRADER','companyA'),(5,'trader2','trader2','TRADER','companyB');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-01 13:30:22
