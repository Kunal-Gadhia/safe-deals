-- UPDATED ON 16-11-16
CREATE DATABASE  IF NOT EXISTS `safedeals` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `safedeals`;
-- MySQL dump 10.13  Distrib 5.7.7-rc, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: safedeals
-- ------------------------------------------------------
-- Server version	5.7.7-rc

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
-- Table structure for table `agency`
--

DROP TABLE IF EXISTS `agency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agency` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `description` longtext,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `agent`
--

DROP TABLE IF EXISTS `agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agent` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) NOT NULL,
  `agency_id` int(11) NOT NULL COMMENT 'REF to agency.id',
  `description` longtext,
  `address` varchar(100) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `agent_agency_id_fk` (`agency_id`),
  CONSTRAINT `agent_agency_id_fk` FOREIGN KEY (`agency_id`) REFERENCES `agency` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `amenity`
--

DROP TABLE IF EXISTS `amenity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `amenity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `description` longtext,
  `amenity_code_id` int(11) DEFAULT NULL COMMENT 'REF amenity.id',
  `amenity_type` varchar(100) NOT NULL COMMENT 'JAVA ENUM com.vsquaresystem.safedeals.amenity.AmenityType',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `amenity_code_id_fk` (`amenity_code_id`),
  CONSTRAINT `amenity_code_id_fk` FOREIGN KEY (`amenity_code_id`) REFERENCES `amenity_code` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transportation_master`
--

DROP TABLE IF EXISTS `transportation_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transportation_master` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `private_amenities_master`
--

DROP TABLE IF EXISTS `private_amenities_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `private_amenities_master` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `road_master`
--

DROP TABLE IF EXISTS `road_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `road_master` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `size` int(20) DEFAULT '0',
  `condition` varchar(100) NOT NULL COMMENT 'JAVA ENUM com.vsquaresystem.safedeals.road.Condition',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `amenity_code`
--

DROP TABLE IF EXISTS `amenity_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `amenity_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `amenity_detail`
--

DROP TABLE IF EXISTS `amenity_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `amenity_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `phone_number` varchar(100) DEFAULT NULL,
  `mobile_number` varchar(100) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL COMMENT 'REF location.id',
  `amenity_id` int(11) DEFAULT NULL COMMENT 'REF amenity.id',
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `location_id_fk` (`location_id`),
  KEY `amenity_id_fk` (`amenity_id`),
  CONSTRAINT `amenity_id_fk` FOREIGN KEY (`amenity_id`) REFERENCES `amenity` (`id`),
  CONSTRAINT `location_id_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bank`
--

DROP TABLE IF EXISTS `bank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) NOT NULL,
  `loan_interest_rate_for_male` double NOT NULL,
  `loan_interest_rate_for_female` double NOT NULL,
  `max_loan_tenure` int(11) NOT NULL,
  `max_age_for_salaried` int(11) NOT NULL,
  `max_age_for_businessman` int(11) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `branch` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `bank_id` int(11) DEFAULT NULL,
  `ifsc_code` varchar(100) DEFAULT NULL,
  `micr_code` int(11) DEFAULT NULL,
  `phone_no` varchar(100) DEFAULT NULL,
  `fax` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `branch_bank_id_fk` (`bank_id`),
  KEY `branch_location_id_fk` (`location_id`),
  KEY `branch_city_id_fk` (`city_id`),
  CONSTRAINT `branch_bank_id_fk` FOREIGN KEY (`bank_id`) REFERENCES `bank` (`id`),
  CONSTRAINT `branch_city_id_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `branch_location_id_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `builder`
--

DROP TABLE IF EXISTS `builder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `builder` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) NOT NULL,
  `city_id` int(11) NOT NULL COMMENT 'REF to city.id',
  `description` longtext,
  `address` varchar(100) DEFAULT NULL,
  `phone_number` varchar(100) DEFAULT NULL,
  `project_name` varchar(100) NOT NULL,
  `property_name` varchar(100) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `builder_city_id_fk` (`city_id`),
  CONSTRAINT `builder_city_id_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `business_associate`
--

DROP TABLE IF EXISTS `business_associate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `business_associate` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `city_id` int(11) DEFAULT NULL COMMENT 'REF city.id',
  `location_id` int(11) DEFAULT NULL COMMENT 'REF location.id',
  `email` varchar(100) NOT NULL,
  `phone_number1` varchar(100) NOT NULL,
  `phone_number2` varchar(100) NOT NULL,
  `fax` varchar(100) NOT NULL,
  `franchise_id` int(11) DEFAULT NULL,
  `bank_id` int(11) DEFAULT NULL,
  `business_associate` int(11) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `business_associate_city_id_fk` (`city_id`),
  KEY `business_associate_location_id_fk` (`location_id`),
  KEY `business_associate_bank_id_fk` (`bank_id`),
  CONSTRAINT `business_associate_bank_id_fk` FOREIGN KEY (`bank_id`) REFERENCES `bank` (`id`),
  CONSTRAINT `business_associate_city_id_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `business_associate_location_id_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `business_partner`
--

DROP TABLE IF EXISTS `business_partner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `business_partner` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `state_id` int(11) NOT NULL COMMENT 'REF to state.id',
  `country_id` int(11) NOT NULL COMMENT 'REF to country.id',
  `description` longtext,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `radius` int(11) NOT NULL,
  `image_url` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `co_ordinate`
--

DROP TABLE IF EXISTS `co_ordinate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `co_ordinate` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `location_id` int(11) DEFAULT NULL COMMENT 'REF location.id',
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `co_ordinate_location_id_fk` (`location_id`),
  CONSTRAINT `co_ordinate_location_id_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `calling_code` varchar(100) DEFAULT NULL,
  `description` longtext,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `developer`
--

DROP TABLE IF EXISTS `developer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `developer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `user_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `phone_number1` varchar(100) NOT NULL,
  `phone_number2` varchar(100) NOT NULL,
  `mobile_number1` varchar(100) NOT NULL,
  `mobile_number2` varchar(100) NOT NULL,
  `description` longtext,
  `address` varchar(100) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `enquiry`
--

DROP TABLE IF EXISTS `enquiry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enquiry` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) NOT NULL,
  `mobile_no` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mode_of_contact` varchar(100) NOT NULL COMMENT 'JAVA ENUM com.vsquaresystem.safedeals.enquiry.ModeOfContact'
  `category` varchar(100) DEFAULT NULL,
  `property_guidance` varchar(500) DEFAULT NULL,
  `property_selling` varchar(500) DEFAULT NULL,
  `property_buying` varchar(500) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `venue` varchar(100) DEFAULT NULL,
  `description` longtext,
  `date` datetime DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `franchise`
--

DROP TABLE IF EXISTS `franchise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `franchise` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `city_id` int(11) NOT NULL COMMENT 'REF to city.id',
  `location_id` int(11) NOT NULL COMMENT 'REF to location.id',
  `email` varchar(100) NOT NULL,
  `phone_number1` varchar(100) NOT NULL,
  `phone_number2` varchar(100) NOT NULL,
  `fax` varchar(100) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `franchise_city_id_fk` (`city_id`),
  KEY `franchise_location_id_fk` (`location_id`),
  CONSTRAINT `franchise_city_id_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `franchise_location_id_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hospital`
--

DROP TABLE IF EXISTS `hospital`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hospital` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) NOT NULL,
  `speciality` varchar(100) DEFAULT NULL,
  `service` varchar(100) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL COMMENT 'REF location.id',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `hospital_location_id_fk` (`location_id`),
  CONSTRAINT `hospital_location_id_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `income_slab`
--

DROP TABLE IF EXISTS `income_slab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `income_slab` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `min_range` double NOT NULL,
  `max_range` double NOT NULL,
  `bank_id` int(11) NOT NULL COMMENT 'REF bank.id',
  `percentage_deduction` double NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `income_slab_bank_id_fk` (`bank_id`),
  CONSTRAINT `income_slab_bank_id_fk` FOREIGN KEY (`bank_id`) REFERENCES `bank` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `description` longtext,
  `city_id` int(11) DEFAULT NULL,
  `location_type_id` int(11) DEFAULT NULL,
  `location_categories` varchar(100) DEFAULT '[]' COMMENT 'JSON ARRAY String location_category.id',
  `safedeal_zone_id` int(11) DEFAULT NULL,
  `major_approach_road` varchar(100) DEFAULT NULL,
  `advantage` varchar(200) DEFAULT NULL,
  `disadvantage` varchar(200) DEFAULT NULL,
  `population` int(11) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `source_of_water` varchar(100) DEFAULT NULL,
  `public_transport` varchar(100) DEFAULT NULL,
  `migration_rate_per_annum` double DEFAULT NULL,
  `distance_from_centre_of_city` double DEFAULT NULL,
  `is_commercial_center` tinyint(1) DEFAULT '0',
  `distance_from_commercial_center` double DEFAULT NULL,
  `image_url` longtext,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `location_city_id_fk` (`city_id`),
  KEY `location_location_type_id_fk` (`location_type_id`),
  KEY `location_safedeal_zone_id_fk` (`safedeal_zone_id`),
  CONSTRAINT `location_city_id_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `location_location_type_id_fk` FOREIGN KEY (`location_type_id`) REFERENCES `location_type` (`id`),
  CONSTRAINT `location_safedeal_zone_id_fk` FOREIGN KEY (`safedeal_zone_id`) REFERENCES `safedeal_zone` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `location_category`
--

DROP TABLE IF EXISTS `location_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `location_type`
--

DROP TABLE IF EXISTS `location_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `market_price`
--

DROP TABLE IF EXISTS `market_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `market_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `location_id` int(11) DEFAULT NULL COMMENT 'REF location.id',
  `city_id` int(11) DEFAULT NULL COMMENT 'REF city.id',
  `year` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `mp_agri_land_lowest` double NOT NULL,
  `mp_agri_land_highest` double NOT NULL,
  `mp_agri_land_average` double DEFAULT NULL,
  `mp_plot_lowest` double NOT NULL,
  `mp_plot_highest` double NOT NULL,
  `mp_plot_average` double DEFAULT NULL,
  `mp_residential_lowest` double NOT NULL,
  `mp_residential_highest` double NOT NULL,
  `mp_residential_average` double DEFAULT NULL,
  `mp_commercial_lowest` double NOT NULL,
  `mp_commercial_highest` double NOT NULL,
  `mp_commercial_average` double DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `market_price_location_id_fk` (`location_id`),
  KEY `market_price_city_id_fk` (`city_id`),
  CONSTRAINT `market_price_city_id_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `market_price_location_id_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1909 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `description` longtext,
  `location_id` int(11) DEFAULT NULL COMMENT 'REF location.id',
  `city_id` int(11) DEFAULT NULL COMMENT 'REF city.id',
  `date_of_construction` datetime DEFAULT NULL,
  `date_of_possession` datetime DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `project_cost` double DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `project_location_id_fk` (`location_id`),
  KEY `project_city_id_fk` (`city_id`),
  CONSTRAINT `project_city_id_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `project_location_id_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `property`
--

DROP TABLE IF EXISTS `property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `property` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `description` longtext,
  `property_cost` double DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL COMMENT 'REF location.id',
  `city_id` int(11) DEFAULT NULL COMMENT 'REF city.id',
  `property_type` varchar(100) DEFAULT NULL,
  `date_of_construction` datetime DEFAULT NULL,
  `date_of_possession` datetime DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `property_location_id_fk` (`location_id`),
  KEY `property_city_id_fk` (`city_id`),
  CONSTRAINT `property_city_id_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `property_location_id_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `property_type`
--

DROP TABLE IF EXISTS `property_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `property_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `number_of_rooms` int(11) NOT NULL,
  `carpet_area` int(11) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `raw_market_price`
--

DROP TABLE IF EXISTS `raw_market_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `raw_market_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `city_id` int(11) NOT NULL COMMENT 'REF city.id',
  `location_name` varchar(100) DEFAULT NULL,
  `safedeal_zone_id` int(11) NOT NULL COMMENT 'REF safedeal_zone.id',
  `location_type_id` int(11) NOT NULL COMMENT 'REF location_type.id',
  `location_categories` varchar(100) NOT NULL DEFAULT '[]' COMMENT 'JSON ARRAY String location_category.id',
  `description` longtext,
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `mp_agri_land_lowest` double DEFAULT NULL,
  `mp_agri_land_highest` double DEFAULT NULL,
  `mp_plot_lowest` double DEFAULT NULL,
  `mp_plot_highest` double DEFAULT NULL,
  `mp_residential_lowest` double DEFAULT NULL,
  `mp_residential_highest` double DEFAULT NULL,
  `mp_commercial_lowest` double DEFAULT NULL,
  `mp_commercial_highest` double DEFAULT NULL,
  `is_commercial_center` tinyint(1) DEFAULT '0',
  `major_approach_road` varchar(100) DEFAULT NULL,
  `source_of_water` varchar(100) DEFAULT NULL,
  `public_transport` varchar(100) DEFAULT NULL,
  `advantage` varchar(100) DEFAULT NULL,
  `disadvantage` varchar(100) DEFAULT NULL,
  `population` int(11) DEFAULT NULL,
  `migration_rate` int(11) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `raw_market_price_city_id_fk` (`city_id`),
  KEY `raw_market_price_safedeal_zone_id_fk` (`safedeal_zone_id`),
  KEY `raw_market_price_location_type_id_fk` (`location_type_id`),
  CONSTRAINT `raw_market_price_city_id_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `raw_market_price_location_type_id_fk` FOREIGN KEY (`location_type_id`) REFERENCES `location_type` (`id`),
  CONSTRAINT `raw_market_price_safedeal_zone_id_fk` FOREIGN KEY (`safedeal_zone_id`) REFERENCES `safedeal_zone` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1922 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `raw_ready_reckoner`
--

DROP TABLE IF EXISTS `raw_ready_reckoner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `raw_ready_reckoner` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `city_id` int(11) NOT NULL COMMENT 'REF city.id',
  `location` varchar(100) NOT NULL,
  `safedeal_zone_id` int(11) NOT NULL COMMENT 'REF safedeal_zone.id',
  `location_type_id` int(11) NOT NULL COMMENT 'REF location_type.id',
  `location_categories` varchar(100) NOT NULL DEFAULT '[]' COMMENT 'JSON ARRAY String location_category.id',
  `rr_year` double NOT NULL,
  `rr_rate_land` double NOT NULL,
  `rr_rate_plot` double NOT NULL,
  `rr_rate_apartment` double NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `raw_ready_reckoner_city_id_fk` (`city_id`),
  KEY `raw_ready_reckoner_safedeal_zone_id_fk` (`safedeal_zone_id`),
  KEY `raw_ready_reckoner_location_type_id_fk` (`location_type_id`),
  CONSTRAINT `raw_ready_reckoner_city_id_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `raw_ready_reckoner_location_type_id_fk` FOREIGN KEY (`location_type_id`) REFERENCES `location_type` (`id`),
  CONSTRAINT `raw_ready_reckoner_safedeal_zone_id_fk` FOREIGN KEY (`safedeal_zone_id`) REFERENCES `safedeal_zone` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ready_reckoner`
--

DROP TABLE IF EXISTS `ready_reckoner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ready_reckoner` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `location_id` int(11) NOT NULL COMMENT 'REF location.id',
  `rr_year` double NOT NULL,
  `rr_rate_land` double NOT NULL,
  `rr_rate_plot` double NOT NULL,
  `rr_rate_apartment` double NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `ready_reckoner_location_id_fk` (`location_id`),
  CONSTRAINT `ready_reckoner_location_id_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `safedeal_zone`
--

DROP TABLE IF EXISTS `safedeal_zone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `safedeal_zone` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `salary_range`
--

DROP TABLE IF EXISTS `salary_range`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salary_range` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `min_salary` int(11) DEFAULT NULL,
  `max_salary` int(11) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `setting_key` varchar(100) NOT NULL COMMENT 'JAVA ENUM com.vsquaresystem.safedeals.setting.SettingKey',
  `setting_value` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `setting_setting_key_unique` (`setting_key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `country_id` int(11) NOT NULL COMMENT 'REF to country.id',
  `description` longtext,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `state_country_id_fk` (`country_id`),
  CONSTRAINT `state_country_id_fk` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `team_master`
--

DROP TABLE IF EXISTS `team_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_master` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `user_id` int(11) DEFAULT NULL COMMENT 'REF user.id',
  `designation` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `photo_path` varchar(100) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `team_master_user_id_fk` (`user_id`),
  CONSTRAINT `team_master_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `testimonial`
--

DROP TABLE IF EXISTS `testimonial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testimonial` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) NOT NULL,
  `description` longtext,
  `attachment` longtext,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transportation`
--

DROP TABLE IF EXISTS `transportation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transportation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) NOT NULL,
  `location_id` int(11) NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `transportation_location_id_fk` (`location_id`),
  CONSTRAINT `transportation_location_id_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `user_type` varchar(100) NOT NULL COMMENT 'JAVA ENUM com.vsquaresystem.safedeals.location.UserType',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_username_unique` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video`
--

DROP TABLE IF EXISTS `video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `video` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `description` text,
  `video_url` varchar(100) DEFAULT NULL,
  `is_intro_video` tinyint(1) DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workplace_area`
--

DROP TABLE IF EXISTS `workplace_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workplace_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `name` varchar(100) DEFAULT NULL,
  `size` int(11) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workplace_category`
--

DROP TABLE IF EXISTS `workplace_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workplace_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `workplace_area_id` int(11) NOT NULL COMMENT 'REF workplace_area.id',
  `name` varchar(100) NOT NULL,
  `description` longtext,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `workplace_category_workplace_area_id_fk` (`workplace_area_id`),
  CONSTRAINT `workplace_category_workplace_area_id_fk` FOREIGN KEY (`workplace_area_id`) REFERENCES `workplace_area` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workplace_category_detail`
--

DROP TABLE IF EXISTS `workplace_category_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workplace_category_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PRIMARY KEY',
  `workplace_category_id` int(11) NOT NULL COMMENT 'REF workplace_category.id',
  `salary_range_id` int(11) NOT NULL COMMENT 'REF salary_range.id',
  `number_of_employee` int(11) DEFAULT '0',
  `number_of_student` int(11) DEFAULT '0',
  `number_of_bed` int(11) DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-15 10:47:37