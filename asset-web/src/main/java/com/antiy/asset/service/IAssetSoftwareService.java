package com.antiy.asset.service;

import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.SoftwareQuery;
import com.antiy.asset.vo.request.AssetImportRequest;
import com.antiy.asset.vo.request.AssetSoftwareRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p> 软件信息表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetSoftwareService extends IBaseService<AssetSoftware> {

    /**
     * 批量保存
     *
     * @param assetSoftwareList
     * @return
     */
    Integer batchSave(List<AssetSoftware> assetSoftwareList) throws Exception;

    /**
     * 保存
     *
     * @param request
     * @return
     */
    ActionResponse saveAssetSoftware(AssetSoftwareRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetSoftware(AssetSoftwareRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetSoftwareResponse> findListAssetSoftware(AssetSoftwareQuery query,Map<String, WaitingTaskReponse> waitingTaskReponseMap) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetSoftwareResponse> findPageAssetSoftware(AssetSoftwareQuery query) throws Exception;

    /**
     * 查询厂商
     * @param manufacturerName
     * @return
     * @throws Exception
     */
    List<String> getManufacturerName(String manufacturerName) throws Exception;

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
    AssetCountColumnarResponse countStatus() throws Exception;

    /**
     * 按第二级品类统计数量
     *
     * @return
     * @throws Exception
     */
    AssetCountResponse countCategory() throws Exception;

    /**
     * 软件详情查询
     * @param softwareQuery 软件详情查询条件
     * @return
     * @throws Exception
     */
    AssetSoftwareDetailResponse querySoftWareDetail(SoftwareQuery softwareQuery) throws Exception;

    /**
     * 导出
     * @param query
     * @param response
     * @throws Exception
     */
    void downloadSoftware(AssetSoftwareQuery query, HttpServletResponse response) throws Exception;

    /**
     * 通过ID修改软件状态
     *
     * @param map
     * @return
     */
    Integer changeStatusById(Map<String, Object> map) throws Exception;

    /**
     * 导出
     * @throws Exception
     */
    void exportTemplate() throws Exception;

    List<String> pulldownManufacturer();

    void exportData(AssetSoftwareQuery assetSoftwareQuery, HttpServletResponse response) throws Exception;


    PageResult<AssetSoftwareResponse> findPageInstallList(AssetSoftwareQuery softwareQuery) throws Exception;

    /**
     * 导入
     * @param file
     * @param areaId
     * @return
     */
    String importExcel(MultipartFile file, AssetImportRequest areaId) throws Exception;

    List<AssetSoftwareResponse> findInstallList(AssetSoftwareQuery softwareQuery) throws Exception;

    PageResult<AssetSoftwareResponse> findPageInstall(AssetSoftwareQuery softwareQuery) throws Exception;

    List<AssetSoftwareInstallResponse> findAssetInstallList(AssetSoftwareQuery softwareQuery) throws Exception;

    PageResult<AssetSoftwareInstallResponse> findPageAssetInstall(AssetSoftwareQuery softwareQuery) throws Exception;

}
