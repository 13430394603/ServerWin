/*
Navicat MySQL Data Transfer

Source Server         : win
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : server

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-06-05 13:02:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dm55`
-- ----------------------------
DROP TABLE IF EXISTS `dm55`;
CREATE TABLE `dm55` (
  `downMessage` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dm55
-- ----------------------------

-- ----------------------------
-- Table structure for `dm66`
-- ----------------------------
DROP TABLE IF EXISTS `dm66`;
CREATE TABLE `dm66` (
  `downMessage` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dm66
-- ----------------------------

-- ----------------------------
-- Table structure for `dm88`
-- ----------------------------
DROP TABLE IF EXISTS `dm88`;
CREATE TABLE `dm88` (
  `downMessage` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dm88
-- ----------------------------

-- ----------------------------
-- Table structure for `dm99`
-- ----------------------------
DROP TABLE IF EXISTS `dm99`;
CREATE TABLE `dm99` (
  `downMessage` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dm99
-- ----------------------------

-- ----------------------------
-- Table structure for `fre55`
-- ----------------------------
DROP TABLE IF EXISTS `fre55`;
CREATE TABLE `fre55` (
  `usercode` varchar(16) NOT NULL,
  PRIMARY KEY (`usercode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fre55
-- ----------------------------
INSERT INTO `fre55` VALUES ('21333');

-- ----------------------------
-- Table structure for `fre66`
-- ----------------------------
DROP TABLE IF EXISTS `fre66`;
CREATE TABLE `fre66` (
  `usercode` varchar(16) NOT NULL,
  PRIMARY KEY (`usercode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fre66
-- ----------------------------

-- ----------------------------
-- Table structure for `fre88`
-- ----------------------------
DROP TABLE IF EXISTS `fre88`;
CREATE TABLE `fre88` (
  `usercode` varchar(16) NOT NULL,
  PRIMARY KEY (`usercode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fre88
-- ----------------------------
INSERT INTO `fre88` VALUES ('99');

-- ----------------------------
-- Table structure for `fre99`
-- ----------------------------
DROP TABLE IF EXISTS `fre99`;
CREATE TABLE `fre99` (
  `usercode` varchar(16) NOT NULL,
  PRIMARY KEY (`usercode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fre99
-- ----------------------------
INSERT INTO `fre99` VALUES ('88');

-- ----------------------------
-- Table structure for `login`
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `usercode` varchar(16) NOT NULL,
  `pass` varchar(16) NOT NULL,
  `state` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`usercode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login
-- ----------------------------
INSERT INTO `login` VALUES ('55', '55', 'online');
INSERT INTO `login` VALUES ('66', '66', 'downline');
INSERT INTO `login` VALUES ('88', '88', 'online');
INSERT INTO `login` VALUES ('99', '99', 'online');

-- ----------------------------
-- Table structure for `register`
-- ----------------------------
DROP TABLE IF EXISTS `register`;
CREATE TABLE `register` (
  `usercode` varchar(16) NOT NULL,
  `pass` varchar(16) NOT NULL,
  `email` varchar(26) NOT NULL,
  `Aname` varchar(30) NOT NULL,
  PRIMARY KEY (`usercode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of register
-- ----------------------------
INSERT INTO `register` VALUES ('55', '55', '55', '');
INSERT INTO `register` VALUES ('66', '66', '66@', '未设置用户名');
INSERT INTO `register` VALUES ('88', '88', '88@qq.com', '88');
INSERT INTO `register` VALUES ('99', '99', '88@qq.com', '99');
DROP TRIGGER IF EXISTS `插入login`;
DELIMITER ;;
CREATE TRIGGER `插入login` AFTER INSERT ON `register` FOR EACH ROW INSERT INTO login(usercode,pass,state) VALUE(NEW.usercode,NEW.pass,'downline')
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `dele`;
DELIMITER ;;
CREATE TRIGGER `dele` AFTER DELETE ON `register` FOR EACH ROW DELETE FROM login WHERE usercode = OLD.usercode
;;
DELIMITER ;
