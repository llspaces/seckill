--秒杀Demo sql脚本
-- ----------------------------
-- Table structure for `goods`
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(255) DEFAULT NULL,
  `goods_img` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `goods_detail` longtext COMMENT '商品详情介绍',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `goods_stock` int(11) DEFAULT '0' COMMENT '商品库存 -1表示无限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', 'iphonex', 'Apple iPhoneX(A1865) 64GB 银色 移动联通电信4G手机', '/img/iphonex.png', 'Apple iPhoneX(A1865) 64GB 银色 移动联通电信4G手机', '8765.00', '10000');
INSERT INTO `goods` VALUES ('2', '华为meta10', '华为meta10 4GB+32GB 月光银 移动联通电信4G手机 双卡双待', '/img/meta10.png', '华为meta10 4GB+32GB 月光银 移动联通电信4G手机 双卡双待', '3212.00', '-1');

-- ----------------------------
-- Table structure for `order_info`
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `delivery_addr_id` int(11) DEFAULT NULL COMMENT '收货地址ID',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `goods_count` int(11) DEFAULT NULL COMMENT '商品数量',
  `goods_price` decimal(10,2) DEFAULT '0.00',
  `order_channel` tinyint(4) DEFAULT NULL COMMENT '1 pc 2 android 3 ios',
  `status` tinyint(4) DEFAULT NULL COMMENT '订单状态：0 新建未支付 1 已支付 2 已发货 3 已收货 4 已退款 5 已完成',
  `create_date` datetime DEFAULT NULL COMMENT '订单创建时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_info
-- ----------------------------

-- ----------------------------
-- Table structure for `seckill_goods`
-- ----------------------------
DROP TABLE IF EXISTS `seckill_goods`;
CREATE TABLE `seckill_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品ID',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `seckill_price` decimal(10,2) DEFAULT '0.00' COMMENT '秒杀单价',
  `stock_count` int(11) DEFAULT NULL COMMENT '库存数量',
  `start_date` datetime DEFAULT NULL COMMENT '开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seckill_goods
-- ----------------------------
INSERT INTO `seckill_goods` VALUES ('1', '1', '888.88', '4', '2019-06-27 23:59:59', '2019-06-30 23:59:59');
INSERT INTO `seckill_goods` VALUES ('2', '2', '666.88', '10', '2019-06-27 23:59:59', '2019-06-30 23:59:59');
-- ----------------------------
-- Table structure for `seckill_order`
-- ----------------------------
DROP TABLE IF EXISTS `seckill_order`;
CREATE TABLE `seckill_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '秒杀订单ID',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `order_id` int(11) DEFAULT NULL COMMENT '订单ID',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seckill_order
-- ----------------------------

-- ----------------------------
-- Table structure for `seckill_user`
-- ----------------------------
DROP TABLE IF EXISTS `seckill_user`;
CREATE TABLE `seckill_user` (
  `id` bigint(11) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `head` varchar(255) DEFAULT NULL,
  `register_date` datetime DEFAULT NULL,
  `last_login_date` datetime DEFAULT NULL,
  `login_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seckill_user
-- ----------------------------
INSERT INTO `seckill_user` VALUES ('17001224729', 'llspace', '0e46da2dc126e939ff4c523824cef73d', 'llspace', null, '2019-06-19 17:46:45', '2019-06-19 17:46:51', '1');
