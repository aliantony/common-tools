-- ------------------------------ ------------------------------ ----------------------------
                                      -- asset
-- ------------------------------ ------------------------------ ----------------------------

-- ----------------------------
-- Table structure for asset
-- ----------------------------
DROP TABLE IF EXISTS `asset`;
CREATE TABLE `asset`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_group` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资产组（逗号分隔）',
  `business_id` bigint(20) NOT NULL COMMENT '业务主键(四库)',
  `number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资产编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资产名称',
  `ethernet_port` tinyint(3) NULL DEFAULT NULL COMMENT '网口',
  `serial_port` tinyint(3) NULL DEFAULT NULL COMMENT '串口',
  `install_type` tinyint(3) NULL DEFAULT 1 COMMENT '安装方式：1-人工，2-自动',
  `serial` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '序列号',
  `area_id` int(11) NULL DEFAULT NULL COMMENT '行政区划主键',
  `category_model` int(11) NULL DEFAULT NULL COMMENT '品类型号',
  `manufacturer` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '厂商',
  `asset_status` tinyint(4) NULL DEFAULT 0 COMMENT '资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6待检查，7-已入网，8-待退役，9-已退役',
  `admittance_status` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 1 COMMENT '准入状态：1-待设置，2-已允许，3-已禁止',
  `operation_system` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作系统',
  `system_bit` tinyint(4) NULL DEFAULT 64 COMMENT '系统位数',
  `responsible_user_id` int(11) NULL DEFAULT NULL COMMENT '责任人主键',
  `location` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '物理位置',
  `latitude` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '纬度',
  `longitude` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '经度',
  `house_location` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机房位置',
  `firmware_version` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '固件版本',
  `uuid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终端UUID',
  `contact_tel` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '联系电话',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '邮箱',
  `asset_source` tinyint(4) NULL DEFAULT 0 COMMENT '上报来源:1-自动上报，2-人工上报',
  `importance_degree` tinyint(4) NULL DEFAULT NULL COMMENT '资产重要程度：1-核心,2-重要,3一般',
  `describle` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `parent_id` int(11) NULL DEFAULT 0 COMMENT '父类资源Id',
  `tags` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '所属标签ID和名称列表JSON串',
  `first_enter_nett` bigint(20) NULL DEFAULT NULL COMMENT '首次入网时间',
  `first_discover_time` bigint(20) NULL DEFAULT NULL COMMENT '首次发现时间',
  `is_innet` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否入网：0-未入网,1-表示入网',
  `service_life` bigint(20) NULL DEFAULT 0 COMMENT '使用到期时间',
  `buy_date` bigint(20) NULL DEFAULT 0 COMMENT '购买日期',
  `version` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本',
  `warranty` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '保修期',
  `install_template_id` int(11) NULL DEFAULT NULL COMMENT '装机模板主键',
  `baseline_template_id` int(11) NULL DEFAULT NULL COMMENT '基准模板主键',
  `baseline_template_correlation_gmt` bigint(20) NULL DEFAULT 0 COMMENT '资产与基准模板关联时间',
  `install_template_correlation_gmt` bigint(20) NULL DEFAULT 0 COMMENT '资产与装机模板关联时间',
  `memo` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_create` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  `gmt_modified` bigint(20) NULL DEFAULT 0 COMMENT '更新时间',
  `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '创建人',
  `modify_user` int(11) NULL DEFAULT 0 COMMENT '修改人',
  `status` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 1 COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `number`(`number`) USING BTREE
) COMMENT = '资产主表';


-- ----------------------------
-- Table structure for asset_assembly
-- ----------------------------
DROP TABLE IF EXISTS `asset_assembly`;
CREATE TABLE `asset_assembly`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_id` int(11) NULL DEFAULT NULL COMMENT '资产主键',
  `amount` tinyint(3) NULL DEFAULT NULL COMMENT '组件数量',
  `business_id` int(11) NULL DEFAULT NULL COMMENT '组件主键',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT = '资产组件关系表';


-- ----------------------------
-- Table structure for asset_group
-- ----------------------------
DROP TABLE IF EXISTS `asset_group`;
CREATE TABLE `asset_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `purpose` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用途',
  `important_degree` tinyint(4) NULL DEFAULT NULL COMMENT '重要程度：0-不重要,1- 一般,3-重要',
  `name` varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `gmt_create` bigint(20) NULL DEFAULT 0 COMMENT '创建时间',
  `gmt_modified` bigint(20) NULL DEFAULT 0 COMMENT '修改时间',
  `memo` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_user` int(11) NULL DEFAULT 0 COMMENT '创建人',
  `modify_user` int(11) NULL DEFAULT 0 COMMENT '修改人',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT = '资产组表';


-- ----------------------------
-- Table structure for asset_group_relation
-- ----------------------------
DROP TABLE IF EXISTS `asset_group_relation`;
CREATE TABLE `asset_group_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资产与资产组主键',
  `asset_group_id` int(11) NULL DEFAULT NULL COMMENT '资产组主键',
  `asset_id` int(11) NULL DEFAULT NULL COMMENT '资产主键',
  `gmt_create` bigint(20) NULL DEFAULT 0 COMMENT '创建时间',
  `gmt_modified` bigint(20) NULL DEFAULT 0 COMMENT '修改时间',
  `memo` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` int(11) NULL DEFAULT 0 COMMENT '创建人',
  `modify_user` int(11) NULL DEFAULT 0 COMMENT '修改人',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_asset_group_id_asset_id`(`asset_group_id`, `asset_id`) USING BTREE
) COMMENT = '资产与资产组关系表';


-- ----------------------------
-- Table structure for asset_install_template
-- ----------------------------
DROP TABLE IF EXISTS `asset_install_template`;
CREATE TABLE `asset_install_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板名称',
  `number_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板编号',
  `category_model` int(11) NULL DEFAULT NULL COMMENT '品类型号',
  `check_status` tinyint(3) NULL DEFAULT NULL COMMENT '审核状态',
  `operation_system` bigint(20) NULL DEFAULT NULL COMMENT '适用操作系统',
  `operation_system_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统名称',
  `enable` tinyint(255) NULL DEFAULT NULL COMMENT '是否启用：0-禁用，1-启用',
  `description` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT = '装机模板';


-- ----------------------------
-- Table structure for asset_install_template_check
-- ----------------------------
DROP TABLE IF EXISTS `asset_install_template_check`;
CREATE TABLE `asset_install_template_check`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `install_template_id` int(11) NULL DEFAULT NULL COMMENT '装机模板主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户主键',
  `advice` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `result` tinyint(3) NULL DEFAULT NULL COMMENT '是否通过：0-拒绝，1-通过',
  `gmt_create` bigint(20) DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT = '装机模板审核表';


-- ----------------------------
-- Table structure for asset_ip_mac
-- ----------------------------
DROP TABLE IF EXISTS `asset_ip_mac`;
CREATE TABLE `asset_ip_mac`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_id` int(11) NULL DEFAULT NULL COMMENT '资产主表',
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP',
  `mac` varchar(17) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MAC',
  `net` int(11) DEFAULT NULL COMMENT '网口',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT = '资产-IP-MAC表';


-- ----------------------------
-- Table structure for asset_link_relation
-- ----------------------------
DROP TABLE IF EXISTS `asset_link_relation`;
CREATE TABLE `asset_link_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_id` int(11) NOT NULL COMMENT '资产主键',
  `asset_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资产IP',
  `asset_port` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资产端口',
  `parent_asset_id` int(4) NOT NULL DEFAULT 0 COMMENT '父级设备主键',
  `parent_asset_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '父级设备IP',
  `parent_asset_port` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级设备端口',
  `gmt_create` bigint(20) NULL DEFAULT 0 COMMENT '创建时间',
  `gmt_modified` bigint(20) NULL DEFAULT 0 COMMENT '修改时间',
  `memo` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` int(11) NULL DEFAULT 0 COMMENT '创建人',
  `modify_user` int(11) NULL DEFAULT 0 COMMENT '修改人',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT = '通联关系表';


-- ----------------------------
-- Table structure for asset_network_equipment
-- ----------------------------
DROP TABLE IF EXISTS `asset_network_equipment`;
CREATE TABLE `asset_network_equipment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_id` int(11) NOT NULL COMMENT '资产主键',
  `interface_size` tinyint(4) NULL DEFAULT NULL COMMENT '接口数目',
  `is_wireless` bit(1) NULL DEFAULT NULL COMMENT '是否无线:0-否,1-是',
  `port_size` int(11) NULL DEFAULT NULL COMMENT '端口数目',
  `cpu_version` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'CPU版本',
  `cpu_size` int(11) NULL DEFAULT NULL COMMENT 'CPU大小',
  `ios` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IOS',
  `firmware_version` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '固件版本',
  `inner_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '内网IP',
  `outer_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '外网IP',
  `mac_address` varchar(17) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'MAC地址',
  `subnet_mask` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '子网掩码',
  `expect_bandwidth` int(11) NULL DEFAULT NULL COMMENT '预计带宽(M)',
  `register` int(11) NULL DEFAULT NULL COMMENT '配置寄存器(GB)',
  `dram_size` float NULL DEFAULT NULL COMMENT 'DRAM大小',
  `flash_size` float NULL DEFAULT NULL COMMENT 'FLASH大小',
  `ncrm_size` float NULL DEFAULT NULL COMMENT 'NCRM大小',
  `create_user` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `modify_user` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `gmt_create` bigint(20) NULL DEFAULT 0 COMMENT '创建时间',
  `memo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `gmt_modified` bigint(20) NULL DEFAULT 0 COMMENT '更新时间',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_NETWORK_ASSET_ID`(`asset_id`) USING BTREE
) COMMENT = '网络设备详情表';


-- ----------------------------
-- Table structure for asset_operation_record
-- ----------------------------
DROP TABLE IF EXISTS `asset_operation_record`;
CREATE TABLE `asset_operation_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `target_object_id` int(11) NOT NULL COMMENT '被操作的对象ID',
  `target_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '被操作对象类别',
  `area_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域主键',
  `origin_status` tinyint(1) NOT NULL COMMENT '原始状态',
  `target_status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `content` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '本次操作(类型)内容',
  `operate_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '操作人ID',
  `process_result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理结果：0拒绝1同意',
  `operate_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人名字',
  `scheme_id` int(11) NULL DEFAULT NULL COMMENT '方案表ID',
  `note` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '当前操作输入的备注信息',
  `file_info` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '附件信息JSON',
  `gmt_create` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  `gmt_modified` bigint(20) NULL DEFAULT 0 COMMENT '更新时间',
  `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '创建人',
  `modify_user` int(11) NULL DEFAULT 0 COMMENT '修改人',
  `memo` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 1 COMMENT '状态,1未删除,0已删除',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT = '资产操作记录表';


-- ----------------------------
-- Table structure for asset_patch_install_template
-- ----------------------------
DROP TABLE IF EXISTS `asset_patch_install_template`;
CREATE TABLE `asset_patch_install_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `install_template_id` int(11) NULL DEFAULT NULL COMMENT '模板主键',
  `patch_id` int(11) NULL DEFAULT NULL COMMENT '补丁主键',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT = '装机模板与补丁关系表';


-- ----------------------------
-- Table structure for asset_port_protocol
-- ----------------------------
DROP TABLE IF EXISTS `asset_port_protocol`;
CREATE TABLE `asset_port_protocol`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_soft_id` int(11) NOT NULL COMMENT '资产软件关系表主键',
  `port` int(11) NULL DEFAULT NULL COMMENT '端口',
  `protocol` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '协议',
  `description` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '描述',
  `gmt_create` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  `gmt_modified` bigint(20) NULL DEFAULT 0 COMMENT '更新时间',
  `memo` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '创建人',
  `modify_user` int(11) NULL DEFAULT 0 COMMENT '修改人',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：1-未删除，0-已删除',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT = '端口协议';


-- ----------------------------
-- Table structure for asset_safety_equipment
-- ----------------------------
DROP TABLE IF EXISTS `asset_safety_equipment`;
CREATE TABLE `asset_safety_equipment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_id` int(11) NOT NULL COMMENT '资产主键',
  `avlx_version` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AVLX检测引擎版本号',
  `command_control_channel` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '命令与控制通道检测引擎版本号',
  `is_manage` bit(1) NULL DEFAULT NULL COMMENT '是否纳入管理',
  `new_version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最新版本',
  `ip` varchar(17) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MAC地址',
  `url` varchar(355) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'URL地址',
  `memo` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` int(11) NULL DEFAULT 0 COMMENT '创建人',
  `modify_user` int(11) NULL DEFAULT 0 COMMENT '修改人',
  `gmt_create` bigint(20) NULL DEFAULT 0 COMMENT '创建时间',
  `gmt_modified` bigint(20) NULL DEFAULT 0 COMMENT '更新时间',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：1-未删除,0-已删除',
  `feature_library` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '特征库版本',
  `strategy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '策略',
  `network_state` tinyint(4) NULL DEFAULT NULL COMMENT '网络状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `INX_ASSET_ID`(`asset_id`) USING BTREE
) COMMENT = '安全设备表';


-- ----------------------------
-- Table structure for asset_software
-- ----------------------------
DROP TABLE IF EXISTS `asset_software`;
CREATE TABLE `asset_software`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `size` bigint(20) NULL DEFAULT NULL COMMENT '软件大小',
  `operation_system` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作系统',
  `category_model` int(11) NULL DEFAULT NULL COMMENT '软件品类',
  `name` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '软件名称',
  `upload_software_name` varchar(240) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '上传的软件名称',
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装包路径',
  `version` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '版本',
  `manufacturer` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '厂商',
  `description` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `asset_group` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资产组',
  `software_label` int(11) NULL DEFAULT NULL COMMENT '软件标签',
  `software_status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '软件状态：1-待登记，2-待分析，3-可安装，4-已退役，5-不予登记',
  `authorization` tinyint(1) NULL DEFAULT NULL COMMENT '授权类型：0-免费软件，1-商业软件',
  `report_source` tinyint(1) NULL DEFAULT NULL COMMENT '上报来源:1-自动上报，2-人工上报',
  `protocol` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '协议',
  `service_life` bigint(20) NULL DEFAULT NULL COMMENT '到期时间',
  `buy_date` bigint(20) NULL DEFAULT NULL COMMENT '购买日期',
  `language` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '语言',
  `release_time` bigint(20) NULL DEFAULT NULL COMMENT '发布时间',
  `serial` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '序列号',
  `md5_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MD5/SHA',
  `publisher` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '发布者',
  `gmt_create` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  `manual_doc_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '软件安装说明书URL',
  `manual_doc_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '软件安装说明书名字',
  `check_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '校验方式',
  `gmt_modified` bigint(20) NULL DEFAULT 0 COMMENT '更新时间',
  `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '创建人',
  `modify_user` int(11) NULL DEFAULT 0 COMMENT '修改人',
  `memo` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备准',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT = '软件信息表';


-- ----------------------------
-- Table structure for asset_software_install_template
-- ----------------------------
DROP TABLE IF EXISTS `asset_software_install_template`;
CREATE TABLE `asset_software_install_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `install_template_id` int(11) NULL DEFAULT NULL COMMENT '模板主键',
  `software_id` int(11) NULL DEFAULT NULL COMMENT '软件主键',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT = '装机模板与软件关系表';


-- Table structure for asset_software_relation
-- ----------------------------
DROP TABLE IF EXISTS `asset_software_relation`;
CREATE TABLE `asset_software_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资产软件主键',
  `asset_id` int(11) NOT NULL COMMENT '资产主键',
  `software_id` bigint(20) NOT NULL COMMENT '软件主键',
  `software_status` tinyint(4) NULL DEFAULT 1 COMMENT '软件资产状态：1-待登记，2-待分析，3-可安装，4-已退役，5-不予登记',
  `port` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '端口',
  `configure_status` tinyint(1) NULL DEFAULT NULL COMMENT '配置状态：1-未配置，2-配置中，3-已配置',
  `install_type` tinyint(1) NULL DEFAULT NULL COMMENT '安装方式：1-人工，2-自动',
  `install_status` tinyint(4) NULL DEFAULT NULL COMMENT '安装状态：1-未配置，2-配置中，3-未安装，4-失败，5-成功，6-安装中',
  `install_time` bigint(20) NULL DEFAULT NULL COMMENT '安装时间',
  `protocol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '协议',
  `gmt_create` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  `gmt_modified` bigint(20) NULL DEFAULT 0 COMMENT '更新时间',
  `memo` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '创建人',
  `modify_user` int(11) NULL DEFAULT 0 COMMENT '修改人',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态：1未删除,0已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_asset_id_software_id`(`asset_id`, `software_id`) USING BTREE
) COMMENT = '资产软件关系信息';



-- ----------------------------
-- Table structure for asset_storage_medium
-- ----------------------------
DROP TABLE IF EXISTS `asset_storage_medium`;
CREATE TABLE `asset_storage_medium`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_id` int(11) NOT NULL DEFAULT 0 COMMENT '资产主键',
  `maximum_storage` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最大存储量',
  `disk_number` int(11) NULL DEFAULT NULL COMMENT '单机磁盘数',
  `high_cache` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最高缓存',
  `inner_interface` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '内置接口',
  `raid_support` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'RAID支持',
  `average_transfer_rate` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平均传输速率',
  `driver_number` int(11) NULL DEFAULT NULL COMMENT '驱动器数量',
  `firmware` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '固件',
  `os_version` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'OS版本',
  `gmt_create` bigint(20) NULL DEFAULT 0 COMMENT '创建时间',
  `gmt_modified` bigint(20) NULL DEFAULT 0 COMMENT '修改时间',
  `memo` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备准',
  `create_user` int(11) NULL DEFAULT 0 COMMENT '创建人',
  `modify_user` int(11) NULL DEFAULT 0 COMMENT '修改人',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：1-未删除,0-已删除',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT = '存数设备表';



-- ----------------------------
-- Table structure for asset_topology
-- ----------------------------
DROP TABLE IF EXISTS `asset_topology`;
CREATE TABLE `asset_topology`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `topology_type` tinyint(3) NULL DEFAULT NULL COMMENT '拓扑类型',
  `relation` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '拓扑关系',
  `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '创建人',
  `gmt_create` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT = '资产拓扑表';


-- ----------------------------
-- Table structure for asset_user
-- ----------------------------
DROP TABLE IF EXISTS `asset_user`;
CREATE TABLE `asset_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `department_id` int(11) NULL DEFAULT NULL COMMENT '部门主键',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '电子邮箱',
  `qq` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'QQ',
  `weixin` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信',
  `mobile` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '住址',
  `create_user` int(11) NULL DEFAULT 0 COMMENT '创建人',
  `modify_user` int(11) NULL DEFAULT 0 COMMENT '修改人',
  `gmt_create` bigint(20) NULL DEFAULT 0 COMMENT '创建时间',
  `gmt_modified` bigint(20) NULL DEFAULT 0 COMMENT '更新时间',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态,1 未删除,0已删除',
  `memo` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备准',
  `detail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `position` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '位置',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT = '资产用户信息';



-- ------------------------------ ------------------------------ ----------------------------
                                      -- kbms
-- ------------------------------ ------------------------------ ----------------------------

-- ----------------------------
-- Table structure for asset_assembly_lib
-- ----------------------------
DROP TABLE IF EXISTS `asset_assembly_lib`;
CREATE TABLE `asset_assembly_lib`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_id` bigint(11) NULL DEFAULT NULL COMMENT '业务主键',
  `type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '保留字段',
  `supplier` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供应商',
  `product_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品名',
  `version` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本号',
  `sys_version` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统版本',
  `language` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '语言',
  `other` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '其他',
  `memo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_storage` tinyint(1) NULL DEFAULT NULL COMMENT '是否入库：1已入库、2未入库',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_business_id`(`business_id`) USING BTREE,
  INDEX `index_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `index_id_status`(`id`, `status`) USING BTREE,
  INDEX `name_index`(`supplier`, `product_name`, `version`) USING BTREE
) COMMENT = '组件表';

-- ----------------------------
-- Table structure for asset_assembly_soft_relation
-- ----------------------------
DROP TABLE IF EXISTS `asset_assembly_soft_relation`;
CREATE TABLE `asset_assembly_soft_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `soft_id` bigint(20) NOT NULL COMMENT '软件业务id',
  `assembly_id` bigint(20) NOT NULL COMMENT '组件业务id',
  `memo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_`(`soft_id`) USING BTREE,
  INDEX `index_soft_id`(`soft_id`) USING BTREE,
  INDEX `index_assembly_id`(`assembly_id`) USING BTREE
) COMMENT = '组件与软件关系表';

-- ----------------------------
-- Table structure for asset_cpe_filter
-- ----------------------------
DROP TABLE IF EXISTS `asset_cpe_filter`;
CREATE TABLE `asset_cpe_filter`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_id` bigint(11) NULL DEFAULT NULL COMMENT '业务主键',
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `serial_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系列名称',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
  `gmt_modified` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_business_id`(`business_id`) USING BTREE,
  UNIQUE INDEX `index_product_name`(`product_name`) USING BTREE COMMENT '操作系统唯一所索引'
) COMMENT = '过滤显示表（筛选指定的数据给用户）';


-- ----------------------------
-- Table structure for asset_hard_assembly_relation
-- ----------------------------
DROP TABLE IF EXISTS `asset_hard_assembly_relation`;
CREATE TABLE `asset_hard_assembly_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `hard_id` bigint(20) NOT NULL COMMENT '硬件id',
  `assembly_id` bigint(20) NOT NULL COMMENT '组件id',
  `memo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_hard_id`(`hard_id`) USING BTREE,
  INDEX `index_assembly_id`(`assembly_id`) USING BTREE
) COMMENT = '硬件与组件关系表';


-- ----------------------------
-- Table structure for asset_hard_soft_lib
-- ----------------------------
DROP TABLE IF EXISTS `asset_hard_soft_lib`;
CREATE TABLE `asset_hard_soft_lib`  (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_id` bigint(20) NULL DEFAULT NULL COMMENT '业务主键',
  `number` int(11) NULL DEFAULT NULL COMMENT '编号',
  `type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型：a-应用软件 h-硬件 o-操作系统',
  `supplier` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供应商',
  `product_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品名',
  `version` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本号',
  `upgrade_msg` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新信息',
  `sys_version` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统版本',
  `language` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '语言',
  `soft_version` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '软件版本',
  `soft_platform` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '软件平台',
  `hard_platform` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '硬件平台',
  `other` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '其他',
  `data_source` tinyint(3) NULL DEFAULT NULL COMMENT '数据来源：1-CPE，2-MANUAL',
  `is_storage` tinyint(1) NULL DEFAULT NULL COMMENT '是否入库：1-已入库、2-未入库',
  `cpe_uri` varchar(640) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'cpe路径',
  `memo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_business_id`(`business_id`) USING BTREE COMMENT '业务主键唯一索引',
  INDEX `index_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `id_type_status`(`id`, `type`, `status`, `product_name`, `supplier`) USING BTREE,
  INDEX `index_relation`(`id`, `business_id`, `type`, `is_storage`, `supplier`, `product_name`, `status`) USING BTREE
) COMMENT = 'CPE表'; 

-- ----------------------------
-- Table structure for asset_protocol
-- ----------------------------
DROP TABLE IF EXISTS `asset_protocol`;
CREATE TABLE `asset_protocol`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_id` bigint(11) NOT NULL COMMENT '业务主键',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '协议',
  `memo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_storage` tinyint(1) NULL DEFAULT 2 COMMENT '是否入库：1-已入库，2-未入库',
  `gmt_modified` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `link_vuls` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联的遏制漏洞编号',
  `gmt_create` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_business_id`(`business_id`) USING BTREE,
  INDEX `index_id`(`id`) USING BTREE,
  INDEX `index_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `index_is_storage`(`is_storage`) USING BTREE,
  INDEX `protocol_name`(`name`) USING BTREE
) COMMENT = '协议表';

-- ----------------------------
-- Table structure for asset_service_depend
-- ----------------------------
DROP TABLE IF EXISTS `asset_service_depend`;
CREATE TABLE `asset_service_depend`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `service_id` bigint(11) NULL DEFAULT NULL COMMENT '服务id',
  `service_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务名',
  `depend_service` bigint(11) NULL DEFAULT NULL COMMENT '依赖服务id',
  `depend_service_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '依赖服务名',
  `memo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `service_id`(`service_id`) USING BTREE,
  INDEX `depend_service`(`depend_service`) USING BTREE
) COMMENT = '服务依赖的服务表';

-- ----------------------------
-- Table structure for asset_service_port_relation
-- ----------------------------
DROP TABLE IF EXISTS `asset_service_port_relation`;
CREATE TABLE `asset_service_port_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `service_id` bigint(11) NOT NULL COMMENT '服务id',
  `port` int(11) NOT NULL COMMENT '端口',
  `memo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `service_id`(`service_id`) USING BTREE
) COMMENT = '服务与端口表';


-- ----------------------------
-- Table structure for asset_service_protocol_relation
-- ----------------------------
DROP TABLE IF EXISTS `asset_service_protocol_relation`;
CREATE TABLE `asset_service_protocol_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `service_id` bigint(11) NOT NULL COMMENT '服务id',
  `protocol_id` bigint(11) NOT NULL COMMENT '协议id',
  `memo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `service_id`(`service_id`) USING BTREE,
  INDEX `protocol_id`(`protocol_id`) USING BTREE
) COMMENT = '服务与协议表';


-- ----------------------------
-- Table structure for asset_soft_port_relation
-- ----------------------------
DROP TABLE IF EXISTS `asset_soft_port_relation`;
CREATE TABLE `asset_soft_port_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `soft_id` bigint(11) NOT NULL COMMENT '软件id',
  `port` int(11) NOT NULL COMMENT '端口',
  `memo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_soft_id`(`soft_id`) USING BTREE
) COMMENT = '软件与端口表';


-- ----------------------------
-- Table structure for asset_soft_protocol_relation
-- ----------------------------
DROP TABLE IF EXISTS `asset_soft_protocol_relation`;
CREATE TABLE `asset_soft_protocol_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `soft_id` bigint(20) NOT NULL COMMENT '软件id',
  `protocol_id` bigint(20) NOT NULL COMMENT '协议id',
  `memo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_soft_id`(`soft_id`) USING BTREE,
  INDEX `index_protocol_id`(`protocol_id`) USING BTREE
) COMMENT = '软件与协议表';

-- ----------------------------
-- Table structure for asset_soft_service_lib
-- ----------------------------
DROP TABLE IF EXISTS `asset_soft_service_lib`;
CREATE TABLE `asset_soft_service_lib`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `soft_id` bigint(20) NULL DEFAULT NULL COMMENT '软件id',
  `service_id` bigint(20) NOT NULL COMMENT '服务id',
  `memo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_soft_id`(`soft_id`) USING BTREE,
  INDEX `index_service_id`(`service_id`) USING BTREE
) COMMENT = '软件提供的服务';

-- ----------------------------
-- Table structure for asset_soft_service_relation
-- ----------------------------
DROP TABLE IF EXISTS `asset_soft_service_relation`;
CREATE TABLE `asset_soft_service_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `soft_id` bigint(20) NOT NULL COMMENT '软件id',
  `service_id` bigint(20) NOT NULL COMMENT '服务id',
  `memo` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_modified` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_soft_id`(`soft_id`) USING BTREE,
  INDEX `index_service_id`(`service_id`) USING BTREE
) COMMENT = '软件依赖的服务';

-- ----------------------------
-- Table structure for asset_sys_service_lib
-- ----------------------------
DROP TABLE IF EXISTS `asset_sys_service_lib`;
CREATE TABLE `asset_sys_service_lib`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_id` bigint(11) NULL DEFAULT NULL COMMENT '业务主键',
  `service` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务名',
  `display_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示名',
  `service_classes` int(11) NULL DEFAULT NULL COMMENT '服务类型',
  `startup_parameter` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '启动参数',
  `describ` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `is_storage` tinyint(1) NULL DEFAULT NULL COMMENT '是否入库：1已入库、2未入库',
  `gmt_modified` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `gmt_create` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `modified_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：1-正常，0-删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_index`(`id`) USING BTREE,
  UNIQUE INDEX `business_id_index`(`business_id`) USING BTREE,
  INDEX `index_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `name_index`(`service`, `display_name`) USING BTREE
) COMMENT = '服务表';