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

-- Volcando datos para la tabla pokebd.habilidad: ~6 rows (aproximadamente)
DELETE FROM `habilidad`;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla pokebd.pokemon: ~4 rows (aproximadamente)
DELETE FROM `pokemon`;
/*!40000 ALTER TABLE `pokemon` DISABLE KEYS */;
INSERT INTO `pokemon` (`id`, `nombre`) VALUES
	(4, 'bulbasaur'),
	(3, 'charmander'),
	(1, 'lucario'),
	(2, 'pikachu');
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

-- Volcando datos para la tabla pokebd.pokemon_has_habilidad: ~6 rows (aproximadamente)
DELETE FROM `pokemon_has_habilidad`;
/*!40000 ALTER TABLE `pokemon_has_habilidad` DISABLE KEYS */;
INSERT INTO `pokemon_has_habilidad` (`pokemonId`, `habilidadId`) VALUES
	(1, 1),
	(3, 1),
	(4, 1),
	(1, 2),
	(1, 3),
	(3, 3),
	(2, 4),
	(2, 5),
	(3, 6),
	(4, 7);
/*!40000 ALTER TABLE `pokemon_has_habilidad` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
