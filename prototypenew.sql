-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: prototypenew
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `allorders`
--

DROP TABLE IF EXISTS `allorders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `allorders` (
  `orderID` varchar(45) NOT NULL,
  PRIMARY KEY (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allorders`
--

LOCK TABLES `allorders` WRITE;
/*!40000 ALTER TABLE `allorders` DISABLE KEYS */;
INSERT INTO `allorders` VALUES ('1'),('10'),('11'),('12'),('13'),('14'),('15'),('16'),('2'),('3'),('4'),('5'),('6'),('7'),('8'),('9');
/*!40000 ALTER TABLE `allorders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cancellationreport`
--

DROP TABLE IF EXISTS `cancellationreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cancellationreport` (
  `orderID` varchar(45) NOT NULL,
  `parkname` varchar(45) DEFAULT NULL,
  `visittime` datetime DEFAULT NULL,
  `visitornum` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `typeorder` enum('individual','organized group') DEFAULT NULL,
  `visitorID` varchar(45) DEFAULT NULL,
  `orderwascanceled` int DEFAULT NULL,
  PRIMARY KEY (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cancellationreport`
--

LOCK TABLES `cancellationreport` WRITE;
/*!40000 ALTER TABLE `cancellationreport` DISABLE KEYS */;
INSERT INTO `cancellationreport` VALUES ('1','temu','2024-03-22 13:00:00','2','2','2','individual','2',0),('2','temu','2024-03-22 13:00:00','2','2','2','individual','5',0),('3','temu','2023-03-26 23:53:00','4','asd','asd','individual','22',0),('4','temu','2024-03-22 13:00:00','2','2','2','organized group','6',0),('5','temu','2025-03-26 23:53:00','4','asd','asd','individual','22',0),('6','temu','2024-03-22 13:00:00','4','asd','asd','organized group','22',1);
/*!40000 ALTER TABLE `cancellationreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currentvisitors`
--

DROP TABLE IF EXISTS `currentvisitors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `currentvisitors` (
  `orderID` varchar(45) NOT NULL,
  `parkname` varchar(45) DEFAULT NULL,
  `visittime` datetime DEFAULT NULL,
  `visitornum` varchar(45) DEFAULT NULL,
  `visitorID` varchar(45) DEFAULT NULL,
  `entrancewithnoorder` int DEFAULT NULL,
  PRIMARY KEY (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currentvisitors`
--

LOCK TABLES `currentvisitors` WRITE;
/*!40000 ALTER TABLE `currentvisitors` DISABLE KEYS */;
/*!40000 ALTER TABLE `currentvisitors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `orderID` varchar(45) NOT NULL,
  `parkname` varchar(45) NOT NULL,
  `visittime` datetime NOT NULL,
  `visitorsnum` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `typeorder` enum('individual','small group','organized group') DEFAULT NULL,
  `visitorID` varchar(45) NOT NULL,
  PRIMARY KEY (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `park`
--

DROP TABLE IF EXISTS `park`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `park` (
  `parkname` varchar(45) NOT NULL,
  `maxvisitorsnumber` int DEFAULT NULL,
  `currentvisitors` int DEFAULT NULL,
  `delaytime` int DEFAULT NULL,
  `diffVisitors` int DEFAULT NULL,
  PRIMARY KEY (`parkname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `park`
--

LOCK TABLES `park` WRITE;
/*!40000 ALTER TABLE `park` DISABLE KEYS */;
INSERT INTO `park` VALUES ('akko',250,0,5,40),('Tamra',150,0,4,35),('temu',100,0,4,20);
/*!40000 ALTER TABLE `park` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkfulltimes`
--

DROP TABLE IF EXISTS `parkfulltimes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parkfulltimes` (
  `parkname` varchar(45) NOT NULL,
  `startTime` datetime NOT NULL,
  `endTime` datetime DEFAULT NULL,
  PRIMARY KEY (`parkname`,`startTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkfulltimes`
--

LOCK TABLES `parkfulltimes` WRITE;
/*!40000 ALTER TABLE `parkfulltimes` DISABLE KEYS */;
INSERT INTO `parkfulltimes` VALUES ('akko','2024-03-23 10:00:00','2024-03-23 12:00:00'),('akko','2024-03-23 12:00:00','2024-03-23 14:00:00'),('Tamra','2024-03-23 13:38:19','2024-03-23 13:38:33'),('temu','2024-03-23 20:44:19','2024-03-23 20:44:44');
/*!40000 ALTER TABLE `parkfulltimes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkwaitlist`
--

DROP TABLE IF EXISTS `parkwaitlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parkwaitlist` (
  `orderID` varchar(45) NOT NULL,
  `parkname` varchar(45) NOT NULL,
  `visittime` datetime NOT NULL,
  `visitorsnum` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `typeorder` enum('individual','small group','organized group') DEFAULT NULL,
  `visitorID` varchar(45) NOT NULL,
  PRIMARY KEY (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkwaitlist`
--

LOCK TABLES `parkwaitlist` WRITE;
/*!40000 ALTER TABLE `parkwaitlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `parkwaitlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pendingrequest`
--

DROP TABLE IF EXISTS `pendingrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pendingrequest` (
  `parkname` varchar(45) NOT NULL,
  `type` enum('maxvisitorsnumber','delaytime','diffVisitors') NOT NULL,
  `newvalue` int DEFAULT NULL,
  PRIMARY KEY (`parkname`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pendingrequest`
--

LOCK TABLES `pendingrequest` WRITE;
/*!40000 ALTER TABLE `pendingrequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `pendingrequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usereport`
--

DROP TABLE IF EXISTS `usereport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usereport` (
  `parkname` varchar(45) NOT NULL,
  `year` varchar(45) NOT NULL,
  `month` varchar(45) NOT NULL,
  PRIMARY KEY (`parkname`,`year`,`month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usereport`
--

LOCK TABLES `usereport` WRITE;
/*!40000 ALTER TABLE `usereport` DISABLE KEYS */;
INSERT INTO `usereport` VALUES ('akko','2024','3');
/*!40000 ALTER TABLE `usereport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `employeeID` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `phonenumber` varchar(45) NOT NULL,
  `role` enum('departmentManager','parkManager','worker','serviceRrepresentative') DEFAULT NULL,
  `parkname` varchar(45) NOT NULL,
  `isLogged` int DEFAULT NULL,
  PRIMARY KEY (`employeeID`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('00000000','alwaysloggeduser','alwayshere','logged1','logged1','logged1@gmail.com','0000000000','parkManager','temu',1),('019019019','adib','taha','adib','adib123','adib@belarus.com','1029384859','worker','Tamra',0),('14141414','adam','zeedan','adam','adam123','adam@gmail.com','0509182734','parkManager','temu',0),('308412345','tarek','zeedan','tarek','tarek123','tarek@goc.ac.il','0503827182','worker','akko',0),('31554668','hama','rama','hd','123456','rama@gmail.com','0514894473','departmentManager','all',0),('332211004','amin','taha','amin','amin123','amin@boneh.com','0123948567','parkManager','Tamra',0),('44332211','kareem','zeedan','shark','shark123','shark@yahoo.com','0556657503','parkManager','akko',0),('61654894','rami','taha','emp1','123456','ram@gmail.com','0505644894','worker','temu',0),('887766554','salah','khateeb','salah','salah123','salah@gmail.com','0192837485','serviceRrepresentative','all',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visitornumberreport`
--

DROP TABLE IF EXISTS `visitornumberreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visitornumberreport` (
  `parkname` varchar(45) NOT NULL,
  `visitornumberofinvidual` varchar(45) DEFAULT NULL,
  `visitorsnumberoforganizedgroup` varchar(45) DEFAULT NULL,
  `year` varchar(45) NOT NULL,
  `month` varchar(45) NOT NULL,
  PRIMARY KEY (`parkname`,`year`,`month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitornumberreport`
--

LOCK TABLES `visitornumberreport` WRITE;
/*!40000 ALTER TABLE `visitornumberreport` DISABLE KEYS */;
INSERT INTO `visitornumberreport` VALUES ('akko','2','0','2024','3'),('temu','54','222','2024','3');
/*!40000 ALTER TABLE `visitornumberreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visitors`
--

DROP TABLE IF EXISTS `visitors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visitors` (
  `visitorID` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phonenumber` varchar(45) NOT NULL,
  `typevisitor` enum('traveller','guide') DEFAULT NULL,
  `isLogged` int DEFAULT NULL,
  PRIMARY KEY (`visitorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitors`
--

LOCK TABLES `visitors` WRITE;
/*!40000 ALTER TABLE `visitors` DISABLE KEYS */;
INSERT INTO `visitors` VALUES ('1','ahmad','ataba','a@gmail.com','0505304969','traveller',0),('2','jad','taha','j@gmail.com','0500055694','guide',0),('alwayslogged','alwaysvisitor','loggedalways','logged@gmail.com','0000000000','traveller',1);
/*!40000 ALTER TABLE `visitors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visitslogs`
--

DROP TABLE IF EXISTS `visitslogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visitslogs` (
  `orderID` varchar(45) NOT NULL,
  `parkname` varchar(45) DEFAULT NULL,
  `entryTime` datetime DEFAULT NULL,
  `exitTime` datetime DEFAULT NULL,
  `orderType` enum('individual','small group','organized group') DEFAULT NULL,
  `visitorsnum` int DEFAULT NULL,
  PRIMARY KEY (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitslogs`
--

LOCK TABLES `visitslogs` WRITE;
/*!40000 ALTER TABLE `visitslogs` DISABLE KEYS */;
INSERT INTO `visitslogs` VALUES ('1','temu','2024-03-22 15:48:23','2024-03-22 15:48:23','small group',5),('10','akko','2024-03-22 23:01:15','2024-03-23 13:21:55','small group',2),('13','temu','2024-03-23 20:40:49','2024-03-23 20:41:19','small group',2),('14','temu','2024-03-23 20:43:47','2024-03-23 23:44:44','small group',80),('15','Tamra','2024-03-23 13:38:19','2024-03-23 15:38:19','small group',2),('2','Tamra','2024-03-22 21:17:00','2024-03-22 23:17:00','small group',20),('7','Tamra','2024-03-22 22:47:34','2024-03-22 23:47:34','small group',10),('8','Tamra','2024-03-22 20:01:15','2024-03-22 23:01:15','individual',30),('9','Tamra','2024-03-22 13:38:19','2024-03-22 16:38:33','small group',20);
/*!40000 ALTER TABLE `visitslogs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visitsreport`
--

DROP TABLE IF EXISTS `visitsreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visitsreport` (
  `reportID` varchar(45) NOT NULL,
  `parkname` varchar(45) NOT NULL,
  `year` int DEFAULT NULL,
  `month` int DEFAULT NULL,
  `day` varchar(45) DEFAULT NULL,
  `below3ordersoofentrancetime` int DEFAULT NULL,
  `4to8ordersofentrancetime` int DEFAULT NULL,
  `9to12ordersofentrancetime` int DEFAULT NULL,
  `13to15ordersofentrancetime` int DEFAULT NULL,
  `below3ordersoofdelaytime` int DEFAULT NULL,
  `4to8ordersofdelaytime` int DEFAULT NULL,
  `9to12ordersofdelaytime` int DEFAULT NULL,
  `13to15ordersofdelaytime` int DEFAULT NULL,
  `departmentmanagerID` int DEFAULT NULL,
  PRIMARY KEY (`reportID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitsreport`
--

LOCK TABLES `visitsreport` WRITE;
/*!40000 ALTER TABLE `visitsreport` DISABLE KEYS */;
/*!40000 ALTER TABLE `visitsreport` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-29 23:15:53
