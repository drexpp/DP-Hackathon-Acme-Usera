start transaction;

drop database if exists `acme-usera`;
	create database `acme-usera`;
	use `acme-usera`;
	-- drop user 'acme-user'@'%';
	-- drop user 'acme-manager'@'%';
	create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
	create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';
	grant select, insert, update, delete on `acme-usera`.* to 'acme-user'@'%';
	grant select, insert, update, delete, create, drop, references, index, alter,
	create temporary tables, lock tables, create view, create routine, alter routine, 
		execute, trigger, show view on `acme-usera`.* to 'acme-manager'@'%';

-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: acme-usera
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `dateBirth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cgls5lrufx91ufsyh467spwa3` (`userAccount_id`),
  CONSTRAINT `FK_cgls5lrufx91ufsyh467spwa3` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_answer`
--

DROP TABLE IF EXISTS `actor_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_answer` (
  `Actor_id` int(11) NOT NULL,
  `answers_id` int(11) NOT NULL,
  UNIQUE KEY `UK_2ockvv1rmgxuw34xilm0e52` (`answers_id`),
  CONSTRAINT `FK_2ockvv1rmgxuw34xilm0e52` FOREIGN KEY (`answers_id`) REFERENCES `answer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_answer`
--

LOCK TABLES `actor_answer` WRITE;
/*!40000 ALTER TABLE `actor_answer` DISABLE KEYS */;
INSERT INTO `actor_answer` VALUES (1665,1723),(1666,1724),(1665,1726),(1666,1727),(1668,1729);
/*!40000 ALTER TABLE `actor_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_folder`
--

DROP TABLE IF EXISTS `actor_folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_folder` (
  `Actor_id` int(11) NOT NULL,
  `folders_id` int(11) NOT NULL,
  UNIQUE KEY `UK_dp573h40udupcm5m1kgn2bc2r` (`folders_id`),
  CONSTRAINT `FK_dp573h40udupcm5m1kgn2bc2r` FOREIGN KEY (`folders_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_folder`
--

LOCK TABLES `actor_folder` WRITE;
/*!40000 ALTER TABLE `actor_folder` DISABLE KEYS */;
INSERT INTO `actor_folder` VALUES (1663,1692),(1664,1693),(1665,1694),(1666,1695),(1667,1696),(1668,1697),(1669,1698),(1670,1699),(1671,1700),(1663,1701),(1664,1702),(1665,1703),(1666,1704),(1667,1705),(1668,1706),(1669,1707),(1670,1708),(1671,1709),(1663,1710),(1664,1711),(1665,1712),(1666,1713),(1667,1714),(1668,1715),(1669,1716),(1670,1717),(1671,1718);
/*!40000 ALTER TABLE `actor_folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `dateBirth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gfgqmtp2f9i5wsojt33xm0uth` (`userAccount_id`),
  CONSTRAINT `FK_gfgqmtp2f9i5wsojt33xm0uth` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1663,0,'','1957-05-22','admintodopoderoso@email.com','Manuel','','Bartual Moreno',1654);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `advertisement`
--

DROP TABLE IF EXISTS `advertisement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advertisement` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `bannerURL` varchar(255) DEFAULT NULL,
  `CVV` int(11) DEFAULT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `expirationMonth` int(11) DEFAULT NULL,
  `expirationYear` int(11) DEFAULT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `targetURL` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `sponsor_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dr486rxf9c2nw6qrxjijijaya` (`sponsor_id`),
  CONSTRAINT `FK_dr486rxf9c2nw6qrxjijijaya` FOREIGN KEY (`sponsor_id`) REFERENCES `sponsor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advertisement`
--

LOCK TABLES `advertisement` WRITE;
/*!40000 ALTER TABLE `advertisement` DISABLE KEYS */;
INSERT INTO `advertisement` VALUES (1688,0,'https://images.telepizza.com/vol/es/images/content/productos/pule_c.png',341,'Mastercard',11,25,'Manuel Esteban','342737357334600','https://www.telepizza.es/productos/pizzas','anuncio 1',1670),(1689,0,'https://cdn.adguard.com/public/Adguard/En/Articles/fake-popup.png',211,'American Express',8,22,'Violeta Moron','373695017560885','https://adguard.com/en/article/popups-blocker.html','anuncio 2',1671),(1690,0,'http://www.nyx.net/~cmeador/public/snakesonmyplane.jpg',341,'Mastercard',11,25,'Manuel Esteban','342737357334600','https://www.reddit.com/r/fakebadads/','anuncio 3',1670);
/*!40000 ALTER TABLE `advertisement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `advertisement_course`
--

DROP TABLE IF EXISTS `advertisement_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advertisement_course` (
  `Advertisement_id` int(11) NOT NULL,
  `courses_id` int(11) NOT NULL,
  KEY `FK_nhwja5v6cd0w3rhw94176kmfh` (`courses_id`),
  KEY `FK_51u2s1c5lmnp39fn5ey7oavjo` (`Advertisement_id`),
  CONSTRAINT `FK_51u2s1c5lmnp39fn5ey7oavjo` FOREIGN KEY (`Advertisement_id`) REFERENCES `advertisement` (`id`),
  CONSTRAINT `FK_nhwja5v6cd0w3rhw94176kmfh` FOREIGN KEY (`courses_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advertisement_course`
--

LOCK TABLES `advertisement_course` WRITE;
/*!40000 ALTER TABLE `advertisement_course` DISABLE KEYS */;
INSERT INTO `advertisement_course` VALUES (1688,1734),(1688,1736),(1689,1734),(1690,1734);
/*!40000 ALTER TABLE `advertisement_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `isSolution` bit(1) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `photoURL` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `actor_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_10g8xii7lw9kq0kcsobgmtw72` (`question_id`),
  CONSTRAINT `FK_10g8xii7lw9kq0kcsobgmtw72` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (1723,0,'','2017-10-19 22:40:00','','Sí, con h1',1665,1722),(1724,0,'\0','2017-10-19 11:40:00','','Sí, con hh1',1666,1722),(1726,0,'\0','2016-10-20 10:20:00','','The meaning is Do Planning',1665,1725),(1727,0,'\0','2016-10-21 10:50:00','','Probably something related to software',1666,1725),(1729,0,'','2016-10-19 11:40:00','','No, the meaning is different. You must use listen when you pay attention.',1668,1728);
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answerform`
--

DROP TABLE IF EXISTS `answerform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answerform` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `photoURL` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `question_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_mok8jxfsvfn5a7s3v583tjdwf` (`question_id`),
  CONSTRAINT `FK_mok8jxfsvfn5a7s3v583tjdwf` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answerform`
--

LOCK TABLES `answerform` WRITE;
/*!40000 ALTER TABLE `answerform` DISABLE KEYS */;
/*!40000 ALTER TABLE `answerform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_foei036ov74bv692o5lh5oi66` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1730,0,'CATEGORY'),(1731,0,'Web'),(1732,0,'Languajes'),(1733,0,'English');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_category`
--

DROP TABLE IF EXISTS `category_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_category` (
  `childCategories_id` int(11) NOT NULL,
  `parentCategories_id` int(11) NOT NULL,
  KEY `FK_4wsg88c3jk5x1d43yycr2rxkf` (`parentCategories_id`),
  KEY `FK_5l5kbsm63g1ln0syhgr8g6i9` (`childCategories_id`),
  CONSTRAINT `FK_5l5kbsm63g1ln0syhgr8g6i9` FOREIGN KEY (`childCategories_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FK_4wsg88c3jk5x1d43yycr2rxkf` FOREIGN KEY (`parentCategories_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_category`
--

LOCK TABLES `category_category` WRITE;
/*!40000 ALTER TABLE `category_category` DISABLE KEYS */;
INSERT INTO `category_category` VALUES (1731,1730),(1732,1730),(1733,1732);
/*!40000 ALTER TABLE `category_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `certification`
--

DROP TABLE IF EXISTS `certification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `certification` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `moment` date DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `examPaper_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5ic8u6fc9syxdlux074px7wix` (`examPaper_id`),
  UNIQUE KEY `UK_ea2knfaun8rk26iyjxv2lvrom` (`ticker`),
  KEY `FK_es7phy7vw6ktcrmbo9pes03df` (`student_id`),
  CONSTRAINT `FK_es7phy7vw6ktcrmbo9pes03df` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FK_5ic8u6fc9syxdlux074px7wix` FOREIGN KEY (`examPaper_id`) REFERENCES `exampaper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certification`
--

LOCK TABLES `certification` WRITE;
/*!40000 ALTER TABLE `certification` DISABLE KEYS */;
INSERT INTO `certification` VALUES (1749,0,'2017-05-18','ticker1',1742,1664);
/*!40000 ALTER TABLE `certification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contactinfo`
--

DROP TABLE IF EXISTS `contactinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contactinfo` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `contactPhone` varchar(255) DEFAULT NULL,
  `skype` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactinfo`
--

LOCK TABLES `contactinfo` WRITE;
/*!40000 ALTER TABLE `contactinfo` DISABLE KEYS */;
INSERT INTO `contactinfo` VALUES (1672,0,'954768345','JohnTron_153'),(1673,0,'954245764','Zacarias_89');
/*!40000 ALTER TABLE `contactinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contactinfo_comments`
--

DROP TABLE IF EXISTS `contactinfo_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contactinfo_comments` (
  `ContactInfo_id` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  KEY `FK_44bj0fiusniel6a7n405f5vwf` (`ContactInfo_id`),
  CONSTRAINT `FK_44bj0fiusniel6a7n405f5vwf` FOREIGN KEY (`ContactInfo_id`) REFERENCES `contactinfo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactinfo_comments`
--

LOCK TABLES `contactinfo_comments` WRITE;
/*!40000 ALTER TABLE `contactinfo_comments` DISABLE KEYS */;
INSERT INTO `contactinfo_comments` VALUES (1672,'Podré responder sus consultas en horario de tarde'),(1673,'Podré responder sus consultas en horario de mañana');
/*!40000 ALTER TABLE `contactinfo_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contactinfo_links`
--

DROP TABLE IF EXISTS `contactinfo_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contactinfo_links` (
  `ContactInfo_id` int(11) NOT NULL,
  `links` varchar(255) DEFAULT NULL,
  KEY `FK_gp5o60hk52b8piyncr1rp7d0x` (`ContactInfo_id`),
  CONSTRAINT `FK_gp5o60hk52b8piyncr1rp7d0x` FOREIGN KEY (`ContactInfo_id`) REFERENCES `contactinfo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactinfo_links`
--

LOCK TABLES `contactinfo_links` WRITE;
/*!40000 ALTER TABLE `contactinfo_links` DISABLE KEYS */;
INSERT INTO `contactinfo_links` VALUES (1672,'https://calendly.com/'),(1673,'https://calendly.com/');
/*!40000 ALTER TABLE `contactinfo_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `creationDate` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isClosed` bit(1) DEFAULT NULL,
  `photoURL` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `category_id` int(11) NOT NULL,
  `creator_id` int(11) NOT NULL,
  `exam_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_qi1q7409kyh4akiin1t91d3pc` (`isClosed`),
  KEY `FK_dbiynre90pcw51q0lv6c8bnte` (`category_id`),
  KEY `FK_absnvuaaxcg9kc8brlrkncxru` (`creator_id`),
  KEY `FK_3rk6n9xuq78295vr5lfvqo87p` (`exam_id`),
  CONSTRAINT `FK_3rk6n9xuq78295vr5lfvqo87p` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`),
  CONSTRAINT `FK_absnvuaaxcg9kc8brlrkncxru` FOREIGN KEY (`creator_id`) REFERENCES `teacher` (`id`),
  CONSTRAINT `FK_dbiynre90pcw51q0lv6c8bnte` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1734,1,'2017-03-22','En este curso aprenderás a desarrollar y programar una página web desde cero con un diseño atractivo y totalmente personalizado.','\0','http://quochoc.vn/caches/cc_large/cou_avatar/2016/01_19/1c172081cde3a2d0cd6dc0c8ec18f7b3.png','Desarrollo Web Completo con HTML5 y CSS3',1731,1667,1737),(1735,0,'2018-05-15','En este curso aprenderás a crear una API REST utilizando java','\0','https://accessusergroups.org/access-latino/wp-content/uploads/sites/3/2018/03/ApiRest.png','Desarrollo de APIs REST',1731,1667,NULL),(1736,1,'2011-02-10','En este curso aprenderás todo lo necesario para obtener el B1 en inglés','','','Curso de inglés B1',1732,1668,1739);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_advertisement`
--

DROP TABLE IF EXISTS `course_advertisement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_advertisement` (
  `Course_id` int(11) NOT NULL,
  `advertisements_id` int(11) NOT NULL,
  KEY `FK_lr6dt6q1yq7hq3cp3a6a8gq13` (`advertisements_id`),
  KEY `FK_enljbkkhu21ak44dducaxoltp` (`Course_id`),
  CONSTRAINT `FK_enljbkkhu21ak44dducaxoltp` FOREIGN KEY (`Course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `FK_lr6dt6q1yq7hq3cp3a6a8gq13` FOREIGN KEY (`advertisements_id`) REFERENCES `advertisement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_advertisement`
--

LOCK TABLES `course_advertisement` WRITE;
/*!40000 ALTER TABLE `course_advertisement` DISABLE KEYS */;
INSERT INTO `course_advertisement` VALUES (1734,1688),(1734,1689),(1734,1690),(1736,1688);
/*!40000 ALTER TABLE `course_advertisement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_teacher`
--

DROP TABLE IF EXISTS `course_teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_teacher` (
  `Course_id` int(11) NOT NULL,
  `teachers_id` int(11) NOT NULL,
  KEY `FK_ppiq8piu9mhwmue4s7myu66g0` (`teachers_id`),
  KEY `FK_65uj2h6kfhxjhq1meh2alntwx` (`Course_id`),
  CONSTRAINT `FK_65uj2h6kfhxjhq1meh2alntwx` FOREIGN KEY (`Course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `FK_ppiq8piu9mhwmue4s7myu66g0` FOREIGN KEY (`teachers_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_teacher`
--

LOCK TABLES `course_teacher` WRITE;
/*!40000 ALTER TABLE `course_teacher` DISABLE KEYS */;
INSERT INTO `course_teacher` VALUES (1734,1667),(1734,1668),(1735,1667),(1736,1668);
/*!40000 ALTER TABLE `course_teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courseform`
--

DROP TABLE IF EXISTS `courseform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courseform` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `photoURL` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7vp5u8birghqsoou0yfre5nm6` (`category_id`),
  CONSTRAINT `FK_7vp5u8birghqsoou0yfre5nm6` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courseform`
--

LOCK TABLES `courseform` WRITE;
/*!40000 ALTER TABLE `courseform` DISABLE KEYS */;
/*!40000 ALTER TABLE `courseform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum`
--

DROP TABLE IF EXISTS `curriculum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `personalRecord_id` int(11) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mabrm092fo1ae2t2m5a3ww4en` (`personalRecord_id`),
  UNIQUE KEY `UK_dwiq3oaoa89i224bhvvmiicao` (`teacher_id`),
  CONSTRAINT `FK_dwiq3oaoa89i224bhvvmiicao` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`),
  CONSTRAINT `FK_mabrm092fo1ae2t2m5a3ww4en` FOREIGN KEY (`personalRecord_id`) REFERENCES `personalrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum`
--

LOCK TABLES `curriculum` WRITE;
/*!40000 ALTER TABLE `curriculum` DISABLE KEYS */;
INSERT INTO `curriculum` VALUES (1676,0,'ticker1',1677,1667),(1683,0,'ticker2',1684,1668);
/*!40000 ALTER TABLE `curriculum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_educationrecord`
--

DROP TABLE IF EXISTS `curriculum_educationrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_educationrecord` (
  `Curriculum_id` int(11) NOT NULL,
  `educationRecord_id` int(11) NOT NULL,
  UNIQUE KEY `UK_c18y6iuyqjps4kpjki1f8ioqp` (`educationRecord_id`),
  KEY `FK_oldens4o8aaajbroxyv11fo6g` (`Curriculum_id`),
  CONSTRAINT `FK_oldens4o8aaajbroxyv11fo6g` FOREIGN KEY (`Curriculum_id`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_c18y6iuyqjps4kpjki1f8ioqp` FOREIGN KEY (`educationRecord_id`) REFERENCES `educationrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_educationrecord`
--

LOCK TABLES `curriculum_educationrecord` WRITE;
/*!40000 ALTER TABLE `curriculum_educationrecord` DISABLE KEYS */;
INSERT INTO `curriculum_educationrecord` VALUES (1676,1678),(1676,1679),(1683,1685);
/*!40000 ALTER TABLE `curriculum_educationrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_miscellaneousrecord`
--

DROP TABLE IF EXISTS `curriculum_miscellaneousrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_miscellaneousrecord` (
  `Curriculum_id` int(11) NOT NULL,
  `miscellaneousRecord_id` int(11) NOT NULL,
  UNIQUE KEY `UK_syjjon56uvea8rolwj3h8mfay` (`miscellaneousRecord_id`),
  KEY `FK_qq8tboyasceyejxb5gtnyhlbx` (`Curriculum_id`),
  CONSTRAINT `FK_qq8tboyasceyejxb5gtnyhlbx` FOREIGN KEY (`Curriculum_id`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_syjjon56uvea8rolwj3h8mfay` FOREIGN KEY (`miscellaneousRecord_id`) REFERENCES `miscellaneousrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_miscellaneousrecord`
--

LOCK TABLES `curriculum_miscellaneousrecord` WRITE;
/*!40000 ALTER TABLE `curriculum_miscellaneousrecord` DISABLE KEYS */;
INSERT INTO `curriculum_miscellaneousrecord` VALUES (1676,1680),(1683,1686);
/*!40000 ALTER TABLE `curriculum_miscellaneousrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_professionalrecord`
--

DROP TABLE IF EXISTS `curriculum_professionalrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_professionalrecord` (
  `Curriculum_id` int(11) NOT NULL,
  `professionalRecords_id` int(11) NOT NULL,
  UNIQUE KEY `UK_fmlxwyi8ktscvd4hflcg2xerj` (`professionalRecords_id`),
  KEY `FK_cxve92cmijecu4e9ev01pp3wc` (`Curriculum_id`),
  CONSTRAINT `FK_cxve92cmijecu4e9ev01pp3wc` FOREIGN KEY (`Curriculum_id`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_fmlxwyi8ktscvd4hflcg2xerj` FOREIGN KEY (`professionalRecords_id`) REFERENCES `professionalrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_professionalrecord`
--

LOCK TABLES `curriculum_professionalrecord` WRITE;
/*!40000 ALTER TABLE `curriculum_professionalrecord` DISABLE KEYS */;
INSERT INTO `curriculum_professionalrecord` VALUES (1676,1681),(1676,1682),(1683,1687);
/*!40000 ALTER TABLE `curriculum_professionalrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customisation`
--

DROP TABLE IF EXISTS `customisation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customisation` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `bannerEn` varchar(255) DEFAULT NULL,
  `bannerEs` varchar(255) DEFAULT NULL,
  `conversionRate` double DEFAULT NULL,
  `premiumPrice` int(11) DEFAULT NULL,
  `standardPrice` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customisation`
--

LOCK TABLES `customisation` WRITE;
/*!40000 ALTER TABLE `customisation` DISABLE KEYS */;
INSERT INTO `customisation` VALUES (1691,0,'Welcome to Acme-Usera','Bienvenidos a Acme-Usera',0.01,70,30);
/*!40000 ALTER TABLE `customisation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `educationrecord`
--

DROP TABLE IF EXISTS `educationrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `educationrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `diplomaTitle` varchar(255) DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `institutionName` varchar(255) DEFAULT NULL,
  `linkAttachment` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `educationrecord`
--

LOCK TABLES `educationrecord` WRITE;
/*!40000 ALTER TABLE `educationrecord` DISABLE KEYS */;
INSERT INTO `educationrecord` VALUES (1678,0,'diplomaTitle1','2012-02-12','institutionName1','https://diploma.com/educationRecord1','2010-05-10'),(1679,0,'diplomaTitle2','2014-12-21','institutionName2','https://diploma.com/educationRecord2','2007-05-01'),(1685,0,'diplomaTitle3','2015-01-12','institutionName3','https://diploma.com/educationRecord3','2010-05-05');
/*!40000 ALTER TABLE `educationrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `educationrecord_comments`
--

DROP TABLE IF EXISTS `educationrecord_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `educationrecord_comments` (
  `EducationRecord_id` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  KEY `FK_8upnn1ut4e4lbxwiapc4rvi51` (`EducationRecord_id`),
  CONSTRAINT `FK_8upnn1ut4e4lbxwiapc4rvi51` FOREIGN KEY (`EducationRecord_id`) REFERENCES `educationrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `educationrecord_comments`
--

LOCK TABLES `educationrecord_comments` WRITE;
/*!40000 ALTER TABLE `educationrecord_comments` DISABLE KEYS */;
INSERT INTO `educationrecord_comments` VALUES (1678,'This is Comment1'),(1678,'This is Comment2'),(1679,'This is Comment1'),(1679,'This is Comment2'),(1685,'This is Comment1'),(1685,'This is Comment2');
/*!40000 ALTER TABLE `educationrecord_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam`
--

DROP TABLE IF EXISTS `exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `mark` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `course_id` int(11) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_s7ji2he4sbh5bjakkxw8vkcir` (`course_id`),
  KEY `FK_rubi9bjesp5ho2nnpdtjx1ifu` (`teacher_id`),
  CONSTRAINT `FK_rubi9bjesp5ho2nnpdtjx1ifu` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`),
  CONSTRAINT `FK_s7ji2he4sbh5bjakkxw8vkcir` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam`
--

LOCK TABLES `exam` WRITE;
/*!40000 ALTER TABLE `exam` DISABLE KEYS */;
INSERT INTO `exam` VALUES (1737,0,100,'Examen curso 1',1734,1667),(1739,0,100,'Examen de ingles',1736,1668);
/*!40000 ALTER TABLE `exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examanswer`
--

DROP TABLE IF EXISTS `examanswer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examanswer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `mark` int(11) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `examPaper_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_n3pkengw76hq37gnq6mmnlcgk` (`number`),
  KEY `FK_6jcjgopn09ac28gntlys5p7kh` (`examPaper_id`),
  CONSTRAINT `FK_6jcjgopn09ac28gntlys5p7kh` FOREIGN KEY (`examPaper_id`) REFERENCES `exampaper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examanswer`
--

LOCK TABLES `examanswer` WRITE;
/*!40000 ALTER TABLE `examanswer` DISABLE KEYS */;
INSERT INTO `examanswer` VALUES (1743,0,100,1,'Esta es la respuesta correcta, quiero mis puntos.',1742),(1745,0,100,2,'No.',1744),(1747,0,10,3,'Maybe',1746),(1748,0,75,4,'Minecraft',1746);
/*!40000 ALTER TABLE `examanswer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examanswerform`
--

DROP TABLE IF EXISTS `examanswerform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examanswerform` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `number` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `examPaper_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_59dhn96al3e2yo574pm4k33g6` (`examPaper_id`),
  CONSTRAINT `FK_59dhn96al3e2yo574pm4k33g6` FOREIGN KEY (`examPaper_id`) REFERENCES `exampaper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examanswerform`
--

LOCK TABLES `examanswerform` WRITE;
/*!40000 ALTER TABLE `examanswerform` DISABLE KEYS */;
/*!40000 ALTER TABLE `examanswerform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examform`
--

DROP TABLE IF EXISTS `examform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examform` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `mark` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `course_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cpaj97bb0s3i9c9useo69vbq1` (`course_id`),
  CONSTRAINT `FK_cpaj97bb0s3i9c9useo69vbq1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examform`
--

LOCK TABLES `examform` WRITE;
/*!40000 ALTER TABLE `examform` DISABLE KEYS */;
/*!40000 ALTER TABLE `examform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exampaper`
--

DROP TABLE IF EXISTS `exampaper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exampaper` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `isFinished` bit(1) DEFAULT NULL,
  `mark` int(11) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `certification_id` int(11) DEFAULT NULL,
  `exam_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1thpnhq5fw602esdb7r2dk5gd` (`certification_id`),
  KEY `FK_e2c6d0vlwki0vkyc3ddm9ly5s` (`exam_id`),
  KEY `FK_pnrgap6245enybuamliapridw` (`student_id`),
  CONSTRAINT `FK_pnrgap6245enybuamliapridw` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FK_1thpnhq5fw602esdb7r2dk5gd` FOREIGN KEY (`certification_id`) REFERENCES `certification` (`id`),
  CONSTRAINT `FK_e2c6d0vlwki0vkyc3ddm9ly5s` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exampaper`
--

LOCK TABLES `exampaper` WRITE;
/*!40000 ALTER TABLE `exampaper` DISABLE KEYS */;
INSERT INTO `exampaper` VALUES (1742,1,'\0',100,'2017-05-13 18:45:00',1749,1737,1664),(1744,0,'',20,'2017-05-13 18:45:00',NULL,1739,1666),(1746,0,'',85,'2017-05-13 18:45:00',NULL,1739,1665);
/*!40000 ALTER TABLE `exampaper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exampaperform`
--

DROP TABLE IF EXISTS `exampaperform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exampaperform` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `isFinished` bit(1) DEFAULT NULL,
  `mark` int(11) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `exam_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fq6ayqve0jof5mt4fj7asxnwo` (`exam_id`),
  CONSTRAINT `FK_fq6ayqve0jof5mt4fj7asxnwo` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exampaperform`
--

LOCK TABLES `exampaperform` WRITE;
/*!40000 ALTER TABLE `exampaperform` DISABLE KEYS */;
/*!40000 ALTER TABLE `exampaperform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examquestion`
--

DROP TABLE IF EXISTS `examquestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examquestion` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `maxScore` int(11) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `photoURL` varchar(255) DEFAULT NULL,
  `statement` varchar(255) DEFAULT NULL,
  `exam_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_b4vor8lscajqda3hefgdohgir` (`number`),
  KEY `FK_l0elqk9t0ry40ou24hcso2vvl` (`exam_id`),
  CONSTRAINT `FK_l0elqk9t0ry40ou24hcso2vvl` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examquestion`
--

LOCK TABLES `examquestion` WRITE;
/*!40000 ALTER TABLE `examquestion` DISABLE KEYS */;
INSERT INTO `examquestion` VALUES (1738,0,'Esta es la respuesta correcta, quiero mis puntos.',100,1,'http://images.slideplayer.es/42/11423160/slides/slide_2.jpg','¿Qué crees que hay que responder en esta pregunta?',1737),(1740,0,'Yes',50,2,'','Do you believe in magic?',1739),(1741,0,'Pokemon',50,1,'https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/International_Pok%C3%A9mon_logo.svg/1200px-International_Pok%C3%A9mon_logo.svg.png','What do you like to play?',1739);
/*!40000 ALTER TABLE `examquestion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `isSystem` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_l1kp977466ddsv762wign7kdh` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
INSERT INTO `folder` VALUES (1692,0,'','in box'),(1693,0,'','in box'),(1694,0,'','in box'),(1695,0,'','in box'),(1696,0,'','in box'),(1697,0,'','in box'),(1698,0,'','in box'),(1699,0,'','in box'),(1700,0,'','in box'),(1701,0,'','out box'),(1702,0,'','out box'),(1703,0,'','out box'),(1704,0,'','out box'),(1705,0,'','out box'),(1706,0,'','out box'),(1707,0,'','out box'),(1708,0,'','out box'),(1709,0,'','out box'),(1710,0,'','trash box'),(1711,0,'','trash box'),(1712,0,'','trash box'),(1713,0,'','trash box'),(1714,0,'','trash box'),(1715,0,'','trash box'),(1716,0,'','trash box'),(1717,0,'','trash box'),(1718,0,'','trash box');
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forum`
--

DROP TABLE IF EXISTS `forum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forum` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_q1q02tynk9lobaw0u6itjq1d3` (`course_id`),
  CONSTRAINT `FK_q1q02tynk9lobaw0u6itjq1d3` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum`
--

LOCK TABLES `forum` WRITE;
/*!40000 ALTER TABLE `forum` DISABLE KEYS */;
INSERT INTO `forum` VALUES (1750,0,1734),(1751,0,1735),(1752,0,1736);
/*!40000 ALTER TABLE `forum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesson`
--

DROP TABLE IF EXISTS `lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lesson` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `photoURL` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `videoURL` varchar(255) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  `teacher_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_swxtnpbei5seidnpkvxyqph49` (`course_id`),
  KEY `FK_bs7u0sl3tjvie49mlwgav9jwc` (`teacher_id`),
  CONSTRAINT `FK_bs7u0sl3tjvie49mlwgav9jwc` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`),
  CONSTRAINT `FK_swxtnpbei5seidnpkvxyqph49` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson`
--

LOCK TABLES `lesson` WRITE;
/*!40000 ALTER TABLE `lesson` DISABLE KEYS */;
INSERT INTO `lesson` VALUES (1719,1,'En esta lección daremos los primeros pasos para aprender a programar html. Mírate el vídeo y si tienes dudas pregunta en el foro.','2017-03-22 00:00:00','En esta lección daremos los primeros pasos para aprender a programar html. Manos a la obra!','https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/HTML5_logo_and_wordmark.svg/1200px-HTML5_logo_and_wordmark.svg.png','Primeros pasos','https://www.youtube.com/embed/cqMfPS8jPys',1734,1667),(1720,1,'El verbo to be (Ser/estar) es uno de los más importantes porque está presente en todo el...','2015-06-22 00:00:00','En esta lección daremos el verbo to be y sus usos','','Verbo to be','',1736,1668),(1721,1,'El pasado simple del verbo to be es Was/Were dependiendo si es tercera persona o no...','2015-06-23 00:00:00','En esta lección daremos el pasado simple y sus usos','','El pasado simple','https://www.youtube.com/embed/tgbNymZ7vqY',1736,1668);
/*!40000 ALTER TABLE `lesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesson_student`
--

DROP TABLE IF EXISTS `lesson_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lesson_student` (
  `Lesson_id` int(11) NOT NULL,
  `students_id` int(11) NOT NULL,
  KEY `FK_k9esgeonjhxfa9pudvnm712qc` (`students_id`),
  KEY `FK_fbsnvxqnuwr589adyj2snn60q` (`Lesson_id`),
  CONSTRAINT `FK_fbsnvxqnuwr589adyj2snn60q` FOREIGN KEY (`Lesson_id`) REFERENCES `lesson` (`id`),
  CONSTRAINT `FK_k9esgeonjhxfa9pudvnm712qc` FOREIGN KEY (`students_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson_student`
--

LOCK TABLES `lesson_student` WRITE;
/*!40000 ALTER TABLE `lesson_student` DISABLE KEYS */;
INSERT INTO `lesson_student` VALUES (1719,1664),(1720,1665),(1721,1665);
/*!40000 ALTER TABLE `lesson_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lessonform`
--

DROP TABLE IF EXISTS `lessonform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lessonform` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `photoURL` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `videoURL` varchar(255) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_po8xu9mmapjjd4of0wm29weus` (`course_id`),
  CONSTRAINT `FK_po8xu9mmapjjd4of0wm29weus` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lessonform`
--

LOCK TABLES `lessonform` WRITE;
/*!40000 ALTER TABLE `lessonform` DISABLE KEYS */;
/*!40000 ALTER TABLE `lessonform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mailmessage`
--

DROP TABLE IF EXISTS `mailmessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mailmessage` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `folder_id` int(11) NOT NULL,
  `recipient_id` int(11) NOT NULL,
  `sender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7v3h8r2fxcp9cdsogubomnpm7` (`folder_id`),
  CONSTRAINT `FK_7v3h8r2fxcp9cdsogubomnpm7` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mailmessage`
--

LOCK TABLES `mailmessage` WRITE;
/*!40000 ALTER TABLE `mailmessage` DISABLE KEYS */;
/*!40000 ALTER TABLE `mailmessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneousrecord`
--

DROP TABLE IF EXISTS `miscellaneousrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneousrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `linkAttachment` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneousrecord`
--

LOCK TABLES `miscellaneousrecord` WRITE;
/*!40000 ALTER TABLE `miscellaneousrecord` DISABLE KEYS */;
INSERT INTO `miscellaneousrecord` VALUES (1680,0,'https://miscellanous.com/miscellaneousRecord1','title1'),(1686,0,'https://miscellanous.com/miscellaneousRecord2','title2');
/*!40000 ALTER TABLE `miscellaneousrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneousrecord_comments`
--

DROP TABLE IF EXISTS `miscellaneousrecord_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneousrecord_comments` (
  `MiscellaneousRecord_id` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  KEY `FK_ajnajfg26mdd0ujsxrq56vyvf` (`MiscellaneousRecord_id`),
  CONSTRAINT `FK_ajnajfg26mdd0ujsxrq56vyvf` FOREIGN KEY (`MiscellaneousRecord_id`) REFERENCES `miscellaneousrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneousrecord_comments`
--

LOCK TABLES `miscellaneousrecord_comments` WRITE;
/*!40000 ALTER TABLE `miscellaneousrecord_comments` DISABLE KEYS */;
INSERT INTO `miscellaneousrecord_comments` VALUES (1680,'This is Comment1'),(1680,'This is Comment2');
/*!40000 ALTER TABLE `miscellaneousrecord_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personalrecord`
--

DROP TABLE IF EXISTS `personalrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personalrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `linkPhoto` varchar(255) DEFAULT NULL,
  `linkedInProfile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personalrecord`
--

LOCK TABLES `personalrecord` WRITE;
/*!40000 ALTER TABLE `personalrecord` DISABLE KEYS */;
INSERT INTO `personalrecord` VALUES (1677,0,'franciscoPerez@email.com','https://flickr.com/personalRecord1','https://linkedin.com/personalRecord1','Francisco','666777222','Pérez'),(1684,0,'maricarmenLopez@email.com','https://flickr.com/personalRecord2','https://linkedin.com/personalRecord2','María del Carmen','658993285','López');
/*!40000 ALTER TABLE `personalrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professionalrecord`
--

DROP TABLE IF EXISTS `professionalrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professionalrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `companyName` varchar(255) DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `linkAttachment` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professionalrecord`
--

LOCK TABLES `professionalrecord` WRITE;
/*!40000 ALTER TABLE `professionalrecord` DISABLE KEYS */;
INSERT INTO `professionalrecord` VALUES (1681,0,'Company1','2013-11-21','https://company1.com/professionalRecord1','Role1','2000-05-03'),(1682,0,'Company2','2015-12-07','https://company2.com/professionalRecord2','Role2','1997-09-19'),(1687,0,'Company3','2010-11-08','https://company3.com/professionalRecord3','Role3','2003-05-15');
/*!40000 ALTER TABLE `professionalrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professionalrecord_comments`
--

DROP TABLE IF EXISTS `professionalrecord_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professionalrecord_comments` (
  `ProfessionalRecord_id` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  KEY `FK_p6ml7gtt96exn742i2gl3b8dj` (`ProfessionalRecord_id`),
  CONSTRAINT `FK_p6ml7gtt96exn742i2gl3b8dj` FOREIGN KEY (`ProfessionalRecord_id`) REFERENCES `professionalrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professionalrecord_comments`
--

LOCK TABLES `professionalrecord_comments` WRITE;
/*!40000 ALTER TABLE `professionalrecord_comments` DISABLE KEYS */;
INSERT INTO `professionalrecord_comments` VALUES (1681,'Comment1'),(1681,'Comment2'),(1687,'Comment1'),(1687,'Comment2');
/*!40000 ALTER TABLE `professionalrecord_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `isAnswered` bit(1) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `photoURL` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `forum_id` int(11) DEFAULT NULL,
  `student_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_kqps86v5157eux0spmbopk806` (`forum_id`),
  KEY `FK_homiuv4eeuww2p6d3w74tgxpi` (`student_id`),
  CONSTRAINT `FK_homiuv4eeuww2p6d3w74tgxpi` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FK_kqps86v5157eux0spmbopk806` FOREIGN KEY (`forum_id`) REFERENCES `forum` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1722,1,'','2017-10-19 08:35:00','','¿Sabéis cómo poner un heading 1?','Comentarios html',1750,1664),(1725,1,'\0','2016-10-14 08:35:00','https://previews.123rf.com/images/mayrum/mayrum1407/mayrum140700029/31646119-web-proceso-de-desarrollo-de-concepto-la-planificaci%C3%B3n-dise%C3%B1o-desarrollo-pruebas-puesta-en-marcha-vector-p.jpg','What\'s the meaning of DP?','Meaning of DP',1752,1664),(1728,1,'','2016-10-14 12:32:00','','I don\'t know the diference between listen and hear. Do they have the same meaning?','Diference between listen and hear?',1752,1664);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionform`
--

DROP TABLE IF EXISTS `questionform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionform` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `photoURL` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `forum_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_r9xwne8h3rgdtmudlttq6atdn` (`forum_id`),
  CONSTRAINT `FK_r9xwne8h3rgdtmudlttq6atdn` FOREIGN KEY (`forum_id`) REFERENCES `forum` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionform`
--

LOCK TABLES `questionform` WRITE;
/*!40000 ALTER TABLE `questionform` DISABLE KEYS */;
/*!40000 ALTER TABLE `questionform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsor`
--

DROP TABLE IF EXISTS `sponsor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `dateBirth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_okfx8h0cn4eidh8ng563vowjc` (`userAccount_id`),
  CONSTRAINT `FK_okfx8h0cn4eidh8ng563vowjc` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsor`
--

LOCK TABLES `sponsor` WRITE;
/*!40000 ALTER TABLE `sponsor` DISABLE KEYS */;
INSERT INTO `sponsor` VALUES (1670,0,'Plaza de los Palomares','1975-05-10','manuEsteban@email.com','Manuel','699887767','Estéban',1661),(1671,0,'Avenida de los Gitanos','1975-05-10','violetaMoron@email.com','Violeta','655437865','Morón',1662);
/*!40000 ALTER TABLE `sponsor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `dateBirth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1wi576q4evovlcg3tygsmdw1p` (`userAccount_id`),
  KEY `UK_k8i34bvmjh38qm5t7u3tkqmjm` (`score`),
  CONSTRAINT `FK_1wi576q4evovlcg3tygsmdw1p` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1664,0,'Avenida séptima nº5','1997-06-10','illojulio@email.com','Julio','902242526','Sanabria',1655,200),(1665,0,'Calle Ernest Hemingway nº48','1998-06-10','raulcasa@email.com','Raul','+902242526','Cassandra',1656,1000),(1666,0,'Calle 8 de 9','1975-05-10','rodrigoflores@email.com','Rodrigo','677895643','Flores',1657,1000);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_lesson`
--

DROP TABLE IF EXISTS `student_lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_lesson` (
  `Student_id` int(11) NOT NULL,
  `lessons_id` int(11) NOT NULL,
  KEY `FK_egqr3fh0fu49kkor73nwg33vo` (`lessons_id`),
  KEY `FK_5jehtl4bbqer0ywo6mpn5lfgs` (`Student_id`),
  CONSTRAINT `FK_5jehtl4bbqer0ywo6mpn5lfgs` FOREIGN KEY (`Student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FK_egqr3fh0fu49kkor73nwg33vo` FOREIGN KEY (`lessons_id`) REFERENCES `lesson` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_lesson`
--

LOCK TABLES `student_lesson` WRITE;
/*!40000 ALTER TABLE `student_lesson` DISABLE KEYS */;
INSERT INTO `student_lesson` VALUES (1664,1719),(1665,1720),(1665,1721);
/*!40000 ALTER TABLE `student_lesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_tutorial`
--

DROP TABLE IF EXISTS `student_tutorial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_tutorial` (
  `Student_id` int(11) NOT NULL,
  `tutorials_id` int(11) NOT NULL,
  UNIQUE KEY `UK_ecxlrqgsm9n7nd3u72yrp5x60` (`tutorials_id`),
  KEY `FK_2v9cqfgsi3tchwupbve4rw0in` (`Student_id`),
  CONSTRAINT `FK_2v9cqfgsi3tchwupbve4rw0in` FOREIGN KEY (`Student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FK_ecxlrqgsm9n7nd3u72yrp5x60` FOREIGN KEY (`tutorials_id`) REFERENCES `tutorial` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_tutorial`
--

LOCK TABLES `student_tutorial` WRITE;
/*!40000 ALTER TABLE `student_tutorial` DISABLE KEYS */;
INSERT INTO `student_tutorial` VALUES (1664,1674),(1664,1675);
/*!40000 ALTER TABLE `student_tutorial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscription` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `CVV` int(11) DEFAULT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `expirationMonth` int(11) DEFAULT NULL,
  `expirationYear` int(11) DEFAULT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `subscriptionType` varchar(255) DEFAULT NULL,
  `course_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_5t645li0ad9oihjymgh6mpfcu` (`subscriptionType`),
  KEY `FK_9lrrxqv5bxgg0mj25y6okp4j1` (`course_id`),
  KEY `FK_n7wmu4nbl4y4n9w59wot85xe7` (`student_id`),
  CONSTRAINT `FK_n7wmu4nbl4y4n9w59wot85xe7` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FK_9lrrxqv5bxgg0mj25y6okp4j1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
INSERT INTO `subscription` VALUES (1753,0,341,'Mastercard',11,25,'Manuel Esteban','342737357334600','STANDARD',1734,1664),(1754,0,211,'American Express',8,22,'Violeta Moron','373695017560885','PREMIUM',1736,1664),(1755,0,531,'Visa',2,24,'Persona Ficticia','1111222233334444','FREE',1736,1665);
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `dateBirth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  `contactInfo_id` int(11) DEFAULT NULL,
  `curriculum_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hh7bf6toh1ysrkeqxhr1077dv` (`userAccount_id`),
  KEY `FK_ndk5jk1061e4p2f68aqt06312` (`contactInfo_id`),
  KEY `FK_m4jvwih1a6blju2kj5ju6wt87` (`curriculum_id`),
  CONSTRAINT `FK_hh7bf6toh1ysrkeqxhr1077dv` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`),
  CONSTRAINT `FK_m4jvwih1a6blju2kj5ju6wt87` FOREIGN KEY (`curriculum_id`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_ndk5jk1061e4p2f68aqt06312` FOREIGN KEY (`contactInfo_id`) REFERENCES `contactinfo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (1667,1,'Calle Marzo nº15','1975-05-10','franciscoPerez@email.com','Francisco','666777222','Pérez',1658,1672,1676),(1668,1,'Calle Mayo 26','1975-05-10','maricarmenLopez@email.com','María del Carmen','658993285','López',1659,1673,1683),(1669,0,'Calle de los Mares','1975-05-10','juanaFito@email.com','Juana','+654445634','Fito',1660,NULL,NULL);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher_course`
--

DROP TABLE IF EXISTS `teacher_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher_course` (
  `Teacher_id` int(11) NOT NULL,
  `coursesJoined_id` int(11) NOT NULL,
  KEY `FK_73n4ys7tuhkcuf4w7wm170vp3` (`coursesJoined_id`),
  KEY `FK_8e64njivkfw881ohytdx1g9rn` (`Teacher_id`),
  CONSTRAINT `FK_8e64njivkfw881ohytdx1g9rn` FOREIGN KEY (`Teacher_id`) REFERENCES `teacher` (`id`),
  CONSTRAINT `FK_73n4ys7tuhkcuf4w7wm170vp3` FOREIGN KEY (`coursesJoined_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher_course`
--

LOCK TABLES `teacher_course` WRITE;
/*!40000 ALTER TABLE `teacher_course` DISABLE KEYS */;
INSERT INTO `teacher_course` VALUES (1667,1734),(1667,1735),(1668,1734),(1668,1736);
/*!40000 ALTER TABLE `teacher_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial`
--

DROP TABLE IF EXISTS `tutorial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tutorial` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `startTime` datetime DEFAULT NULL,
  `student_id` int(11) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2ro3yfcgkbrontyo9uuoaj780` (`student_id`),
  KEY `FK_pcwccxqt5jthfh6whhxccxun6` (`teacher_id`),
  CONSTRAINT `FK_pcwccxqt5jthfh6whhxccxun6` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`),
  CONSTRAINT `FK_2ro3yfcgkbrontyo9uuoaj780` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial`
--

LOCK TABLES `tutorial` WRITE;
/*!40000 ALTER TABLE `tutorial` DISABLE KEYS */;
INSERT INTO `tutorial` VALUES (1674,0,'2018-07-03 19:30:00',1664,1667),(1675,0,'2018-06-29 18:45:00',1664,1667);
/*!40000 ALTER TABLE `tutorial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (1654,0,'21232f297a57a5a743894a0e4a801fc3','admin'),(1655,0,'5e5545d38a68148a2d5bd5ec9a89e327','student1'),(1656,0,'213ee683360d88249109c2f92789dbc3','student2'),(1657,0,'8e4947690532bc44a8e41e9fb365b76a','student3'),(1658,0,'41c8949aa55b8cb5dbec662f34b62df3','teacher1'),(1659,0,'ccffb0bb993eeb79059b31e1611ec353','teacher2'),(1660,0,'82470256ea4b80343b27afccbca1015b','teacher3'),(1661,0,'42c63ad66d4dc07ed17753772bef96d6','sponsor1'),(1662,0,'3dc67f80a03324e01b1640f45d107485','sponsor2');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (1654,'ADMIN'),(1655,'STUDENT'),(1656,'STUDENT'),(1657,'STUDENT'),(1658,'TEACHER'),(1659,'TEACHER'),(1660,'TEACHER'),(1661,'SPONSOR'),(1662,'SPONSOR');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-06 23:29:28

commit;