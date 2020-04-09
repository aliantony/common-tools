package com.antiy.asset.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.templet.AssetEntity;
import com.antiy.asset.vo.query.AssetBaselinTemplateQuery;
import com.antiy.asset.vo.query.AssetMultipleQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.query.NoRegisterRequest;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

/**
 * <p> 资产主表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetService extends IBaseService<Asset> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    ActionResponse saveAsset(AssetOuterRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAsset(AssetRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetResponse> findListAsset(AssetQuery query, Map<String, WaitingTaskReponse> processMap) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetResponse> findPageAsset(AssetQuery query) throws Exception;

    /**
     * 资产认领/操作
     */

    public ActionResponse dealAssetOperation(AssetLockRequest assetLockRequest) throws Exception;

    /**
     * 判断资产是否重复
     *
     * @param uuid
     * @param ipMac
     * @return
     */
    boolean checkRepeatAsset(String uuid, List<String[]> ipMac);

    /**
     * 批量修改
     *
     * @param ids
     * @param assetStatus
     * @return
     */
    Integer changeStatus(String[] ids, Integer assetStatus) throws Exception;

    /**
     * 批量保存 <p> 批量保存 m
     *
     * @param assets
     * @return
     */
    Integer batchSave(List<Asset> assets) throws Exception;

    /**
     * 根据品类型号查询对应资产列表
     *
     * @param query
     * @return
     * @throws Exception
     */
    List<AssetResponse> findListAssetByCategoryModel(AssetQuery query) throws Exception;

    /**
     * 根据品类型号查询对应资产数量
     *
     * @param query
     * @return
     * @throws Exception
     */
    Integer findCountByCategoryModel(AssetQuery query) throws Exception;

    /**
     * 根据品类型号查询对应资产
     *
     * @return
     * @throws Exception
     */
    PageResult<AssetResponse> findPageAssetByCategoryModel(AssetQuery query) throws Exception;

    /**
     * 按厂商统计数量
     *
     * @return
     * @throws Exception
     */
    List<EnumCountResponse> countManufacturer() throws Exception;

    /**
     * 按状态统计数量
     *
     * @return
     * @throws Exception
     */
    List<EnumCountResponse> countStatus() throws Exception;

    /**
     * 按第二级品类统计数量
     *
     * @return
     * @throws Exception
     */
    List<EnumCountResponse> countCategory() throws Exception;

    /**
     * 通过ID列表查询资产列表
     *
     * @param ids
     * @return List<AssetResponse>
     */
    List<AssetResponse> queryAssetByIds(Integer[] ids);

    /**
     * 通过ID查询资产详情
     *
     * @param condition 主键封装对象
     * @return AssetOuterResponse
     */
    AssetOuterResponse getByAssetId(QueryCondition condition) throws Exception;

    /**
     * 资产变更
     *
     * @param assetOuterRequest 资产信息
     */
    ActionResponse changeAsset(AssetOuterRequest assetOuterRequest) throws Exception;

    /**
     * @param types 导出模板的类型
     */
    void exportTemplate(String[] types) throws Exception;

    /**
     * @param assetQuery 导出模板的条件
     */
    void exportData(AssetQuery assetQuery, HttpServletResponse servletResponse,
                    HttpServletRequest request) throws Exception;

    /**
     * 硬件导入PC
     *
     * @param file
     * @param areaId
     * @return
     */
    String importPc(MultipartFile file, AssetImportRequest areaId) throws Exception;

    /**
     * 硬件导入网络设备
     *
     * @param file
     * @param areaId
     * @return
     */
    String importNet(MultipartFile file, AssetImportRequest areaId) throws Exception;

    /**
     * 硬件导入安全设备
     *
     * @param file
     * @param areaId
     * @return
     */
    String importSecurity(MultipartFile file, AssetImportRequest areaId) throws Exception;

    /**
     * 硬件导存储设备
     *
     * @param file
     * @param areaId
     * @return
     */
    String importStory(MultipartFile file, AssetImportRequest areaId) throws Exception;

    /**
     * 硬件其他设备
     *
     * @param file
     * @param areaId
     * @return
     */
    String importOhters(MultipartFile file, AssetImportRequest areaId) throws Exception;

    /**
     * 通过区域Id查询当前区域是否存在资产
     *
     * @param areaIds
     * @return
     */
    Integer queryAssetCountByAreaIds(List<String> areaIds);

    /**
     * 通联设置的资产查询 与普通资产查询类似， 不同点在于品类型号显示二级品类， 只查已入网，网络设备和计算设备的资产,且会去掉通联表中已存在的资产
     */
    PageResult<AssetResponse> findUnconnectedAsset(AssetQuery query) throws Exception;

    /**
     * 通联设置的厂商下拉查询
     */
    Set<String> pulldownUnconnectedManufacturer(Integer isNet, String primaryKey) throws Exception;

    /**
     * 告警管理查询资产信息
     *
     * @param alarmAssetRequest
     * @return
     * @throws Exception
     */
    AlarmAssetDataResponse queryAlarmAssetList(AlarmAssetRequest alarmAssetRequest) throws Exception;

    /**
     * 获取资产id列表
     *
     * @return
     */
    IDResponse findAssetIds();

    /**
     * 工作台待登记数量
     *
     * @return
     */
    Integer queryWaitRegistCount();

    /**
     * 统计正常资产数量
     *
     * @return
     */
    Integer queryNormalCount();

    /**
     * 根据区域ID返回资产UUID
     *
     * @return
     * @throws Exception
     */
    List<String> queryUuidByAreaIds(AreaIdRequest request) throws Exception;

    /**
     * 根据区域ID返回资产ID
     *
     * @return
     * @throws Exception
     */
    List<String> queryIdByAreaIds(AreaIdRequest request) throws Exception;

    // void implementationFile(ProcessTemplateRequest baseRequest) throws Exception;

    /**
     * 查询mac重复
     *
     * @return
     */
    boolean checkRepeatMAC(String mac, Integer id) throws Exception;

    /**
     * 查询编号重复
     *
     * @return
     */
    boolean checkRepeatNumber(String number, Integer id) throws Exception;

    List<AssetAssemblyResponse> getAssemblyInfo(QueryCondition condition);

    /**
     * 不予登记
     *
     * @param assetStatusChangeRequest
     * @return
     */
    Integer assetNoRegister(AssetStatusChangeRequest assetStatusChangeRequest) throws Exception;

    Integer assetNoRegister(List<NoRegisterRequest> list) throws Exception;

    /**
     * 基准模板下拉
     *
     * @return
     */
    List<SelectResponse> queryBaselineTemplate();

    List<AssetEntity> assetsTemplate(ProcessTemplateRequest asset) throws Exception;

    List<IpMacPort> matchAssetByIpMac(AssetMatchRequest request) throws Exception;

    /**
     * 根据资产ID返回资产UUID
     *
     * @return
     * @throws Exception
     */
    List<String> queryUuidByAssetId(AssetIdRequest request) throws Exception;

    /**
     * 异常统计
     */
    Integer countUnusual(AssetQuery assetQuery) throws Exception;

    /**
     * 根据用户区域统计未知资产数量
     */
    Integer queryUnknownAssetCount(AssetUnknownRequest request) throws Exception;

    /**
     * 更新资产的基准模板
     *
     * @param query
     * @return
     */
    Integer updateAssetBaselineTemplate(AssetBaselinTemplateQuery query);

    List<String> findAvailableAssetStatus(Integer assetStatus);

    List<AssetMatchResponse> queryAssetInfo(AssetMatchRequest request);

    /**
     * 资产列表综合查询
     *
     * @param asset
     * @return
     */
    PageResult<AssetResponse> queryAssetPage(AssetMultipleQuery asset);

    List<AssetAreaAndIpResponse> queryIpByAreaId(AssetIpRequest request);

    /**
     * 资产列表查询-厂商下拉
     * @return
     */
    List<String> queryManufacturer();

    /**
     * 资产列表查询-名称下拉
     * @return
     */
    List<String> queryName();

    /**
     * 资产列表查询-版本下拉
     * @return
     */
    List<String> queryVersion();

    /**
     * 资产列表查询-资产组下拉
     * @return
     */
    List<SelectResponse> queryAssetGroup();

    /**
     * 资产列表查询-使用者下拉
     * @return
     */
    List<SelectResponse> queryUser();

    /**
     * 资产列表查询-所属组织下拉
     * @return
     */
    List<SelectResponse> queryDepartment();

    /**
     * 资产列表查询-基准模板下拉
     * @return
     */
    List<SelectResponse> queryTemplate();
}
