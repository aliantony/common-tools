CREATE DATABASE
IF NOT EXISTS `` DEFAULT CHARACTER
SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE asset_stg;

-- asset
DROP USER
IF EXISTS asset_code_stg@'%';

DROP USER
IF EXISTS asset_rep_stg@'%';

DROP USER
IF EXISTS asset_data_stg@'%';

-- 数据用户 ddl dml
CREATE USER asset_data_stg IDENTIFIED BY '2W@drpOrnDD#nbd';

-- 开发用户  dml
CREATE USER asset_code_stg IDENTIFIED BY '2q2xnW2dDwx#Kp';

-- 只读用户
CREATE USER asset_rep_stg IDENTIFIED BY '2q2xnOp34COix#Kp';

-- 授权
GRANT SELECT
	,
	INSERT,
	UPDATE,
	DELETE ON asset_stg.* TO asset_code_stg@'%';

GRANT SELECT
	,
	CREATE TEMPORARY TABLES ON asset_stg.* TO asset_rep_stg@'%';

GRANT SELECT
	,
	INSERT,
	UPDATE,
	DELETE,
	CREATE,
	ALTER,
	DROP,
	INDEX,
	CREATE routine,
	ALTER routine,
	EXECUTE,
	CREATE TEMPORARY TABLES ON asset_stg.* TO asset_data_stg@'%';

FLUSH PRIVILEGES;

source ddl/asset_ddl.sql;
source dml/asset_dml.sql;