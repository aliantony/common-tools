-- MySQL dump 10.13  Distrib 5.7.24, for Win64 (x86_64)
--
-- Host: 10.240.50.103    Database: csom
-- ------------------------------------------------------
-- Server version	5.7.27

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
-- Table structure for table `asset`
--

DROP TABLE IF EXISTS `asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_id` bigint(20) NOT NULL COMMENT '业务主键(四库)',
  `asset_group` varchar(255) DEFAULT NULL COMMENT '资产组（逗号分隔）',
  `number` varchar(32) DEFAULT NULL COMMENT '资产编号',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '资产名称',
  `install_type` tinyint(3) DEFAULT '1' COMMENT '安装方式：1-人工，2-自动',
  `serial` varchar(32) DEFAULT '' COMMENT '序列号',
  `area_id` int(11) DEFAULT NULL COMMENT '行政区划主键',
  `category_model` int(11) DEFAULT NULL COMMENT '品类型号',
  `manufacturer` varchar(80) DEFAULT NULL COMMENT '厂商',
  `asset_status` tinyint(3) DEFAULT '0' COMMENT '资产状态：1-待登记，2-不予登记，3-待实施，4-待验证，5-待入网，6-已入网,7-待检查，8-待整改，9-变更中, 10-待退役，11-已退役',
  `admittance_status` tinyint(3) unsigned zerofill DEFAULT '1' COMMENT '准入状态：1-待设置，2-已允许，3-已禁止',
  `operation_system` bigint(20) DEFAULT '' COMMENT '操作系统',
  `responsible_user_id` int(11) DEFAULT NULL COMMENT '责任人主键',
  `location` varchar(64) DEFAULT '' COMMENT '物理位置',
  `latitude` varchar(16) DEFAULT '' COMMENT '纬度',
  `longitude` varchar(16) DEFAULT '' COMMENT '经度',
  `house_location` varchar(64) DEFAULT NULL COMMENT '机房位置',
  `firmware_version` varchar(30) DEFAULT NULL COMMENT '固件版本',
  `uuid` varchar(64) DEFAULT NULL COMMENT '终端UUID',
  `asset_source` tinyint(3) DEFAULT '0' COMMENT '上报来源:1-自动上报，2-人工上报',
  `importance_degree` tinyint(3) DEFAULT NULL COMMENT '资产重要程度：1-核心,2-重要,3一般',
  `describle` varchar(300) DEFAULT NULL COMMENT '描述',
  `first_enter_nett` bigint(20) DEFAULT NULL COMMENT '首次入网时间',
  `first_discover_time` bigint(20) DEFAULT NULL COMMENT '首次发现时间',
  `is_innet` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否入网：0-未入网,1-表示入网',
  `service_life` bigint(20) DEFAULT '0' COMMENT '使用到期时间',
  `buy_date` bigint(20) DEFAULT '0' COMMENT '购买日期',
  `version` varchar(64) NOT NULL COMMENT '版本',
  `warranty` varchar(30) DEFAULT NULL COMMENT '保修期',
  `install_template_id` int(11) DEFAULT NULL COMMENT '装机模板主键',
  `baseline_template_id` int(11) DEFAULT NULL COMMENT '基准模板主键',
  `baseline_template_correlation_gmt` bigint(20) DEFAULT '0' COMMENT '资产与基准模板关联时间',
  `install_template_correlation_gmt` bigint(20) DEFAULT '0' COMMENT '资产与装机模板关联时间',
  `memo` varchar(300) DEFAULT NULL COMMENT '备注',
  `gmt_create` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT '0' COMMENT '更新时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_user` int(11) DEFAULT '0' COMMENT '修改人',
  `status` tinyint(3) unsigned zerofill DEFAULT '1' COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `number` (`number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_assembly`
--

DROP TABLE IF EXISTS `asset_assembly`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_assembly` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_id` int(11) NOT NULL COMMENT '资产主键',
  `amount` tinyint(3) DEFAULT NULL COMMENT '组件数量',
  `business_id` int(11) NOT NULL COMMENT '组件主键',
  `status` tinyint(3) unsigned zerofill DEFAULT '1' COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产组件关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_assembly_lib`
--

DROP TABLE IF EXISTS `asset_assembly_lib`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_assembly_lib` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_id` bigint(11) NOT NULL COMMENT '业务主键',
  `type` varchar(16) DEFAULT NULL COMMENT '组件类型',
  `supplier` varchar(64) NOT NULL COMMENT '供应商',
  `product_name` varchar(500) NOT NULL COMMENT '产品名',
  `version` varchar(64) DEFAULT NULL COMMENT '版本号',
  `sys_version` varchar(32) DEFAULT NULL COMMENT '系统版本',
  `language` varchar(32) DEFAULT NULL COMMENT '语言',
  `other` varchar(128) DEFAULT NULL COMMENT '其他',
  `memo` varchar(1024) DEFAULT NULL COMMENT '备注',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `is_storage` tinyint(3) DEFAULT NULL COMMENT '是否入库：1已入库、2未入库',
  `status` int(11) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `index_business_id` (`business_id`) USING BTREE,
  KEY `index_gmt_create` (`gmt_create`) USING BTREE,
  KEY `index_id_status` (`id`,`status`) USING BTREE,
  KEY `name_index` (`supplier`,`product_name`,`version`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_assembly_soft_relation`
--

DROP TABLE IF EXISTS `asset_assembly_soft_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_assembly_soft_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `soft_id` bigint(20) NOT NULL COMMENT '软件业务id',
  `assembly_id` bigint(20) NOT NULL COMMENT '组件业务id',
  `memo` varchar(1024) DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `status` int(11) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_` (`soft_id`) USING BTREE,
  KEY `index_soft_id` (`soft_id`) USING BTREE,
  KEY `index_assembly_id` (`assembly_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组件与软件关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_cpe_filter`
--

DROP TABLE IF EXISTS `asset_cpe_filter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_cpe_filter` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_id` bigint(11) DEFAULT NULL COMMENT '业务主键',
  `product_name` varchar(255) DEFAULT NULL COMMENT '产品名称',
  `serial_name` varchar(255) DEFAULT NULL COMMENT '系列名称',
  `type` varchar(10) DEFAULT NULL COMMENT '类型',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `status` int(11) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `index_business_id` (`business_id`) USING BTREE,
  UNIQUE KEY `index_product_name` (`product_name`) USING BTREE COMMENT '操作系统唯一所索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='过滤显示表（筛选指定的数据给用户）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_department`
--

DROP TABLE IF EXISTS `asset_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '部门名',
  `responsible_user` varchar(32) DEFAULT '' COMMENT '负责人',
  `telephone` varchar(15) DEFAULT '' COMMENT '联系电话',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级部门',
  `memo` varchar(300) DEFAULT NULL COMMENT '备注',
  `gmt_create` bigint(20) DEFAULT '0' COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT '0' COMMENT '修改时间',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人',
  `modify_user` int(11) DEFAULT '0' COMMENT '修改人',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态,1未删除,0已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='资产部门信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_group`
--

DROP TABLE IF EXISTS `asset_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `purpose` varchar(128) DEFAULT NULL COMMENT '用途',
  `important_degree` tinyint(3) NOT NULL COMMENT '重要程度：0-不重要,1- 一般,3-重要',
  `name` varchar(90) NOT NULL COMMENT '名称',
  `gmt_create` bigint(20) DEFAULT '0' COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT '0' COMMENT '修改时间',
  `memo` varchar(300) DEFAULT '' COMMENT '备注',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人',
  `modify_user` int(11) DEFAULT '0' COMMENT '修改人',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_group_relation`
--

DROP TABLE IF EXISTS `asset_group_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_group_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资产与资产组主键',
  `asset_group_id` int(11) NOT NULL COMMENT '资产组主键',
  `asset_id` int(11) NOT NULL COMMENT '资产主键',
  `gmt_create` bigint(20) DEFAULT '0' COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT '0' COMMENT '修改时间',
  `memo` varchar(300) DEFAULT NULL COMMENT '备注',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人',
  `modify_user` int(11) DEFAULT '0' COMMENT '修改人',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_asset_group_id_asset_id` (`asset_group_id`,`asset_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产与资产组关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_hard_assembly_relation`
--

DROP TABLE IF EXISTS `asset_hard_assembly_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_hard_assembly_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `hard_id` bigint(20) NOT NULL COMMENT '硬件id',
  `assembly_id` bigint(20) NOT NULL COMMENT '组件id',
  `memo` varchar(1024) DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `status` int(11) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_hard_id` (`hard_id`) USING BTREE,
  KEY `index_assembly_id` (`assembly_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='硬件与组件关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_hard_soft_lib`
--

DROP TABLE IF EXISTS `asset_hard_soft_lib`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_hard_soft_lib` (
  `id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_id` bigint(20) NOT NULL COMMENT '业务主键',
  `number` int(11) DEFAULT NULL COMMENT '编号',
  `type` varchar(8) NOT NULL COMMENT '类型：a-应用软件 h-硬件 o-操作系统',
  `supplier` varchar(64) DEFAULT NULL COMMENT '供应商',
  `product_name` varchar(128) DEFAULT NULL COMMENT '产品名',
  `version` varchar(64) DEFAULT NULL COMMENT '版本号',
  `upgrade_msg` varchar(64) DEFAULT NULL COMMENT '更新信息',
  `sys_version` varchar(64) DEFAULT NULL COMMENT '系统版本',
  `language` varchar(8) DEFAULT NULL COMMENT '语言',
  `soft_version` varchar(64) DEFAULT NULL COMMENT '软件版本',
  `soft_platform` varchar(64) DEFAULT NULL COMMENT '软件平台',
  `hard_platform` varchar(32) DEFAULT NULL COMMENT '硬件平台',
  `other` varchar(32) DEFAULT NULL COMMENT '其他',
  `data_source` tinyint(3) DEFAULT NULL COMMENT '数据来源：1-CPE，2-MANUAL',
  `is_storage` tinyint(3) DEFAULT NULL COMMENT '是否入库：1-已入库、2-未入库',
  `cpe_uri` varchar(640) DEFAULT NULL COMMENT 'cpe路径',
  `memo` varchar(1024) DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `status` int(11) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `index_business_id` (`business_id`) USING BTREE COMMENT '业务主键唯一索引',
  KEY `index_gmt_create` (`gmt_create`) USING BTREE,
  KEY `id_type_status` (`id`,`type`,`status`,`product_name`,`supplier`) USING BTREE,
  KEY `index_relation` (`id`,`business_id`,`type`,`is_storage`,`supplier`,`product_name`,`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='CPE表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_install_history`
--

DROP TABLE IF EXISTS `asset_install_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_install_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_id` int(11) NOT NULL COMMENT '设备ID',
  `package_id` int(11) NOT NULL COMMENT '安装包ID',
  `type` tinyint(3) DEFAULT NULL COMMENT '类型：1、升级包，2、特征库',
  `upgrade_type` varchar(32) DEFAULT NULL COMMENT '升级方式，MANUAL(人工），AUTO_MATIC（自动)',
  `upgrade_status` varchar(32) DEFAULT NULL COMMENT '升级状态:SUCCESS(成功），FAIL(失败)',
  `version` varchar(32) DEFAULT NULL COMMENT '版本',
  `upgrade_date` bigint(20) DEFAULT '0' COMMENT '升级时间',
  `gmt_create` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT '0' COMMENT '更新时间',
  `memo` varchar(300) DEFAULT NULL COMMENT '备注',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_user` int(11) DEFAULT '0' COMMENT '修改人',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态,1 未删除,0已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `UK_ASSET_PACKAGE` (`asset_id`,`package_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=710 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='安装记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_install_template`
--

DROP TABLE IF EXISTS `asset_install_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_install_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL COMMENT '模板名称',
  `number_code` varchar(64) NOT NULL COMMENT '模板编号',
  `category_model` int(11) NOT NULL COMMENT '品类型号',
  `current_status` tinyint(3) DEFAULT NULL COMMENT '状态：1-待审核，2-拒绝，3-启用，4-禁用',
  `operation_system` bigint(20) NOT NULL COMMENT '适用操作系统',
  `operation_system_name` varchar(120) NOT NULL COMMENT '操作系统名称',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='装机模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_install_template_check`
--

DROP TABLE IF EXISTS `asset_install_template_check`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_install_template_check` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `install_template_id` int(11) NOT NULL COMMENT '装机模板主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户主键',
  `advice` varchar(255) DEFAULT NULL COMMENT '审核意见',
  `result` tinyint(3) NOT NULL COMMENT '审核结果：1-提交审核 2-拒绝 3 审核通过',
  `gmt_create` bigint(20) DEFAULT '0' COMMENT '创建时间',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='装机模板审核表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_ip_relation`
--

DROP TABLE IF EXISTS `asset_ip_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_ip_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_id` int(11) NOT NULL COMMENT '资产主表',
  `ip` varchar(15) NOT NULL COMMENT 'IP',
  `net` int(11) DEFAULT NULL COMMENT '网口',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产-IP关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_link_relation`
--

DROP TABLE IF EXISTS `asset_link_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_link_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_id` int(11) NOT NULL COMMENT '资产主键',
  `asset_ip` varchar(15) NOT NULL COMMENT '资产IP',
  `asset_port` varchar(64) DEFAULT NULL COMMENT '资产端口',
  `parent_asset_id` int(11) NOT NULL DEFAULT '0' COMMENT '父级设备主键',
  `parent_asset_ip` varchar(15) NOT NULL DEFAULT '0' COMMENT '父级设备IP',
  `parent_asset_port` varchar(64) DEFAULT NULL COMMENT '父级设备端口',
  `gmt_create` bigint(20) DEFAULT '0' COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT '0' COMMENT '修改时间',
  `memo` varchar(300) DEFAULT NULL COMMENT '备注',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人',
  `modify_user` int(11) DEFAULT '0' COMMENT '修改人',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通联关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_mac_relation`
--

DROP TABLE IF EXISTS `asset_mac_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_mac_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_id` int(11) NOT NULL COMMENT '资产主表',
  `mac` varchar(17) DEFAULT NULL COMMENT 'MAC',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产-MAC关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_network_equipment`
--

DROP TABLE IF EXISTS `asset_network_equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_network_equipment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_id` int(11) NOT NULL COMMENT '资产主键',
  `outer_ip` varchar(15) DEFAULT NULL COMMENT '外网IP',
  `interface_size` tinyint(3) DEFAULT NULL COMMENT '接口数目',
  `is_wireless` bit(1) DEFAULT NULL COMMENT '是否无线：0-否,1-是',
  `port_size` int(11) DEFAULT NULL COMMENT '端口数目',
  `cpu_version` varchar(64) DEFAULT NULL COMMENT 'CPU版本',
  `cpu_size` int(11) DEFAULT NULL COMMENT 'CPU大小',
  `ios` varchar(64) DEFAULT NULL COMMENT 'IOS',
  `firmware_version` varchar(32) DEFAULT NULL COMMENT '固件版本',
  `subnet_mask` varchar(15) DEFAULT '' COMMENT '子网掩码',
  `expect_bandwidth` int(11) DEFAULT NULL COMMENT '预计带宽(M)',
  `register` int(11) DEFAULT NULL COMMENT '配置寄存器(GB)',
  `dram_size` float DEFAULT NULL COMMENT 'DRAM大小',
  `flash_size` float DEFAULT NULL COMMENT 'FLASH大小',
  `ncrm_size` float DEFAULT NULL COMMENT 'NCRM大小',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人',
  `modify_user` int(11) DEFAULT NULL COMMENT '修改人',
  `gmt_create` bigint(20) DEFAULT '0' COMMENT '创建时间',
  `memo` varchar(64) DEFAULT '' COMMENT '备注',
  `gmt_modified` bigint(20) DEFAULT '0' COMMENT '更新时间',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_NETWORK_ASSET_ID` (`asset_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网络设备详情表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_operation_record`
--

DROP TABLE IF EXISTS `asset_operation_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_operation_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `target_object_id` int(11) NOT NULL COMMENT '被操作的对象ID',
  `origin_status` tinyint(3) NOT NULL COMMENT '原始状态',
  `target_status` int(11) DEFAULT NULL COMMENT '目标状态',
  `content` varchar(300) DEFAULT '' COMMENT '本次操作(类型)内容',
  `operate_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `process_result` varchar(255) DEFAULT NULL COMMENT '处理结果：0拒绝1同意',
  `operate_user_name` varchar(255) DEFAULT NULL COMMENT '操作人名字',
  `note` varchar(1024) NOT NULL DEFAULT '' COMMENT '当前操作输入的备注信息',
  `file_info` varchar(1024) NOT NULL DEFAULT '' COMMENT '附件信息JSON',
  `gmt_create` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `status` tinyint(3) unsigned zerofill DEFAULT '1' COMMENT '状态：1未删除,0已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COMMENT='资产动态表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_patch_install_template`
--

DROP TABLE IF EXISTS `asset_patch_install_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_patch_install_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `install_template_id` int(11) NOT NULL COMMENT '模板主键',
  `patch_id` int(11) NOT NULL COMMENT '补丁主键',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='装机模板与补丁关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_protocol`
--

DROP TABLE IF EXISTS `asset_protocol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_protocol` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_id` bigint(11) NOT NULL COMMENT '业务主键',
  `name` varchar(128) NOT NULL COMMENT '协议',
  `memo` varchar(1024) DEFAULT NULL COMMENT '备注',
  `is_storage` tinyint(3) DEFAULT '2' COMMENT '是否入库：1-已入库，2-未入库',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `link_vuls` varchar(1024) DEFAULT NULL COMMENT '关联的遏制漏洞编号',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `status` int(11) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_business_id` (`business_id`) USING BTREE,
  KEY `index_id` (`id`) USING BTREE,
  KEY `index_gmt_create` (`gmt_create`) USING BTREE,
  KEY `index_is_storage` (`is_storage`) USING BTREE,
  KEY `protocol_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='协议表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_safety_equipment`
--

DROP TABLE IF EXISTS `asset_safety_equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_safety_equipment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_id` int(11) NOT NULL COMMENT '资产主键',
  `avlx_version` varchar(64) DEFAULT NULL COMMENT 'AVLX检测引擎版本号',
  `command_control_channel` varchar(64) DEFAULT NULL COMMENT '命令与控制通道检测引擎版本号',
  `is_manage` bit(1) DEFAULT NULL COMMENT '是否纳入管理',
  `new_version` varchar(255) DEFAULT NULL COMMENT '最新版本',
  `url` varchar(355) DEFAULT NULL COMMENT 'URL地址',
  `feature_library` varchar(255) DEFAULT NULL COMMENT '特征库版本',
  `network_state` tinyint(3) DEFAULT NULL COMMENT '网络状态',
  `strategy` varchar(255) DEFAULT NULL COMMENT '策略',
  `memo` varchar(300) DEFAULT NULL COMMENT '备注',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人',
  `modify_user` int(11) DEFAULT '0' COMMENT '修改人',
  `gmt_create` bigint(20) DEFAULT '0' COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT '0' COMMENT '更新时间',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `INX_ASSET_ID` (`asset_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='安全设备表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_service_depend`
--

DROP TABLE IF EXISTS `asset_service_depend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_service_depend` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `service_id` bigint(11) NOT NULL COMMENT '服务id',
  `service_name` varchar(64) DEFAULT NULL COMMENT '服务名',
  `depend_service` bigint(11) NOT NULL COMMENT '依赖服务id',
  `depend_service_name` varchar(64) DEFAULT NULL COMMENT '依赖服务名',
  `memo` varchar(1024) DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `status` int(11) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `service_id` (`service_id`) USING BTREE,
  KEY `depend_service` (`depend_service`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务依赖的服务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_service_port_relation`
--

DROP TABLE IF EXISTS `asset_service_port_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_service_port_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `service_id` bigint(11) NOT NULL COMMENT '服务id',
  `port` int(11) NOT NULL COMMENT '端口',
  `memo` varchar(1024) DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `status` int(11) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `service_id` (`service_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务与端口表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_service_protocol_relation`
--

DROP TABLE IF EXISTS `asset_service_protocol_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_service_protocol_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `service_id` bigint(11) NOT NULL COMMENT '服务id',
  `protocol_id` bigint(11) NOT NULL COMMENT '协议id',
  `memo` varchar(1024) DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `status` int(11) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `service_id` (`service_id`) USING BTREE,
  KEY `protocol_id` (`protocol_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务与协议表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_soft_port_relation`
--

DROP TABLE IF EXISTS `asset_soft_port_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_soft_port_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `soft_id` bigint(11) NOT NULL COMMENT '软件id',
  `port` int(11) NOT NULL COMMENT '端口',
  `memo` varchar(1024) DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `status` int(11) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_soft_id` (`soft_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='软件与端口表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_soft_protocol_relation`
--

DROP TABLE IF EXISTS `asset_soft_protocol_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_soft_protocol_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `soft_id` bigint(20) NOT NULL COMMENT '软件id',
  `protocol_id` bigint(20) NOT NULL COMMENT '协议id',
  `memo` varchar(1024) DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `status` int(11) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_soft_id` (`soft_id`) USING BTREE,
  KEY `index_protocol_id` (`protocol_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='软件与协议表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_soft_service_lib`
--

DROP TABLE IF EXISTS `asset_soft_service_lib`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_soft_service_lib` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `soft_id` bigint(20) NOT NULL COMMENT '软件id',
  `service_id` bigint(20) NOT NULL COMMENT '服务id',
  `memo` varchar(1024) DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `status` int(11) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_soft_id` (`soft_id`) USING BTREE,
  KEY `index_service_id` (`service_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='软件提供的服务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_soft_service_relation`
--

DROP TABLE IF EXISTS `asset_soft_service_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_soft_service_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `soft_id` bigint(20) NOT NULL COMMENT '软件id',
  `service_id` bigint(20) NOT NULL COMMENT '服务id',
  `memo` varchar(1024) DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `status` int(11) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_soft_id` (`soft_id`) USING BTREE,
  KEY `index_service_id` (`service_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='软件依赖的服务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_software_install_template`
--

DROP TABLE IF EXISTS `asset_software_install_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_software_install_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `install_template_id` int(11) NOT NULL COMMENT '模板主键',
  `software_id` int(11) NOT NULL COMMENT '软件主键',
  `status` tinyint(11) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='装机模板与软件关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_software_relation`
--

DROP TABLE IF EXISTS `asset_software_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_software_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资产软件主键',
  `asset_id` int(11) NOT NULL COMMENT '资产主键',
  `software_id` bigint(20) NOT NULL COMMENT '软件主键',
  `gmt_create` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT '0' COMMENT '更新时间',
  `memo` varchar(300) DEFAULT NULL COMMENT '备注',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `modify_user` int(11) DEFAULT '0' COMMENT '修改人',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态：1未删除,0已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_asset_id_software_id` (`asset_id`,`software_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产软件关系信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_storage_medium`
--

DROP TABLE IF EXISTS `asset_storage_medium`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_storage_medium` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_id` int(11) NOT NULL DEFAULT '0' COMMENT '资产主键',
  `maximum_storage` varchar(16) DEFAULT NULL COMMENT '最大存储量',
  `disk_number` int(11) DEFAULT NULL COMMENT '单机磁盘数',
  `high_cache` varchar(30) DEFAULT NULL COMMENT '最高缓存',
  `inner_interface` varchar(32) DEFAULT '' COMMENT '内置接口',
  `raid_support` varchar(32) DEFAULT '' COMMENT 'RAID支持',
  `average_transfer_rate` varchar(30) DEFAULT NULL COMMENT '平均传输速率',
  `driver_number` int(11) DEFAULT NULL COMMENT '驱动器数量',
  `firmware` varchar(32) DEFAULT '' COMMENT '固件',
  `os_version` varchar(32) DEFAULT '' COMMENT 'OS版本',
  `gmt_create` bigint(20) DEFAULT '0' COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT '0' COMMENT '修改时间',
  `memo` varchar(300) DEFAULT NULL COMMENT '备准',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人',
  `modify_user` int(11) DEFAULT '0' COMMENT '修改人',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存数设备表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_sys_service_lib`
--

DROP TABLE IF EXISTS `asset_sys_service_lib`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_sys_service_lib` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_id` bigint(11) NOT NULL COMMENT '业务主键',
  `service` varchar(128) NOT NULL COMMENT '服务名',
  `display_name` varchar(128) DEFAULT NULL COMMENT '显示名',
  `service_classes` int(11) DEFAULT NULL COMMENT '服务类型',
  `startup_parameter` varchar(300) DEFAULT NULL COMMENT '启动参数',
  `describ` varchar(1024) DEFAULT NULL COMMENT '描述',
  `is_storage` tinyint(3) DEFAULT NULL COMMENT '是否入库：1已入库、2未入库',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `status` int(11) DEFAULT '1' COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id_index` (`id`) USING BTREE,
  UNIQUE KEY `business_id_index` (`business_id`) USING BTREE,
  KEY `index_gmt_create` (`gmt_create`) USING BTREE,
  KEY `name_index` (`service`,`display_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_user`
--

DROP TABLE IF EXISTS `asset_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(90) NOT NULL COMMENT '名称',
  `department_id` int(11) NOT NULL COMMENT '部门主键',
  `email` varchar(32) DEFAULT '' COMMENT '电子邮箱',
  `qq` varchar(30) DEFAULT NULL COMMENT 'QQ',
  `weixin` varchar(32) DEFAULT NULL COMMENT '微信',
  `mobile` varchar(30) DEFAULT NULL COMMENT '手机',
  `address` varchar(128) DEFAULT '' COMMENT '住址',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人',
  `modify_user` int(11) DEFAULT '0' COMMENT '修改人',
  `gmt_create` bigint(20) DEFAULT '0' COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT '0' COMMENT '更新时间',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态,1- 未删除,0-已删除',
  `memo` varchar(300) DEFAULT NULL COMMENT '备准',
  `detail_address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `position` varchar(64) DEFAULT NULL COMMENT '位置',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产用户信息';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-19 13:04:07
