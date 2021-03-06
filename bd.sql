CREATE DATABASE  IF NOT EXISTS `bd_autobuses` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci */;
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
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nif` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `nombre` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `apellidos` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `email` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `password` varbinary(250) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nif_UNIQUE` (`nif`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
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
INSERT INTO `estaciones` VALUES (1,'Albacete',NULL),(2,'Almansa',NULL),(3,'Cenizate',NULL),(4,'Villamalea',NULL),(5,'Fuentealbilla',NULL),(6,'Chinchilla',NULL),(7,'Alcala del Jucar',NULL),(8,'Villarta',NULL),(9,'Iniesta',NULL),(10,'Valencia',NULL);
/*!40000 ALTER TABLE `estaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facturas`
--

DROP TABLE IF EXISTS `facturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `facturas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `viajebackup` int(11) NOT NULL,
  `tarjeta` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tarjetaid_idx` (`tarjeta`),
  KEY `viajeid_idx` (`viajebackup`),
  CONSTRAINT `tarjetaid` FOREIGN KEY (`tarjeta`) REFERENCES `tarjetas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `viajeid` FOREIGN KEY (`viajebackup`) REFERENCES `viajes_backup` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facturas`
--

LOCK TABLES `facturas` WRITE;
/*!40000 ALTER TABLE `facturas` DISABLE KEYS */;
/*!40000 ALTER TABLE `facturas` ENABLE KEYS */;
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
-- Table structure for table `ocupacion`
--

DROP TABLE IF EXISTS `ocupacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ocupacion` (
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
-- Dumping data for table `ocupacion`
--

LOCK TABLES `ocupacion` WRITE;
/*!40000 ALTER TABLE `ocupacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `ocupacion` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutas`
--

LOCK TABLES `rutas` WRITE;
/*!40000 ALTER TABLE `rutas` DISABLE KEYS */;
INSERT INTO `rutas` VALUES (1,1,2,14.94),(10,1,4,10.72),(11,1,6,3.14),(12,2,10,27.8),(13,2,8,23.2),(14,2,3,22.4),(15,3,1,9.6),(16,3,5,2.68),(17,3,2,22),(18,3,4,4.32),(19,4,1,10.66),(20,4,6,13.32),(21,4,7,4.54),(22,4,10,5),(23,5,6,12.42),(24,5,3,2.68),(25,5,8,25),(26,5,1,9.2),(27,6,7,10.44),(28,6,4,13.2),(29,6,2,12.52),(30,6,1,3.42),(31,7,1,12.74),(32,7,2,10.62),(33,7,9,10.66),(34,7,4,4.44),(35,8,9,26.8),(36,8,10,25.4),(37,8,1,38.4),(38,8,4,28.4),(39,9,10,2.3),(40,9,8,27),(41,9,3,25.6),(42,9,5,6.6),(43,10,9,2.3),(44,10,1,13.52),(45,10,5,5.06),(46,10,6,16.52);
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
  `horaLLegada` time DEFAULT NULL,
  `ruta` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_rutaHorario_idx` (`ruta`),
  KEY `id_horaHorario_idx` (`horaSalida`),
  CONSTRAINT `id_rutaHorario` FOREIGN KEY (`ruta`) REFERENCES `rutas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutas_horarios`
--

LOCK TABLES `rutas_horarios` WRITE;
/*!40000 ALTER TABLE `rutas_horarios` DISABLE KEYS */;
INSERT INTO `rutas_horarios` VALUES (1,1,'10:00:00',1),(2,3,'11:00:00',1),(3,3,'13:15:00',10),(4,5,'17:15:00',10),(5,7,'22:00:00',10),(6,2,'10:27:00',11),(7,4,'13:40:00',11),(8,6,'19:40:00',11),(9,1,'08:19:00',12),(10,3,'13:40:00',12),(11,6,'20:40:00',12),(12,2,'11:30:00',13),(13,5,'19:40:00',13),(14,2,'11:23:00',14),(15,5,'19:40:00',14),(16,4,'13:40:00',15),(17,7,'21:50:00',15),(18,4,'13:32:00',16),(19,6,'19:42:00',16),(20,1,'10:23:00',17),(21,2,'11:23:00',17),(22,4,'13:40:00',18),(23,5,'18:50:00',18),(24,1,'09:45:00',19),(25,3,'13:15:00',19),(26,5,'19:15:00',20),(27,6,'20:20:00',20),(28,2,'10:30:00',21),(29,4,'23:50:00',21),(30,5,'20:00:00',22),(31,7,'22:50:00',22),(32,4,'14:15:00',23),(33,5,'19:15:00',23),(34,2,'10:13:00',24),(35,4,'13:35:00',24),(36,2,'10:23:00',25),(37,4,'13:45:00',25),(38,6,'20:10:00',26),(39,7,'21:45:00',26),(40,1,'09:45:00',27),(41,2,'10:45:00',27),(42,4,'14:00:00',28),(43,6,'20:15:00',28),(44,3,'13:05:00',29),(45,6,'20:05:00',29),(46,4,'13:50:00',30),(47,7,'21:50:00',30),(48,2,'10:52:00',31),(49,5,'19:20:00',31),(50,1,'09:43:00',32),(51,5,'19:10:00',32),(52,2,'10:54:00',33),(53,5,'00:00:19',33),(54,7,'09:28:00',34),(55,4,'13:40:00',34),(56,1,'09:12:00',35),(57,4,'13:35:00',35),(58,1,'10:30:00',36),(59,3,'14:00:00',36),(60,2,'11:00:00',37),(61,7,'22:20:00',37),(62,3,'12:50:00',38),(63,4,'13:55:00',38),(64,1,'10:27:00',39),(65,7,'23:00:00',39),(66,3,'12:40:00',40),(67,5,'18:45:00',40),(68,1,'09:11:00',41),(69,2,'10:11:00',41),(70,4,'13:52:00',42),(71,5,'18:55:00',42),(72,1,'10:28:00',43),(73,3,'14:00:00',43),(74,1,'11:00:00',44),(75,5,'20:00:00',44),(76,6,'20:30:00',45),(77,3,'14:00:00',45),(78,1,'11:00:00',46),(79,2,'12:00:00',46);
/*!40000 ALTER TABLE `rutas_horarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjetas`
--

DROP TABLE IF EXISTS `tarjetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarjetas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numero` varbinary(250) NOT NULL,
  `fechaCaducidad` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `tipoTarjeta` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `idCliente` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_cliente_idx` (`idCliente`),
  CONSTRAINT `id_cliente` FOREIGN KEY (`idCliente`) REFERENCES `clientes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjetas`
--

LOCK TABLES `tarjetas` WRITE;
/*!40000 ALTER TABLE `tarjetas` DISABLE KEYS */;
/*!40000 ALTER TABLE `tarjetas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viajeros`
--

DROP TABLE IF EXISTS `viajeros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `viajeros` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nif` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `nombre` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `apellidos` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nif_UNIQUE` (`nif`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viajeros`
--

LOCK TABLES `viajeros` WRITE;
/*!40000 ALTER TABLE `viajeros` DISABLE KEYS */;
/*!40000 ALTER TABLE `viajeros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viajeros_backup`
--

DROP TABLE IF EXISTS `viajeros_backup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `viajeros_backup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nif` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `nombre` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `apellidos` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nif_UNIQUE` (`nif`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viajeros_backup`
--

LOCK TABLES `viajeros_backup` WRITE;
/*!40000 ALTER TABLE `viajeros_backup` DISABLE KEYS */;
/*!40000 ALTER TABLE `viajeros_backup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viajeros_viajes`
--

DROP TABLE IF EXISTS `viajeros_viajes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `viajeros_viajes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idViaje` int(11) NOT NULL,
  `idViajero` int(11) NOT NULL,
  `asiento` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_viajero_idx` (`idViajero`),
  KEY `id_viaje_idx` (`idViaje`),
  CONSTRAINT `id_viajeros` FOREIGN KEY (`idViajero`) REFERENCES `viajeros` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_viajes` FOREIGN KEY (`idViaje`) REFERENCES `viajes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viajeros_viajes`
--

LOCK TABLES `viajeros_viajes` WRITE;
/*!40000 ALTER TABLE `viajeros_viajes` DISABLE KEYS */;
/*!40000 ALTER TABLE `viajeros_viajes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viajeros_viajes_backup`
--

DROP TABLE IF EXISTS `viajeros_viajes_backup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `viajeros_viajes_backup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idViaje_backup` int(11) NOT NULL,
  `idViajero_backup` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_viaje_back_idx` (`idViaje_backup`),
  CONSTRAINT `id_viaje_back` FOREIGN KEY (`idViaje_backup`) REFERENCES `viajes_backup` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viajeros_viajes_backup`
--

LOCK TABLES `viajeros_viajes_backup` WRITE;
/*!40000 ALTER TABLE `viajeros_viajes_backup` DISABLE KEYS */;
/*!40000 ALTER TABLE `viajeros_viajes_backup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viajes`
--

DROP TABLE IF EXISTS `viajes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `viajes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idViaje` int(11) NOT NULL,
  `precio` double NOT NULL,
  `metodoPago` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `codigo` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cliente_id_idx` (`metodoPago`),
  KEY `rutas_horarios_id_idx` (`idViaje`),
  CONSTRAINT `cliente_id` FOREIGN KEY (`metodoPago`) REFERENCES `tarjetas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `rutas_horarios_id` FOREIGN KEY (`idViaje`) REFERENCES `rutas_horarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viajes`
--

LOCK TABLES `viajes` WRITE;
/*!40000 ALTER TABLE `viajes` DISABLE KEYS */;
/*!40000 ALTER TABLE `viajes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viajes_backup`
--

DROP TABLE IF EXISTS `viajes_backup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `viajes_backup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idRuta` int(11) NOT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idRutas_idx` (`idRuta`),
  CONSTRAINT `idRutas` FOREIGN KEY (`idRuta`) REFERENCES `rutas_horarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viajes_backup`
--

LOCK TABLES `viajes_backup` WRITE;
/*!40000 ALTER TABLE `viajes_backup` DISABLE KEYS */;
/*!40000 ALTER TABLE `viajes_backup` ENABLE KEYS */;
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

-- Dump completed on 2018-05-08 21:10:15
