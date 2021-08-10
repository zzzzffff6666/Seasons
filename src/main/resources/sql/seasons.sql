-- MySQL dump 10.13  Distrib 8.0.25, for macos11 (x86_64)
--
-- Host: localhost    Database: seasons
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit` (
  `item` varchar(30) NOT NULL COMMENT '审核条目：type#id',
  `auditor` int NOT NULL COMMENT '审核人ID',
  `auditor_name` varchar(10) NOT NULL COMMENT '审核人名称',
  `state` int NOT NULL COMMENT '审核结果：0通过，1未通过',
  `opinion` varchar(100) DEFAULT NULL COMMENT '审核意见',
  `created` datetime DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`item`,`auditor`),
  KEY `auditor` (`auditor`),
  KEY `created` (`created` DESC),
  KEY `item` (`item`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit`
--

LOCK TABLES `audit` WRITE;
/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buy`
--

DROP TABLE IF EXISTS `buy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buy` (
  `uid` int NOT NULL COMMENT '用户ID',
  `wid` int NOT NULL COMMENT '作品ID',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `type` int NOT NULL COMMENT '支付类型：0非商用，1商用',
  `way` varchar(10) NOT NULL COMMENT '支付方式：微信/支付宝/余额',
  `created` datetime NOT NULL COMMENT '支付时间',
  PRIMARY KEY (`uid`,`wid`),
  KEY `created` (`created` DESC),
  KEY `uid` (`uid`),
  KEY `wid` (`wid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buy`
--

LOCK TABLES `buy` WRITE;
/*!40000 ALTER TABLE `buy` DISABLE KEYS */;
INSERT INTO `buy` VALUES (1,1,300.00,0,'余额','2021-08-05 11:51:29');
/*!40000 ALTER TABLE `buy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `laud`
--

DROP TABLE IF EXISTS `laud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `laud` (
  `uid` int NOT NULL COMMENT '用户ID',
  `wid` int NOT NULL COMMENT '作品ID',
  `created` datetime NOT NULL COMMENT '点赞时间',
  `title` varchar(20) DEFAULT NULL COMMENT '作品标题',
  `url` varchar(100) DEFAULT NULL COMMENT '作品URL',
  PRIMARY KEY (`uid`,`wid`),
  KEY `uid` (`uid`),
  KEY `wid` (`wid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `laud`
--

LOCK TABLES `laud` WRITE;
/*!40000 ALTER TABLE `laud` DISABLE KEYS */;
/*!40000 ALTER TABLE `laud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manage`
--

DROP TABLE IF EXISTS `manage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage` (
  `uid` int NOT NULL COMMENT '用户ID',
  `max` int DEFAULT NULL COMMENT '最大风格分区管理数量',
  `current` int DEFAULT NULL COMMENT '当前风格分区管理数量',
  PRIMARY KEY (`uid`),
  CONSTRAINT `check_max` CHECK ((`max` >= `current`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manage`
--

LOCK TABLES `manage` WRITE;
/*!40000 ALTER TABLE `manage` DISABLE KEYS */;
/*!40000 ALTER TABLE `manage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `mid` bigint NOT NULL AUTO_INCREMENT COMMENT '信息ID',
  `receiver` int NOT NULL COMMENT '用户ID',
  `sender` int NOT NULL COMMENT '发送人ID',
  `sender_name` varchar(10) NOT NULL COMMENT '发送人名称',
  `msg` varchar(100) DEFAULT NULL COMMENT '消息内容',
  `url` varchar(100) DEFAULT NULL COMMENT '链接请求',
  `read` tinyint(1) NOT NULL COMMENT '是否已读',
  `created` datetime NOT NULL COMMENT '时间',
  PRIMARY KEY (`mid`),
  KEY `sender` (`sender`),
  KEY `receiver` (`receiver`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `oid` int NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `uid` int NOT NULL COMMENT '用户ID',
  `coin` decimal(10,2) NOT NULL COMMENT '金额',
  `state` int NOT NULL COMMENT '支付状态：0未支付，1已支付',
  `created` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`oid`),
  KEY `created` (`created` DESC),
  KEY `state` (`state`),
  KEY `uid` (`uid`),
  CONSTRAINT `uid5` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
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
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `pid` int NOT NULL COMMENT '权限ID',
  `name` varchar(20) NOT NULL COMMENT '权限名称',
  PRIMARY KEY (`pid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (12,'*:*'),(11,'log:*'),(1,'user:*'),(3,'user:delete'),(2,'user:insert'),(5,'user:select'),(4,'user:update'),(6,'work:*'),(8,'work:delete'),(7,'work:insert'),(10,'work:select'),(9,'work:update');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `rid` int NOT NULL COMMENT '角色ID',
  `name` varchar(10) NOT NULL COMMENT '角色名称',
  `desc` varchar(30) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`rid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'user','普通用户'),(2,'creator','创作者，上传作品'),(3,'partner','合作商家，采购作品'),(4,'admin','管理员，后台管理'),(5,'root','源管理员，一切权限');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permission` (
  `rid` int NOT NULL COMMENT '角色ID',
  `pid` int NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`rid`,`pid`),
  KEY `rid` (`rid`),
  KEY `pid` (`pid`),
  CONSTRAINT `pid1` FOREIGN KEY (`pid`) REFERENCES `permission` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rid2` FOREIGN KEY (`rid`) REFERENCES `role` (`rid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (1,3),(1,4),(1,5),(1,10),(2,7),(2,8),(2,9),(4,1),(4,2),(4,6),(4,11),(5,12);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `style`
--

DROP TABLE IF EXISTS `style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `style` (
  `sid` int NOT NULL AUTO_INCREMENT COMMENT '风格ID',
  `manager` int NOT NULL COMMENT '管理者ID',
  `name` varchar(10) NOT NULL COMMENT '风格名称',
  `work_num` int NOT NULL COMMENT '总的作品数量',
  `weekly_num` int NOT NULL COMMENT '本周新增数量',
  `daily_num` int NOT NULL COMMENT '当日新增数量',
  `updated` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`sid`),
  UNIQUE KEY `name` (`name`),
  KEY `manager` (`manager`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `style`
--

LOCK TABLES `style` WRITE;
/*!40000 ALTER TABLE `style` DISABLE KEYS */;
/*!40000 ALTER TABLE `style` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscribe`
--

DROP TABLE IF EXISTS `subscribe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscribe` (
  `subscriber` int NOT NULL COMMENT '订阅者ID',
  `publisher` int NOT NULL COMMENT '创作者ID',
  `level` int NOT NULL COMMENT '订阅等级',
  `created` datetime NOT NULL COMMENT '订阅时间',
  PRIMARY KEY (`publisher`,`subscriber`),
  KEY `publisher` (`publisher`),
  KEY `subscriber` (`subscriber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscribe`
--

LOCK TABLES `subscribe` WRITE;
/*!40000 ALTER TABLE `subscribe` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscribe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `uid` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(10) NOT NULL COMMENT '用户昵称',
  `phone` char(11) DEFAULT NULL COMMENT '手机号',
  `password` varchar(32) NOT NULL COMMENT '加密后的密码',
  `salt` varchar(20) NOT NULL COMMENT '盐值',
  `coin` decimal(10,2) DEFAULT NULL COMMENT '余额',
  `active` tinyint(1) NOT NULL COMMENT '活跃状态：0冻结，1活跃',
  `login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `phone` (`phone`),
  KEY `active` (`active`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'root','17770517023','797da2a716bf846f9feb37c3d470e5ae','kNuuDBjLMNN29Ce75TtA',10000.00,1,NULL),(10000,'admin',NULL,'8004a3454dc6b792226e1a6242a33cd3','VTVRN50jXNhbZ2w5Khni',10000.00,1,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `uid` int NOT NULL COMMENT '用户ID',
  `rid` int NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`uid`,`rid`),
  KEY `uid` (`uid`),
  KEY `rid` (`rid`),
  CONSTRAINT `rid1` FOREIGN KEY (`rid`) REFERENCES `role` (`rid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,5);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work`
--

DROP TABLE IF EXISTS `work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `work` (
  `wid` int NOT NULL AUTO_INCREMENT COMMENT '作品ID',
  `creator` int NOT NULL COMMENT '创作者ID',
  `creator_name` varchar(10) DEFAULT NULL COMMENT '作者名称',
  `style` varchar(10) NOT NULL COMMENT '作品风格',
  `title` varchar(20) NOT NULL COMMENT '作品标题',
  `content` varchar(100) DEFAULT NULL COMMENT '作品内容',
  `laud_num` int NOT NULL COMMENT '点赞数',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `price_biz` decimal(10,2) NOT NULL COMMENT '商用价格',
  `url` varchar(100) NOT NULL COMMENT '资源url',
  `state` int NOT NULL COMMENT '作品状态：0正常，1待审核',
  `created` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`wid`),
  KEY `price` (`price`),
  KEY `title` (`title`),
  KEY `creator` (`creator`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work`
--

LOCK TABLES `work` WRITE;
/*!40000 ALTER TABLE `work` DISABLE KEYS */;
/*!40000 ALTER TABLE `work` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-10 16:29:26
