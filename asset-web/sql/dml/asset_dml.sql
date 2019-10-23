---
---default common operation system
---
update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'os_x_server' limit 1) where `product_name` = 'os_x_server';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'mac_os_x' limit 1) where `product_name` = 'mac_os_x';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'hp-ux' limit 1) where `product_name` = 'hp-ux';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'aix' limit 1) where `product_name` = 'aix';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'enterprise_linux' limit 1) where `product_name` = 'enterprise_linux';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'linux_2' limit 1) where `product_name` = 'linux_2';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'windows-nt' limit 1) where `product_name` = 'windows-nt';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'windows_7' limit 1) where `product_name` = 'windows_7';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'windows_8' limit 1) where `product_name` = 'windows_8';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'windows_8.1' limit 1) where `product_name` = 'windows_8.1';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'windows_10' limit 1) where `product_name` = 'windows_10';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'windows_server_2008' limit 1) where `product_name` = 'windows_server_2008';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'windows_server_2012' limit 1) where `product_name` = 'windows_server_2012';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'windows_server_2016' limit 1) where `product_name` = 'windows_server_2016';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'windows_server_2003' limit 1) where `product_name` = 'windows_server_2003';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'linux_enterprise_server' limit 1) where `product_name` = 'linux_enterprise_server';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'suse_linux_enterprise_server' limit 1) where `product_name` = 'suse_linux_enterprise_server';

update asset_cpe_filter set business_id = (SELECT business_id FROM `csom`.`asset_hard_soft_lib` WHERE `product_name` = 'ubuntu_linux' limit 1) where `product_name` = 'ubuntu_linux';