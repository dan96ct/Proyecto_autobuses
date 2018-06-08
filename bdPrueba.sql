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
-- Table structure for table `backup_ocupacion`
--

DROP TABLE IF EXISTS `backup_ocupacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `backup_ocupacion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_backup_reservas` int(11) NOT NULL,
  `id_backup_viajeros` int(11) NOT NULL,
  `asiento` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_backup_reservas_idx` (`id_backup_reservas`),
  KEY `id_backup_viajeros_idx` (`id_backup_viajeros`),
  CONSTRAINT `id_backup_reservas` FOREIGN KEY (`id_backup_reservas`) REFERENCES `backup_reservas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_backup_viajeros` FOREIGN KEY (`id_backup_viajeros`) REFERENCES `backup_viajeros` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `backup_ocupacion`
--

LOCK TABLES `backup_ocupacion` WRITE;
/*!40000 ALTER TABLE `backup_ocupacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `backup_ocupacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `backup_reservas`
--

DROP TABLE IF EXISTS `backup_reservas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `backup_reservas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_backup_viajes` int(11) NOT NULL,
  `id_tarjeta` int(11) NOT NULL,
  `precio` double NOT NULL,
  `codigo` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_backup_viajes_idx` (`id_backup_viajes`),
  KEY `id_tarjeta_2_idx` (`id_tarjeta`),
  CONSTRAINT `id_backup_viajes` FOREIGN KEY (`id_backup_viajes`) REFERENCES `backup_viajes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_tarjeta_2` FOREIGN KEY (`id_tarjeta`) REFERENCES `tarjetas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `backup_reservas`
--

LOCK TABLES `backup_reservas` WRITE;
/*!40000 ALTER TABLE `backup_reservas` DISABLE KEYS */;
/*!40000 ALTER TABLE `backup_reservas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `backup_viajeros`
--

DROP TABLE IF EXISTS `backup_viajeros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `backup_viajeros` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nif` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `nombre` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `apellidos` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nif_UNIQUE` (`nif`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `backup_viajeros`
--

LOCK TABLES `backup_viajeros` WRITE;
/*!40000 ALTER TABLE `backup_viajeros` DISABLE KEYS */;
/*!40000 ALTER TABLE `backup_viajeros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `backup_viajes`
--

DROP TABLE IF EXISTS `backup_viajes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `backup_viajes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_rutas_horarios` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `plazasOcupadas` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_rutas_horarios_2_idx` (`id_rutas_horarios`),
  CONSTRAINT `id_rutas_horarios_2` FOREIGN KEY (`id_rutas_horarios`) REFERENCES `rutas_horarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `backup_viajes`
--

LOCK TABLES `backup_viajes` WRITE;
/*!40000 ALTER TABLE `backup_viajes` DISABLE KEYS */;
/*!40000 ALTER TABLE `backup_viajes` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'74524306M','Daniel','Cebrian','d.cebrian9@gmail.com','\� ��\n�$\��O��ZF');
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estaciones`
--

LOCK TABLES `estaciones` WRITE;
/*!40000 ALTER TABLE `estaciones` DISABLE KEYS */;
INSERT INTO `estaciones` VALUES (1,'Albacete',NULL),(2,'Almansa',NULL),(4,'Villamalea',NULL);
/*!40000 ALTER TABLE `estaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocupacion`
--

DROP TABLE IF EXISTS `ocupacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ocupacion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_reserva` int(11) NOT NULL,
  `id_viajero` int(11) NOT NULL,
  `asiento` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_rutaHorario_idx` (`id_reserva`),
  KEY `id_viajero_idx` (`id_viajero`),
  CONSTRAINT `id_reserva` FOREIGN KEY (`id_reserva`) REFERENCES `reservas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_viajero` FOREIGN KEY (`id_viajero`) REFERENCES `viajeros` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocupacion`
--

LOCK TABLES `ocupacion` WRITE;
/*!40000 ALTER TABLE `ocupacion` DISABLE KEYS */;
INSERT INTO `ocupacion` VALUES (1,1,1,1),(2,2,1,2),(3,3,1,3),(4,4,2,1);
/*!40000 ALTER TABLE `ocupacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservas`
--

DROP TABLE IF EXISTS `reservas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reservas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_viaje` int(11) NOT NULL,
  `id_tarjeta` int(11) NOT NULL,
  `precio` double NOT NULL,
  `codigo` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cliente_id_idx` (`id_tarjeta`),
  KEY `id_rutas_horarios2_idx` (`id_viaje`),
  CONSTRAINT `cliente_id` FOREIGN KEY (`id_tarjeta`) REFERENCES `tarjetas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_viajes` FOREIGN KEY (`id_viaje`) REFERENCES `viajes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservas`
--

LOCK TABLES `reservas` WRITE;
/*!40000 ALTER TABLE `reservas` DISABLE KEYS */;
INSERT INTO `reservas` VALUES (1,1,1,5.6,'6eif4jc'),(2,1,1,5.6,'fbhbb1d'),(3,1,1,5.6,'d562gi1'),(4,4,2,7.2,'5234hjk');
/*!40000 ALTER TABLE `reservas` ENABLE KEYS */;
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
  `precio` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_origen_idx` (`origen`),
  KEY `id_destino_idx` (`destino`),
  CONSTRAINT `id_destino` FOREIGN KEY (`destino`) REFERENCES `estaciones` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_origen` FOREIGN KEY (`origen`) REFERENCES `estaciones` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutas`
--

LOCK TABLES `rutas` WRITE;
/*!40000 ALTER TABLE `rutas` DISABLE KEYS */;
INSERT INTO `rutas` VALUES (47,1,2,5.6),(48,2,1,5.6),(49,4,1,7.2),(50,1,4,7.2);
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
  `horaSalida` time NOT NULL,
  `horaLLegada` time NOT NULL,
  `ruta` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_rutaHorario_idx` (`ruta`),
  KEY `id_horaHorario_idx` (`horaSalida`),
  CONSTRAINT `id_rutaHorario` FOREIGN KEY (`ruta`) REFERENCES `rutas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutas_horarios`
--

LOCK TABLES `rutas_horarios` WRITE;
/*!40000 ALTER TABLE `rutas_horarios` DISABLE KEYS */;
INSERT INTO `rutas_horarios` VALUES (80,'10:00:00','11:00:00',47),(81,'18:00:00','19:00:00',48),(82,'09:00:00','09:20:00',49),(83,'08:00:00','08:20:00',50),(84,'07:00:00','08:00:00',47);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjetas`
--

LOCK TABLES `tarjetas` WRITE;
/*!40000 ALTER TABLE `tarjetas` DISABLE KEYS */;
INSERT INTO `tarjetas` VALUES (1,'�<\�n�)?�p\"3�b�K\\��PyBK��6c��P=','2018-05','MasterCard',1),(2,'Z�3\�`\�\����V(��\\��PyBK��6c��P=','2018-05','MasterCard',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viajeros`
--

LOCK TABLES `viajeros` WRITE;
/*!40000 ALTER TABLE `viajeros` DISABLE KEYS */;
INSERT INTO `viajeros` VALUES (1,'74524306M','Daniel','Cebrian'),(2,'97956234Q','daniel','cebrian');
/*!40000 ALTER TABLE `viajeros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viajes`
--

DROP TABLE IF EXISTS `viajes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `viajes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_rutas_horarios` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `plazasOcupadas` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_rutas_horarios_idx` (`id_rutas_horarios`),
  CONSTRAINT `id_rutas_horarios` FOREIGN KEY (`id_rutas_horarios`) REFERENCES `rutas_horarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viajes`
--

LOCK TABLES `viajes` WRITE;
/*!40000 ALTER TABLE `viajes` DISABLE KEYS */;
INSERT INTO `viajes` VALUES (1,80,'2018-05-22',3),(2,81,'2018-05-22',0),(3,82,'2018-05-22',0),(4,83,'2018-05-22',1),(5,84,'2018-05-20',0);
/*!40000 ALTER TABLE `viajes` ENABLE KEYS */;
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

-- Dump completed on 2018-05-20 16:46:22