package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetKeyManage;
import com.antiy.asset.vo.query.AssetKeyManageQuery;
import com.antiy.asset.vo.query.KeyPullQuery;
import com.antiy.asset.vo.request.AssetKeyManageRequest;
import com.antiy.asset.vo.response.KeyPullDownResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author chenchaowu
 */
public interface AssetKeyManageDao {

	/**
	 * 根据Id获取key详情
	 * @param keyId
	 * @return
	 */
	AssetKeyManage queryId(Integer keyId);

	/**
	 * key分页查询--总数
	 * @param keyManageQuery
	 * @return
	 */
	Integer queryCount(AssetKeyManageQuery keyManageQuery);

	/**
	 * key分页查询--列表
	 * @param keyManageQuery
	 * @return
	 */
	List<AssetKeyManage> queryList(AssetKeyManageQuery keyManageQuery);

	/**
	 * key登记
	 * @param keyManage
	 * @return
	 */
	Integer keyRegister(AssetKeyManage keyManage);

	/**
	 * key领用
	 * @param keyManage
	 * @return
	 */
	Integer keyRecipients(AssetKeyManage keyManage);

	/**
	 * key归还
	 * @param keyManage
	 * @return
	 */
	Integer keyReturn(AssetKeyManage keyManage);

	/**
	 * key冻结/解冻
	 * @param keyManage
	 * @return
	 */
	Integer keyFreeze(AssetKeyManage keyManage);

	/**
	 * key删除
	 * @param keyId
	 * @return
	 */
	Integer keyRemove(Integer keyId);

	/**
	 * key编号数量校验
	 * @param request
	 * @return
	 */
	Integer keyNumCountVerify(AssetKeyManageRequest request);

	/**
	 * key领用用户数量校验
	 * @param request
	 * @return
	 */
	Integer keyNameCountVerify(AssetKeyManageRequest request);

    void insertBatch(List<AssetKeyManage> list);

    void updateBatch(List<AssetKeyManage> list);

	/**
	 * key--设备下拉
	 * @param query
	 * @return
	 */
	Integer assetMapCount(KeyPullQuery query);

    List<KeyPullDownResponse> assetMapList(KeyPullQuery query);

	/**
	 * key -- 用户下拉
	 * @param query
	 * @return
	 */
	Integer userMapCount(KeyPullQuery query);

	List<KeyPullDownResponse> userMapList(KeyPullQuery query);

    List<Integer> getAssetUserIds(@Param("type") Integer type);
}
