/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-06-23 20:02:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pay_channel
-- ----------------------------
DROP TABLE IF EXISTS `pay_channel`;
CREATE TABLE `pay_channel` (
  `id` varchar(64) NOT NULL,
  `channel` varchar(4) DEFAULT NULL COMMENT '支付渠道',
  `name` varchar(60) DEFAULT NULL COMMENT '渠道名称',
  `code` varchar(5) DEFAULT NULL COMMENT '渠道code',
  `simple_name` varchar(20) DEFAULT NULL COMMENT '英文简写',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
