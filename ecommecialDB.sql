CREATE DATABASE  IF NOT EXISTS `mydatabase` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mydatabase`;
-- MySQL dump 10.13  Distrib 8.0.19, for macos10.15 (x86_64)
--
-- Host: localhost    Database: mydatabase
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,'Rolex'),(2,'Casio'),(3,'Seiko');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_product`
--

DROP TABLE IF EXISTS `cart_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK66bvly2ku8gmnq4ajawmls531` (`user_id`),
  KEY `FK2kdlr8hs2bwl14u8oop49vrxi` (`product_id`),
  CONSTRAINT `FK2kdlr8hs2bwl14u8oop49vrxi` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK66bvly2ku8gmnq4ajawmls531` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_product`
--

LOCK TABLES `cart_product` WRITE;
/*!40000 ALTER TABLE `cart_product` DISABLE KEYS */;
INSERT INTO `cart_product` VALUES (1,1,2,4),(3,1,3,5),(10,4,1,7);
/*!40000 ALTER TABLE `cart_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'man'),(2,'women'),(3,'kid');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `image` varchar(300) NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6oo0cvcdtb6qmwsga468uuukk` (`product_id`),
  CONSTRAINT `FK6oo0cvcdtb6qmwsga468uuukk` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,'image1',1),(2,'image2',1),(3,'image3',1),(4,'image5',2),(5,'image6',3);
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_product`
--

DROP TABLE IF EXISTS `order_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_product` (
  `id` varchar(200) NOT NULL,
  `order_id` varchar(200) NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `unit_price` int NOT NULL,
  `total_price` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cfdvveveeve_idx` (`order_id`),
  CONSTRAINT `cfdvveveeve` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_product`
--

LOCK TABLES `order_product` WRITE;
/*!40000 ALTER TABLE `order_product` DISABLE KEYS */;
INSERT INTO `order_product` VALUES ('15720326-7a50-405c-bd0a-46a266888cf6','c251479e-9790-4dd2-a10e-dbf17e9da2ec',1,4,'Product1',1000000,4000000),('21fb0cb0-c7e7-4f0f-8faf-493c44f3945a','d79bad9a-2431-45a3-b7fc-3f62b09600b9',2,1,'Product2',2000000,2000000),('402dc5b5-0cc6-4c2f-bff8-612e30966d78','208288c0-d605-4b67-90e8-37fa30d66af1',3,4,'Product3',3000000,12000000),('6b0c61cb-c10f-4b4c-a59b-e86dcd664057','c251479e-9790-4dd2-a10e-dbf17e9da2ec',3,4,'Product3',3000000,12000000),('8df2072f-91e5-4e64-b782-a2b09acd2b23','c251479e-9790-4dd2-a10e-dbf17e9da2ec',2,1,'Product2',2000000,2000000),('97c46020-48cc-4000-adb6-3e39f06d78ce','d79bad9a-2431-45a3-b7fc-3f62b09600b9',1,4,'Product1',1000000,4000000),('ae839849-be3c-47e2-90cd-63218c669607','d79bad9a-2431-45a3-b7fc-3f62b09600b9',3,4,'Product3',3000000,12000000),('b050bc32-4a82-44de-87b2-b8672b609751','208288c0-d605-4b67-90e8-37fa30d66af1',2,1,'Product2',2000000,2000000),('c42b2994-d24f-4206-a6cd-21dbfd070cdc','0321136d-7a42-40d0-8fac-55717576a415',2,1,'Product2',2000000,2000000),('d482c427-8615-4b88-8ad4-82aaf9f7d64b','0321136d-7a42-40d0-8fac-55717576a415',3,4,'Product3',3000000,12000000),('d9a76184-8387-4c84-873a-cbc3c36f6f15','0321136d-7a42-40d0-8fac-55717576a415',1,4,'Product1',1000000,4000000),('e666cbda-94f1-4246-842f-1f836d3f7aa9','f3ba026e-a484-4c76-85e3-42e90719c664',3,4,'Product3',3000000,12000000),('e9b1be25-d233-4249-90f3-727609db991d','208288c0-d605-4b67-90e8-37fa30d66af1',1,4,'Product1',1000000,4000000);
/*!40000 ALTER TABLE `order_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` varchar(200) NOT NULL,
  `user_id` int NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `address` varchar(300) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `total_price` int NOT NULL,
  `shipping_price` int NOT NULL,
  `final_price` int NOT NULL,
  `date` datetime NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('0321136d-7a42-40d0-8fac-55717576a415',1,'Nguyen Quang Hung','Hanoi','0868724828',18000000,0,18000000,'2021-08-06 00:00:00','Success Ordered'),('208288c0-d605-4b67-90e8-37fa30d66af1',1,'Nguyen Quang Hung','Hanoi','0868724828',18000000,0,18000000,'2021-08-06 00:00:00','Success Ordered'),('28e40613-45ca-4a8d-8c28-9beb719e6b9b',1,'Nguyen Quang Hung','Hanoi','0868724828',18000000,0,18000000,'2021-08-06 00:00:00','Success Ordered'),('425d4c55-c752-4a02-9fe3-36d2a1fbc4b1',1,'Nguyen Quang Hung','Hanoi','0868724828',18000000,0,18000000,'2021-08-06 00:00:00','Success Ordered'),('8d960a41-a771-4ae1-a2b3-aa0659913cfe',1,'Nguyen Quang Hung','Hanoi','0868724828',18000000,0,18000000,'2021-08-06 00:00:00','Success Ordered'),('c251479e-9790-4dd2-a10e-dbf17e9da2ec',1,'Nguyen Quang Hung','Hanoi','0868724828',18000000,0,18000000,'2021-08-06 00:00:00','Success Ordered'),('d79bad9a-2431-45a3-b7fc-3f62b09600b9',1,'Nguyen Quang Hung','Hanoi','0868724828',18000000,0,18000000,'2021-08-06 00:00:00','Success Ordered'),('f3ba026e-a484-4c76-85e3-42e90719c664',2,'Nguyen Quang Hung','Hanoi','0868724829',12000000,0,12000000,'2021-08-07 00:00:00','Success Ordered');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `price` int NOT NULL,
  `brand_id` int DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  KEY `FKs6cydsualtsrprvlf2bb3lcam` (`brand_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKs6cydsualtsrprvlf2bb3lcam` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Product1',1000000,1,1),(2,'Product2',2000000,2,2),(3,'Product3',3000000,3,3);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(200) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `address` varchar(100) NOT NULL,
  `role` varchar(45) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'useruser1','$2a$12$E.UQDUpfgoLVwR.TTJTrIORf6h40JUOsMWlH2ZxUBhDLWyenXD3kq','Nguyen Quang Hung','quanghungnl99@gmail.com','0868724828','Hanoi','USER',NULL),(2,'useruser2','$2a$12$xR8cSdB6mALgN737QN2q7uFlXLkeizz4n5ViyBWw5AlcLOOAAoMI6','Nguyen Quang Hung','quanghungnl999@gmail.com','0868724829','Hanoi','USER',NULL),(3,'useruser3','$2a$12$xR8cSdB6mALgN737QN2q7uFlXLkeizz4n5ViyBWw5AlcLOOAAoMI6','Nguyen Quang Hung','quanghungnl9999@gmail.com','0868724830','HCM','USER',NULL),(4,'useruser4','$2a$12$xR8cSdB6mALgN737QN2q7uFlXLkeizz4n5ViyBWw5AlcLOOAAoMI6','Nguyen Quang Hung','quanghungnl99999@gmail.com','0868724831','Da Nang','USER',NULL);
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

-- Dump completed on 2021-08-07 12:54:56
