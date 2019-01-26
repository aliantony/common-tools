package com.antiy.asset.service;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.vo.query.AssetDetialCondition;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetImportRequest;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.response.AssetCountResponse;
import com.antiy.asset.vo.response.AssetOuterResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    List<AssetResponse> findListAsset(AssetQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetResponse> findPageAsset(AssetQuery query) throws Exception;

    /**
     * 保存上报数据
     *
     * @param assetOuterRequestList
     * @throws Exception
     */
    void saveReportAsset(List<AssetOuterRequest> assetOuterRequestList) throws Exception;

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
     * 通过资产ID修改资产状态
     *
     * @param id
     * @param targetStatus
     * @return
     */
    Integer changeStatusById(String id, Integer targetStatus) throws Exception;


    /**
     * 批量保存
     *
     * 批量保存 m
     * @param assets
     * @return
     */
    Integer batchSave(List<Asset> assets) throws Exception;

    /**
     * 查询下拉的厂商信息
     *
     * @return
     */
    List<String> pulldownManufacturer() throws Exception;

    /**
     * 根据品类型号查询对应资产列表
     * @param query
     * @return
     * @throws Exception
     */
    List<AssetResponse> findListAssetByCategoryModel(AssetQuery query) throws Exception;

    /**
     * 根据品类型号查询对应资产数量
     * @param query
     * @return
     * @throws Exception
     */
    Integer findCountByCategoryModel(AssetQuery query) throws Exception;

    /**
     * 根据品类型号查询对应资产
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
    AssetCountResponse countManufacturer() throws Exception;

    /**
     * 按状态统计数量
     *
     * @return
     * @throws Exception
     */
    AssetCountResponse countStatus() throws Exception;

    /**
     * 按第二级品类统计数量
     *
     * @return
     * @throws Exception
     */
    AssetCountResponse countCategory() throws Exception;


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
     * @param asset 主键封装对象
     * @return AssetOuterResponse
     */
    AssetOuterResponse getByAssetId(AssetDetialCondition asset) throws Exception;

    /**
     * 资产变更
     * @param assetOuterRequest 资产信息
     */
    Integer changeAsset(AssetOuterRequest assetOuterRequest) throws Exception;
    /**
     * 1-计算设备 2-网络设备 3-安全设备 4-存储介质 5-其他设备
     * @param type 导出模板的类型
     */
    void exportTemplate(int type) throws Exception;

    /**
     * @param assetQuery 导出模板的条件
     */
    void exportData(AssetQuery assetQuery, HttpServletResponse servletResponse) throws Exception;

    /**
     * 硬件导入PC
     * @param file
     * @param areaId
     * @return
     */
    String importPc(MultipartFile file, AssetImportRequest areaId) throws Exception;
    /**
     * 硬件导入网络设备
     * @param file
     * @param areaId
     * @return
     */
    String importNet(MultipartFile file, AssetImportRequest areaId) throws Exception;
    /**
     * 硬件导入安全设备
     * @param file
     * @param areaId
     * @return
     */
    String importSecurity(MultipartFile file, AssetImportRequest areaId) throws Exception;
    /**
     * 硬件导存储设备
     * @param file
     * @param areaId
     * @return
     */
    String importStory(MultipartFile file, AssetImportRequest areaId) throws Exception;
    /**
     * 硬件其他设备
     * @param file
     * @param areaId
     * @return
     */
    String importOhters(MultipartFile file, AssetImportRequest areaId) throws Exception;
}
