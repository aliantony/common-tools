package com.antiy.asset.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.antiy.asset.vo.request.AssetSoftwareReportRequest;
import org.springframework.web.multipart.MultipartFile;

import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.ConfigRegisterRequest;
import com.antiy.asset.vo.query.SoftwareQuery;
import com.antiy.asset.vo.request.AssetImportRequest;
import com.antiy.asset.vo.request.AssetSoftwareRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

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
    List<AssetSoftwareResponse> findListAssetSoftware(AssetSoftwareQuery query,
                                                      Map<String, WaitingTaskReponse> waitingTasks) throws Exception;

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
     * 软件详情查询
     * @param softwareQuery 软件详情查询条件
     * @return
     * @throws Exception
     */
    AssetSoftwareDetailResponse querySoftWareDetail(SoftwareQuery softwareQuery) throws Exception;

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

    /**
     * 硬件登记、软件安装配置接口
     * @param request
     */
    ActionResponse configRegister(ConfigRegisterRequest request) throws Exception;

    /**
     * 处理资产上报的软件信息
     * @param assetId
     * @param softwareReportRequestList
     */
    void reportData(Integer assetId, List<AssetSoftwareReportRequest> softwareReportRequestList);
}
