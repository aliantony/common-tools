package com.antiy.asset.dao;

import org.apache.ibatis.annotations.Select;

/**
 * @author zhouye
 * 资产-检查-结果验证流程关联资产是否已上传结果附件
 */
public interface AssetBaselineFileDao {
	/**
	 * 检查流程关联资产是否已上传附件
	 * @param assetId 资产id
	 * @return 关联附件数量
	 */
	@Select("select count(1) from baseline_fix_result RIGHT JOIN baseline_waiting_config on waiting_config_id = baseline_waiting_config.id\n" +
			" where is_pass is null and parse_status in(1,2) and asset_id = #{assetId}")
	Integer queryBaselineCheckFileIsExist(String assetId);

	/**
	 * 验证流程关联资产是否已上传附件
	 * @param assetId 资产id
	 * @return 关联附件数量
	 */
	@Select("select count(1) from baseline_check_result RIGHT JOIN baseline_waiting_config on waiting_config_id = baseline_waiting_config.id\n" +
			" where is_pass is null and parse_status in(1,2) and asset_id = #{assetId}")
	Integer queryBaselineValidateFileIsExist(String assetId);
}
