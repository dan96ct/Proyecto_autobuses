CREATE DATABASE  IF NOT EXISTS `bd_autobuses` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `bd_autobuses`;
-- MySQL dump 10.13  Distrib 5.7.20, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: bd_autobuses
-- ------------------------------------------------------
-- Server version	5.7.20

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
-- Table structure for table `autobuses`
--

DROP TABLE IF EXISTS `autobuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `autobuses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numPlazas` int(11) NOT NULL,
  `matricula` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autobuses`
--

LOCK TABLES `autobuses` WRITE;
/*!40000 ALTER TABLE `autobuses` DISABLE KEYS */;
/*!40000 ALTER TABLE `autobuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estaciones`
--

DROP TABLE IF EXISTS `estaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estaciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `coordenadas` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estaciones`
--

LOCK TABLES `estaciones` WRITE;
/*!40000 ALTER TABLE `estaciones` DISABLE KEYS */;
INSERT INTO `estaciones` VALUES (1,'Albacete',NULL),(2,'Almansa',NULL),(3,'Cenizate',NULL),(4,'Casas Ibañez',NULL),(5,'Fuentealbilla',NULL),(6,'Chinchilla',NULL),(7,'Alacalá del Jucar',NULL),(8,'Madrid',NULL),(9,'Barcelona',NULL),(10,'Valencia',NULL);
/*!40000 ALTER TABLE `estaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `horarios`
--

DROP TABLE IF EXISTS `horarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `horarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hora` time NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horarios`
--

LOCK TABLES `horarios` WRITE;
/*!40000 ALTER TABLE `horarios` DISABLE KEYS */;
INSERT INTO `horarios` VALUES (1,'09:00:00'),(2,'10:00:00'),(3,'12:30:00'),(4,'13:20:00'),(5,'18:30:00'),(6,'19:30:00'),(7,'21:20:00');
/*!40000 ALTER TABLE `horarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plazas_ocupadas`
--

DROP TABLE IF EXISTS `plazas_ocupadas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plazas_ocupadas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dia` date NOT NULL,
  `plazasOcupadas` int(11) NOT NULL,
  `rutas_horarios` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_rutaHorario_idx` (`rutas_horarios`),
  CONSTRAINT `id_rutaPlazasOcupadas` FOREIGN KEY (`rutas_horarios`) REFERENCES `rutas_horarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plazas_ocupadas`
--

LOCK TABLES `plazas_ocupadas` WRITE;
/*!40000 ALTER TABLE `plazas_ocupadas` DISABLE KEYS */;
/*!40000 ALTER TABLE `plazas_ocupadas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rutas`
--

DROP TABLE IF EXISTS `rutas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rutas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `origen` int(11) NOT NULL,
  `destino` int(11) NOT NULL,
  `precio` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_origen_idx` (`origen`),
  KEY `id_destino_idx` (`destino`),
  CONSTRAINT `id_destino` FOREIGN KEY (`destino`) REFERENCES `estaciones` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_origen` FOREIGN KEY (`origen`) REFERENCES `estaciones` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutas`
--

LOCK TABLES `rutas` WRITE;
/*!40000 ALTER TABLE `rutas` DISABLE KEYS */;
INSERT INTO `rutas` VALUES (1,1,2,NULL),(2,2,1,NULL),(3,3,4,NULL),(4,5,3,NULL),(5,3,5,NULL),(6,7,8,NULL),(7,7,9,NULL),(8,1,9,NULL);
/*!40000 ALTER TABLE `rutas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rutas_horarios`
--

DROP TABLE IF EXISTS `rutas_horarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rutas_horarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `horaSalida` int(11) NOT NULL,
  `horaLLegada` time(6) DEFAULT NULL,
  `ruta` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_rutaHorario_idx` (`ruta`),
  KEY `id_horaHorario_idx` (`horaSalida`),
  CONSTRAINT `id_rutaHorario` FOREIGN KEY (`ruta`) REFERENCES `rutas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutas_horarios`
--

LOCK TABLES `rutas_horarios` WRITE;
/*!40000 ALTER TABLE `rutas_horarios` DISABLE KEYS */;
INSERT INTO `rutas_horarios` VALUES (1,1,'09:20:00.000000',3),(2,2,'10:20:00.000000',3),(3,3,'12:50:00.000000',3),(4,1,'09:15:00.000000',4),(5,6,'20:40:00.000000',2),(6,4,'13:40:00.000000',5),(7,3,'13:40:00.000000',2);
/*!40000 ALTER TABLE `rutas_horarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bd_autobuses'
--

--
-- Dumping routines for database 'bd_autobuses'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-21 19:02:19
