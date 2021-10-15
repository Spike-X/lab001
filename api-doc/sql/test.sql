DROP DATABASE if exists pcb ;
CREATE DATABASE pcb default character set 'utf8mb4';
use pcb;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for review_eigenvalue
-- ----------------------------
DROP TABLE IF EXISTS `review_eigenvalue`;
CREATE TABLE `review_eigenvalue`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `md5` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件md5特征值',
  `review_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '检视id(projectId)',
  `user_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户工号',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_md5_user_time`(`md5`(20), `user_no`(10), `create_time`) USING BTREE COMMENT '组合索引md5_user_time'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '检视特征值表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of review_eigenvalue
-- ----------------------------
INSERT INTO `review_eigenvalue` VALUES (1427923690907238401, '4ca1fc86607e785740d0c7af2990cc08', '4ca1fc86607e785740d0c7af2990cc08', 'zWX989878', 'zhangtao', '2021-08-24 10:15:19', '2021-08-24 10:15:19');
INSERT INTO `review_eigenvalue` VALUES (1428272237682917378, '0a250014347940875d422dd82b456782', '0a250014347940875d422dd82b456782', 'zWX989878', 'zhangtao', '2021-08-24 10:15:19', '2021-08-24 10:15:19');
INSERT INTO `review_eigenvalue` VALUES (1428272938874077186, '170ecb8475ca6e384dbd74c17e165c9e', '170ecb8475ca6e384dbd74c17e165c9e', 'zWX989878', 'zhangtao', '2021-08-24 10:15:19', '2021-08-24 10:15:19');
INSERT INTO `review_eigenvalue` VALUES (1428276366400634881, '5eb63bbbe01eeed093cb22bb8f5acdc3', '5eb63bbbe01eeed093cb22bb8f5acdc3', 'zWX989878', 'zhangtao', '2021-08-24 10:15:19', '2021-08-24 10:15:19');
INSERT INTO `review_eigenvalue` VALUES (1428276366400634882, '5eb63bbbe01eeed093cb22bb8f5acdc3', '5eb63bbbe01eeed093cb22bb8f5acdc3', 'zWX989878', 'zhangtao', '2021-08-25 10:15:19', '2021-08-25 10:15:19');
INSERT INTO `review_eigenvalue` VALUES (1428310206666051586, '49976d37222ab1089a8c413b4520cc55', '49976d37222ab1089a8c413b4520cc55', 'zWX989878', 'zhangtao', '2021-08-25 10:15:19', '2021-08-25 10:15:19');
INSERT INTO `review_eigenvalue` VALUES (1429989788112711682, '835741aba850778a5b06bfd57f55c98c', '835741aba850778a5b06bfd57f55c98c', 'zWX989818', 'zhangtao', '2021-08-25 16:11:48', '2021-08-25 16:11:48');
INSERT INTO `review_eigenvalue` VALUES (1432657163169591297, '5d15dccef8fc01c81446dd245bb4202a', '5d15dccef8fc01c81446dd245bb4202a', 'zWX989878', 'zhangtao', '2021-08-31 18:51:00', '2021-08-31 18:51:00');
INSERT INTO `review_eigenvalue` VALUES (1432657379465654274, '168f94d2d3c5b44e941a65780ad4a7ce', '168f94d2d3c5b44e941a65780ad4a7ce', 'zWX989878', 'zhangtao', '2021-08-31 18:51:51', '2021-08-31 18:51:51');
INSERT INTO `review_eigenvalue` VALUES (1432657927816376321, '77777777888888889999999900000000', 'f1724b492d8baebecda072d01de472e6', 'zWX989878', 'zhangtao', '2021-08-31 18:54:02', '2021-08-31 18:54:02');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父菜单id',
  `menu_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `menu_type` tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '菜单类型（2目录 1菜单 0按钮）',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除（1是，0否）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE COMMENT '测试'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1427568115883196418, 0, 'test', 0, 0, '', 0, '2021-08-17 17:48:56', '2021-08-17 17:48:56');
INSERT INTO `sys_menu` VALUES (1427891693413511170, 0, 'test110', 1, 0, '', 0, '2021-08-18 15:14:43', '2021-08-18 15:30:32');
INSERT INTO `sys_menu` VALUES (1427923690907238401, 0, 'wtf', 0, 0, '', 0, '2021-08-18 17:21:52', '2021-08-18 17:21:52');
INSERT INTO `sys_menu` VALUES (1428172610572972034, 1427923690907238401, '400800', 0, 0, '', 0, '2021-08-19 09:50:59', '2021-08-19 09:50:59');
INSERT INTO `sys_menu` VALUES (1428272237682917378, 0, '121', 1, 0, '', 0, '2021-08-19 16:26:52', '2021-08-19 16:26:52');
INSERT INTO `sys_menu` VALUES (1428272938874077186, 0, '911', 0, 0, '', 0, '2021-08-19 16:28:10', '2021-08-19 16:28:10');
INSERT INTO `sys_menu` VALUES (1428276366400634881, 0, '798', 1, 0, '', 0, '2021-08-19 16:42:32', '2021-09-07 14:44:32');
INSERT INTO `sys_menu` VALUES (1428310206666051586, 0, 'haha', 0, 0, '', 0, '2021-08-19 18:57:45', '2021-09-07 14:44:32');

SET FOREIGN_KEY_CHECKS = 1;
