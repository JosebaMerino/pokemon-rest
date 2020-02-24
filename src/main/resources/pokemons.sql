-- --------------------------------------------------------
-- Host:                         localhost
-- Versión del servidor:         5.7.24 - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para pokebd
DROP DATABASE IF EXISTS `pokebd`;
CREATE DATABASE IF NOT EXISTS `pokebd` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pokebd`;

-- Volcando estructura para tabla pokebd.habilidad
DROP TABLE IF EXISTS `habilidad`;
CREATE TABLE IF NOT EXISTS `habilidad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla pokebd.habilidad: ~7 rows (aproximadamente)
/*!40000 ALTER TABLE `habilidad` DISABLE KEYS */;
INSERT INTO `habilidad` (`id`, `nombre`) VALUES
	(6, 'ascua'),
	(5, 'electricidad estatica'),
	(2, 'foco interno'),
	(7, 'hedor'),
	(1, 'impasible'),
	(3, 'justiciero'),
	(4, 'pararayos');
/*!40000 ALTER TABLE `habilidad` ENABLE KEYS */;

-- Volcando estructura para tabla pokebd.pokemon
DROP TABLE IF EXISTS `pokemon`;
CREATE TABLE IF NOT EXISTS `pokemon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla pokebd.pokemon: ~12 rows (aproximadamente)
/*!40000 ALTER TABLE `pokemon` DISABLE KEYS */;
INSERT INTO `pokemon` (`id`, `nombre`) VALUES
	(33, 'admin'),
	(23, 'Ander 2'),
	(2, 'BUA SAPI'),
	(4, 'bulbasaur'),
	(22, 'bulbasaur\''),
	(31, 'DA MIKEL BUA'),
	(3, 'DA MIKEL BUA SAPI'),
	(14, 'MIKEL BUA'),
	(32, 'NUEVO'),
	(11, 'Pichachu'),
	(30, 'Pichu'),
	(29, 'pikachuuuajjjjja'),
	(5, 'raichuuu');
/*!40000 ALTER TABLE `pokemon` ENABLE KEYS */;

-- Volcando estructura para tabla pokebd.pokemon_has_habilidad
DROP TABLE IF EXISTS `pokemon_has_habilidad`;
CREATE TABLE IF NOT EXISTS `pokemon_has_habilidad` (
  `pokemonId` int(11) NOT NULL,
  `habilidadId` int(11) NOT NULL,
  PRIMARY KEY (`pokemonId`,`habilidadId`),
  KEY `FK_habilidad` (`habilidadId`),
  CONSTRAINT `FK_habilidad` FOREIGN KEY (`habilidadId`) REFERENCES `habilidad` (`id`),
  CONSTRAINT `FK_pokemon` FOREIGN KEY (`pokemonId`) REFERENCES `pokemon` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla pokebd.pokemon_has_habilidad: ~13 rows (aproximadamente)
/*!40000 ALTER TABLE `pokemon_has_habilidad` DISABLE KEYS */;
INSERT INTO `pokemon_has_habilidad` (`pokemonId`, `habilidadId`) VALUES
	(2, 1),
	(3, 1),
	(4, 1),
	(4, 2),
	(2, 3),
	(3, 3),
	(29, 4),
	(30, 4),
	(32, 4),
	(29, 5),
	(30, 5),
	(32, 5),
	(3, 6),
	(4, 7);
/*!40000 ALTER TABLE `pokemon_has_habilidad` ENABLE KEYS */;

-- Volcando estructura para tabla pokebd.usuarios
DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla pokebd.usuarios: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` (`id`, `nombre`, `password`) VALUES
	(1, 'admin', 'admin'),
	(2, 'mikel', 'sapi');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
