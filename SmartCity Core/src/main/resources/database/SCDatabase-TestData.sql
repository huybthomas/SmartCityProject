-- SmartCity Database Test Data Version 0.0.1, 21/07/2016
--
-- Database: smartcitydb - 2016 UAntwerpen
-- ----------------------------------------------------
-- Server version   5.6.29

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
-- Test data for table `point`
--

DROP TABLE IF EXISTS smartcitydb.point;
CREATE TABLE smartcitydb.point (
  `pid` bigint(20) NOT NULL AUTO_INCREMENT,
  `rfid` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `pointlock` int(11) DEFAULT '1',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*!40101 LOCK TABLES smartcitydb.point WRITE; */
/*!40000 ALTER TABLE smartcitydb.point DISABLE KEYS */;
INSERT INTO smartcitydb.point VALUES
  (1,'04 70 39 32 06 27 80','PARKING',0),
  (2,'04 67 88 8A C8 48 80','POINT',0),
  (3,'04 97 36 A2 F7 22 80','POINT',0),
  (4,'04 36 8A 9A F6 1F 80','TRAFFICLIGHT',0),
  (5,'04 7B 88 8A C8 48 80','POINT',1),
  (6,'04 6C 6B 32 06 27 80','POINT',0),
  (7,'04 84 88 8A C8 48 80','POINT',1),
  (8,'04 B3 88 8A C8 48 80','PARKING',1),
  (9,'04 8D 88 8A C8 48 80','PARKING',0),
  (10,'04 AA 88 8A C8 48 80','POINT',0),
  (11,'04 C4 FD 12 A9 34 80','PARKING',0),
  (12,'04 96 88 8A C8 48 80','POINT',0),
  (13,'04 A1 88 8A C8 48 80','POINT',0),
  (14,'04 86 04 22 A9 34 84','TRAFFICLIGHT',0),
  (15,'04 18 25 9A 7F 22 80','PARKING',0),
  (16,'04 BC 88 8A C8 48 80','POINT',0),
  (17,'04 C5 88 8A C8 48 80','POINT',0),
  (18,'04 EC 88 8A C8 48 80','POINT',0),
  (19,'04 E3 88 8A C8 48 80','POINT',0),
  (20,'04 26 3E 92 1E 25 80','PARKING',1),
  (21,'04 DA 88 8A C8 48 80','POINT',0),
  (22,'04 D0 88 8A C8 48 80','POINT',0),
  (23,'04 41 70 92 1E 25 80','PARKING',0),
  (24,'04 3C 67 9A F6 1F 80','PARKING',0);
/*!40000 ALTER TABLE smartcitydb.point ENABLE KEYS */;
/*!40101 UNLOCK TABLES; */

--
-- Test data for table `link`
--

DROP TABLE IF EXISTS smartcitydb.link;
CREATE TABLE smartcitydb.link (
  `lid` bigint(20) NOT NULL AUTO_INCREMENT,
  `length` bigint(20) DEFAULT '1',
  `start_direction` varchar(255) DEFAULT NULL,
  `start_point` bigint(20) DEFAULT NULL,
  `stop_direction` varchar(255) DEFAULT NULL,
  `stop_point` bigint(20) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `weight` int(11) DEFAULT '1',
  PRIMARY KEY (`lid`),
  KEY `fk_startpoint` (`start_point`),
  KEY `fk_stoppoint` (`stop_point`),
  KEY `FK_fraaqnos7yp6t7drynxqo0ov` (`pid`),
  CONSTRAINT `FK_fraaqnos7yp6t7drynxqo0ov` FOREIGN KEY (`pid`) REFERENCES `point` (`pid`),
  CONSTRAINT `fk_startpoint` FOREIGN KEY (`start_point`) REFERENCES `point` (`pid`),
  CONSTRAINT `fk_stoppoint` FOREIGN KEY (`stop_point`) REFERENCES `point` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

/*!40101 LOCK TABLES smartcitydb.link WRITE; */
/*!40000 ALTER TABLE smartcitydb.link DISABLE KEYS */;
INSERT INTO smartcitydb.link VALUES
  (1,10,'E',1,'W',2,NULL,1),
  (2,10,'W',2,'E',1,NULL,1),
  (4,450,'W',7,'E',2,NULL,1),
  (5,10,'S',3,'N',7,NULL,1),
  (6,10,'N',7,'S',3,NULL,1),
  (7,10,'S',4,'N',8,NULL,1),
  (8,10,'N',8,'S',4,NULL,1),
  (9,180,'E',7,'W',8,NULL,1),
  (10,180,'W',8,'E',7,NULL,1),
  (12,450,'W',5,'E',8,NULL,1),
  (13,10,'E',5,'W',6,NULL,1),
  (14,10,'W',6,'E',5,NULL,1),
  (15,650,'S',2,'W',9,NULL,1),
  (17,180,'S',7,'N',9,NULL,1),
  (18,180,'N',9,'S',7,NULL,1),
  (19,180,'S',8,'N',10,NULL,1),
  (20,180,'N',10,'S',8,NULL,1),
  (21,180,'E',9,'W',10,NULL,1),
  (22,180,'W',10,'E',9,NULL,1),
  (24,650,'E',10,'S',5,NULL,1),
  (25,375,'S',9,'N',11,NULL,1),
  (26,740,'N',12,'S',10,NULL,1),
  (27,550,'S',11,'W',12,NULL,1),
  (28,450,'S',12,'N',13,NULL,1),
  (29,450,'N',13,'S',12,NULL,1),
  (30,650,'W',13,'N',17,NULL,1),
  (31,10,'N',18,'S',14,NULL,1),
  (32,250,'N',14,'S',13,NULL,1),
  (33,10,'E',15,'W',16,NULL,1),
  (34,10,'W',16,'E',15,NULL,1),
  (36,450,'W',17,'E',16,NULL,1),
  (37,180,'E',17,'W',18,NULL,1),
  (38,180,'W',18,'E',17,NULL,1),
  (40,450,'W',19,'E',18,NULL,1),
  (41,10,'E',19,'W',20,NULL,1),
  (42,10,'W',20,'E',19,NULL,1),
  (43,650,'S',16,'W',21,NULL,1),
  (45,180,'S',17,'N',21,NULL,1),
  (46,180,'N',21,'S',17,NULL,1),
  (47,180,'S',18,'N',22,NULL,1),
  (48,180,'N',22,'S',18,NULL,1),
  (49,180,'E',21,'W',22,NULL,1),
  (50,180,'W',22,'E',21,NULL,1),
  (51,650,'E',22,'S',19,NULL,1),
  (53,10,'S',21,'N',23,NULL,1),
  (54,10,'N',23,'S',21,NULL,1),
  (55,10,'S',22,'N',24,NULL,1),
  (56,10,'N',24,'S',22,NULL,1);
/*!40000 ALTER TABLE smartcitydb.link ENABLE KEYS */;
/*!40101 UNLOCK TABLES; */

--
-- Test data for table `bot`
--

DROP TABLE IF EXISTS smartcitydb.bot;
CREATE TABLE smartcitydb.bot (
  `rid` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_id` bigint(20) DEFAULT NULL,
  `percentage_completed` int(11) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `link_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`rid`),
  KEY `FK_g2k7qbjgq85d7hmmov6r4benu` (`link_id`),
  CONSTRAINT `FK_g2k7qbjgq85d7hmmov6r4benu` FOREIGN KEY (`link_id`) REFERENCES `link` (`lid`)
) ENGINE=InnoDB AUTO_INCREMENT=260 DEFAULT CHARSET=utf8;

/*!40101 LOCK TABLES smartcitydb.bot WRITE; */
/*!40000 ALTER TABLE smartcitydb.bot DISABLE KEYS */;
/* INSERT INTO smartcitydb.bot VALUES */;
/*!40000 ALTER TABLE smartcitydb.bot ENABLE KEYS */;
/*!40101 UNLOCK TABLES; */

--
-- Test data for table `trafficlight`
--

DROP TABLE IF EXISTS smartcitydb.trafficlight;
CREATE TABLE smartcitydb.trafficlight (
  `tlid` bigint(20) NOT NULL AUTO_INCREMENT,
  `direction` varchar(255) DEFAULT NULL,
  `point_id` bigint(20) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tlid`),
  KEY `fk_pointid` (`point_id`),
  CONSTRAINT `FK_36q0ntiwsex3ooj744c0t9py1` FOREIGN KEY (`point_id`) REFERENCES `point` (`pid`),
  CONSTRAINT `fk_pointid` FOREIGN KEY (`point_id`) REFERENCES `point` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*!40101 LOCK TABLES smartcitydb.trafficlight WRITE; */
/*!40000 ALTER TABLE smartcitydb.trafficlight DISABLE KEYS */;
INSERT INTO smartcitydb.trafficlight VALUES (1,'Z',5,'state');
/*!40000 ALTER TABLE smartcitydb.trafficlight ENABLE KEYS */;
/*!40101 UNLOCK TABLES; */
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
