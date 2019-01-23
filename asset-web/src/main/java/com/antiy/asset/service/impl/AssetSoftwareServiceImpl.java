package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetPortProtocolService;
import com.antiy.asset.service.IAssetSoftwareLicenseService;
import com.antiy.asset.service.IAssetSoftwareService;
import com.antiy.asset.templet.AssetSoftwareEntity;
import com.antiy.asset.templet.ExportSoftwareEntity;
import com.antiy.asset.templet.ImportResult;
import com.antiy.asset.util.*;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.query.AssetPortProtocolQuery;
import com.antiy.asset.vo.query.AssetSoftwareLicenseQuery;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.SoftwareQuery;
import com.antiy.asset.vo.request.AssetPortProtocolRequest;
import com.antiy.asset.vo.request.AssetSoftwareLicenseRequest;
import com.antiy.asset.vo.request.AssetSoftwareRequest;
import com.antiy.asset.vo.request.ManualStartActivityRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.*;
import com.antiy.common.download.DownloadVO;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.antiy.biz.file.FileHelper.logger;

/**
 * <p> 软件信息表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetSoftwareServiceImpl extends BaseServiceImpl<AssetSoftware> implements IAssetSoftwareService {

    @Resource
    private AssetSoftwareDao                                                 assetSoftwareDao;
    @Resource
    private AssetSoftwareRelationDao                                         assetSoftwareRelationDao;
    @Resource
    private AssetSoftwareLicenseDao                                          assetSoftwareLicenseDao;
    @Resource
    private AssetPortProtocolDao                                             assetPortProtocolDaoDao;
    @Resource
    private AssetCategoryModelDao                                            assetCategoryModelDao;
    @Resource
    private AssetOperationRecordDao                                          assetOperationRecordDao;
    @Resource
    private BaseConverter<AssetSoftwareRequest, AssetSoftware>               requestConverter;

    @Resource
    private BaseConverter<AssetSoftwareLicenseRequest, AssetSoftwareLicense> assetSoftwareLicenseBaseConverter;

    @Resource
    private BaseConverter<AssetSoftware, AssetSoftwareDetailResponse>        assetSoftwareDetailConverter;

    @Resource
    private IAssetPortProtocolService                                        iAssetPortProtocolService;

    @Resource
    private IAssetSoftwareLicenseService                                     iAssetSoftwareLicenseService;

    @Resource
    private TransactionTemplate                                              transactionTemplate;

    @Resource
    private ExcelDownloadUtil                                                excelDownloadUtil;

    @Resource
    private ActivityClient                                                   activityClient;

    @Resource
    private SoftwareEntityConvert                                            softwareEntityConvert;

    private static final Logger                                              LOGGER = LogUtils
        .get(AssetSoftwareServiceImpl.class);

    @Override
    public Integer saveAssetSoftware(AssetSoftwareRequest request) throws Exception {
        Integer num = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    AssetSoftware assetSoftware = requestConverter.convert(request, AssetSoftware.class);
                    // AssetSoftwareLicense license = new BaseConverter<AssetSoftwareLicenseRequest,
                    // AssetSoftwareLicense>().convert(
                    // request.getSoftwareLicenseRequest(), AssetSoftwareLicense.class);
                    // AssetPortProtocol protocol = new BaseConverter<AssetPortProtocolRequest,
                    // AssetPortProtocol>().convert(
                    // request.getAssetPortProtocolRequest(), AssetPortProtocol.class);
                    // license.setSoftwareId(sid);
                    // protocol.setAssetSoftId(sid);
                    // assetSoftwareLicenseDao.insert(license);
                    // assetPortProtocolDaoDao.insert(protocol);
                    assetSoftware.setSoftwareStatus(AssetStatusEnum.ANALYZE.getCode());
                    assetSoftware.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetSoftware.setGmtCreate(System.currentTimeMillis());
                    assetSoftware.setReportSource(2);
                    assetSoftwareDao.insert(assetSoftware);
                    Integer sid = assetSoftware.getId();
                    if (ArrayUtils.isNotEmpty(request.getAssetIds())) {
                        String[] assetIds = request.getAssetIds();
                        for (String s : assetIds) {
                            AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                            assetSoftwareRelation.setSoftwareId(sid);
                            assetSoftwareRelation.setAssetId(Integer.parseInt(s));
                            assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
                            assetSoftwareRelation.setSoftwareStatus(AssetStatusEnum.ANALYZE.getCode());
                            assetSoftwareRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            assetSoftwareRelationDao.insert(assetSoftwareRelation);
                        }
                    }
                    // 记录资产操作流程
                    AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
                    assetOperationRecord.setTargetObjectId(sid);
                    assetOperationRecord.setTargetType(AssetOperationTableEnum.SOFTWARE.getCode());
                    assetOperationRecord.setTargetStatus(AssetStatusEnum.ANALYZE.getCode());
                    assetOperationRecord.setContent("登记软件资产");
                    assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
                    assetOperationRecord.setGmtCreate(System.currentTimeMillis());
                    assetOperationRecordDao.insert(assetOperationRecord);
                    // 写入业务日志
                    LogHandle.log(assetSoftware.toString(), AssetEventEnum.SOFT_INSERT.getName(),
                            AssetEventEnum.SOFT_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum.SOFT_INSERT.getName() + " {}", assetSoftware.toString());
                    return sid;
                } catch (Exception e) {
                    LOGGER.warn ("登记软件信息失败", e);
                    return 0;
                }
            }
        });

        if (num != null && num > 0) {
            // 启动流程
            ManualStartActivityRequest activityRequest = request.getActivityRequest();
            activityRequest.setBusinessId(String.valueOf(num));
            activityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
            ActionResponse actionResponse = activityClient.manualStartProcess(activityRequest);
            if (null==actionResponse||!actionResponse.isSuccess ()){
                throw  new BusinessException ("软件资产登记流程失败",new Throwable ());
            }
        }

        return num;

    }

    @Override
    @Transactional
    public Integer batchSave(List<AssetSoftware> assetSoftwareList) throws Exception {
        int i = 0;
        for (; i < assetSoftwareList.size(); i++) {
            AssetSoftware t = assetSoftwareList.get(i);
            t.setGmtCreate(System.currentTimeMillis());
            t.setSoftwareStatus(3);
            assetSoftwareDao.insert(t);
        }
        return i + 1;
    }

    @Override
    public Integer updateAssetSoftware(AssetSoftwareRequest request) throws Exception {
        Integer count = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    // 1.更新软件信息
                    request.setSoftwareStatus(3); // 软件变更需要改状态到带配置
                    AssetSoftware assetSoftware = requestConverter.convert(request, AssetSoftware.class);
                    assetSoftware.setId(DataTypeUtils.stringToInteger(request.getId()));
                    int assetSoftwareCount = assetSoftwareDao.update(assetSoftware);

                    // 2.更新license表
                    if (null != request.getSoftwareLicenseRequest()
                        && StringUtils.isNotBlank(request.getSoftwareLicenseRequest().getId())) {
                        updateLicense(request);
                    }

                    // 3.是否存在关联表Id和状态，如果存在，则更新关联表即可(更新某一个实例)
                    if (StringUtils.isNotBlank(request.getAssetSoftwareRelationId())
                        && request.getSoftwareStatus() != null) {
                        AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                        assetSoftwareRelation
                            .setId(DataTypeUtils.stringToInteger(request.getAssetSoftwareRelationId()));
                        assetSoftwareRelation.setSoftwareStatus(request.getSoftwareStatus());
                        assetSoftwareRelationDao.update(assetSoftwareRelation);
                    } else if (ArrayUtils.isNotEmpty(request.getAssetIds())) { // 更新一批实例
                        // 4.移除端口和关联表的关系
                        List<Integer> releationIds = assetSoftwareRelationDao.getAllReleationId(null,
                            DataTypeUtils.stringToInteger(request.getId()));
                        if (CollectionUtils.isNotEmpty(releationIds)) {
                            assetPortProtocolDaoDao.deletePortProtocol(releationIds);
                        }

                        // 5.移除关系表
                        assetSoftwareRelationDao.deleteSoftwareRelAsset(null,
                            DataTypeUtils.stringToInteger(request.getId()));

                        // 5.插入关系表，并且插入端口数据
                        for (String assetId : request.getAssetIds()) {
                            AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                            assetSoftwareRelation.setSoftwareId(DataTypeUtils.stringToInteger(request.getId()));
                            assetSoftwareRelation.setAssetId(DataTypeUtils.stringToInteger(assetId));
                            assetSoftwareRelation.setSoftwareStatus(request.getSoftwareStatus());
                            assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
                            assetSoftwareRelationDao.insert(assetSoftwareRelation);
                            ParamterExceptionUtils.isNull(assetSoftwareRelation.getId(), "更新软件失败");

                            // 批量插入端口信息
                            if (ArrayUtils.isNotEmpty(request.getAssetPortProtocolRequest().getPort())) {
                                AssetPortProtocol protocol = new BaseConverter<AssetPortProtocolRequest, AssetPortProtocol>()
                                    .convert(request.getAssetPortProtocolRequest(), AssetPortProtocol.class);
                                protocol.setAssetSoftId(assetSoftwareRelation.getId());
                                for (Integer port : request.getAssetPortProtocolRequest().getPort()) {
                                    protocol.setPort(port);
                                    // 插入端口信息
                                    assetPortProtocolDaoDao.insert(protocol);
                                }
                            }

                        }
                    }
                    // 写入业务日志
                    LogHandle.log(assetSoftware.toString(), AssetEventEnum.SOFT_UPDATE.getName(),
                            AssetEventEnum.SOFT_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum.SOFT_UPDATE.getName() + " {}", assetSoftware.toString());
                    return assetSoftwareCount;
                } catch (Exception e) {
                    LOGGER.error("修改软件信息失败", e);
                }
                return 0;
            }
        });

        // TODO 调用工作流，给配置管理员
        activityClient.completeTask(request.getRequest());
        return count;
    }

    /**
     * 更新lincense
     * @param request
     */
    private void updateLicense(AssetSoftwareRequest request) throws Exception {
        AssetSoftwareLicense assetSoftwareLicense = assetSoftwareLicenseBaseConverter
            .convert(request.getSoftwareLicenseRequest(), AssetSoftwareLicense.class);
        assetSoftwareLicense.setSoftwareId(DataTypeUtils.stringToInteger(request.getId()));
        // 写入业务日志
        LogHandle.log(assetSoftwareLicense.toString(), AssetEventEnum.SOFT_INSERT.getName(),
                AssetEventEnum.SOFT_LICENSE_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.SOFT_LICENSE_UPDATE.getName() + " {}", assetSoftwareLicense.toString());
        assetSoftwareLicenseDao.update(assetSoftwareLicense);
    }

    private Map<Integer, Long> handleSoftCount(List<Map<String, Object>> softObjectList) {
        Map<Integer, Long> map = new HashMap<>();
        for (Map<String, Object> objectMap : softObjectList) {
            Integer id = objectMap.get("id") != null ? Integer.valueOf(objectMap.get("id").toString()) : 0;
            Long count = objectMap.get("name") != null ? Long.parseLong(objectMap.get("name").toString()) : 0;
            map.put(id, count);
        }
        return map;
    }

    @Override
    public List<AssetSoftwareResponse> findListAssetSoftware(AssetSoftwareQuery query) throws Exception {
        List<AssetSoftware> assetSoftware = assetSoftwareDao.findListAssetSoftware(query);
        Map<Integer, Long> softAssetCount = null;
        if (query.getQueryAssetCount()) {
            List<Integer> allSoftwareIds = new ArrayList<>();
            assetSoftware.stream().forEach(assetSoftwareDO -> allSoftwareIds.add(assetSoftwareDO.getId()));
            softAssetCount = handleSoftCount(assetSoftwareRelationDao.countSoftwareRelAsset(allSoftwareIds));
        }

        Map<Integer, Long> finalSoftAssetCount = softAssetCount;
        BaseConverter baseConverter = new BaseConverter<AssetSoftware, AssetSoftwareResponse>() {

            @Override
            protected void convert(AssetSoftware assetSoftware, AssetSoftwareResponse assetSoftwareResponse) {
                super.convert(assetSoftware, assetSoftwareResponse);
                if (MapUtils.isNotEmpty(finalSoftAssetCount)) {
                    assetSoftwareResponse.setAssetCount(finalSoftAssetCount.get(assetSoftware.getId()) != null
                        ? finalSoftAssetCount.get(assetSoftware.getId()).intValue()
                        : 0);
                }
            }
        };
        return baseConverter.convert(assetSoftware, AssetSoftwareResponse.class);
    }

    public Integer findCountAssetSoftware(AssetSoftwareQuery query) throws Exception {
        return assetSoftwareDao.findCount(query);
    }

    @Override
    public PageResult<AssetSoftwareResponse> findPageAssetSoftware(AssetSoftwareQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetSoftware(query), query.getCurrentPage(),
            this.findListAssetSoftware(query));
    }

    @Override
    public List<String> getManufacturerName(String manufacturerName) throws Exception {
        List<Integer> list = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        return assetSoftwareDao.findManufacturerName(manufacturerName, list);
    }

    /**
     * 查询出品类和其子品类
     *
     * @param id 查询的品类id
     */
    public List<AssetCategoryModel> recursionSearch(Integer id) throws Exception {
        List<AssetCategoryModel> list = assetCategoryModelDao.getAll();
        List<AssetCategoryModel> result = new ArrayList();
        for (AssetCategoryModel AssetCategoryModel : list) {
            if (AssetCategoryModel.getId().equals(id)) {
                result.add(AssetCategoryModel);
            }
        }
        recursion(result, list, id);
        return result;
    }

    /**
     * 递归查询出所有的品类和其子品类
     *
     * @param result 查询的结果集
     * @param list 查询的数据集
     * @param id 递归的参数
     */
    private void recursion(List<AssetCategoryModel> result, List<AssetCategoryModel> list, Integer id) {
        for (AssetCategoryModel AssetCategoryModel : list) {
            if (Objects.equals(AssetCategoryModel.getParentId(), id)) {
                result.add(AssetCategoryModel);
                recursion(result, list, AssetCategoryModel.getId());
            }
        }
    }

    @Override
    public AssetCountResponse countManufacturer() throws Exception {
        int maxNum = 5;
        List<Integer> ids = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        List<Map<String, Long>> list = assetSoftwareDao.countManufacturer(ids);
        if (list.size() > maxNum) {
            list.sort(new Comparator<Map<String, Long>>() {
                @Override
                public int compare(Map<String, Long> o1, Map<String, Long> o2) {
                    return (int) (o2.get("value") - o1.get("value"));
                }
            });
        }
        Map result = new HashMap();
        int i = 0;
        long sum = 0;
        for (Map map : list) {
            if (i < maxNum) {
                result.put(map.get("key"), map.get("value"));
                i++;
            } else {
                sum = sum + (Long) map.get("value");
            }
        }
        result.put("其他", sum);
        AssetCountResponse assetCountResponse = new AssetCountResponse();
        assetCountResponse.setMap(result);
        return assetCountResponse;
    }

    @Override
    public AssetCountResponse countStatus() throws Exception {
        List<Integer> ids = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        List<Map<String, Long>> list = assetSoftwareDao.countStatus(ids);
        Map<String, Long> result = new HashMap();
        for (Map map : list) {
            AssetStatusEnum assetStatusEnum = AssetStatusEnum.getAssetByCode((Integer) map.get("key"));
            result.put(assetStatusEnum == null ? "" : assetStatusEnum.getMsg(), (Long) map.get("value"));
        }
        AssetCountResponse assetCountResponse = new AssetCountResponse();
        assetCountResponse.setMap(result);
        return assetCountResponse;
    }

    @Override
    public AssetCountResponse countCategory() throws Exception {
        List<Integer> ids = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        HashMap<String, Object> map = new HashMap();
        map.put("name", "软件");
        map.put("parentId", 0);
        List<AssetCategoryModel> categoryModelList = assetCategoryModelDao.getByWhere(map);
        if (CollectionUtils.isNotEmpty(categoryModelList)) {
            Integer id = categoryModelList.get(0).getId();
            map.clear();
            map.put("parentId", id);
            List<AssetCategoryModel> categoryModelList1 = assetCategoryModelDao.getByWhere(map);
            HashMap<String, Long> result = new HashMap<>();
            for (AssetCategoryModel a : categoryModelList1) {
                List<AssetCategoryModel> search = recursionSearch(a.getId());
                List list = new ArrayList();
                for (AssetCategoryModel b : search) {
                    list.add(b.getId());
                }
                AssetSoftwareQuery assetSoftwareQuery = new AssetSoftwareQuery();
                assetSoftwareQuery.setCategoryModels(ArrayTypeUtil.ObjectArrayToIntegerArray(list.toArray()));
                assetSoftwareQuery.setAreaIds(ArrayTypeUtil.ObjectArrayToIntegerArray(ids.toArray()));
                Long sum = assetSoftwareDao.findCountByCategoryModel(assetSoftwareQuery);
                result.put(a.getName(), sum);
            }
            AssetCountResponse assetCountResponse = new AssetCountResponse();
            assetCountResponse.setMap(result);
            return assetCountResponse;
        }
        return null;
    }

    @Override
    public AssetSoftwareDetailResponse querySoftWareDetail(SoftwareQuery softwareQuery) throws Exception {

        // 1 获取软件资产详情
        AssetSoftware assetSoftware = this.getById(DataTypeUtils.stringToInteger(softwareQuery.getPrimaryKey()));
        AssetSoftwareDetailResponse assetSoftwareDetailResponse = assetSoftwareDetailConverter.convert(assetSoftware,
            AssetSoftwareDetailResponse.class);

        // TODO 软件资产码表信息

        // 2 是否需要查询端口信息
        if (softwareQuery.getQueryPort()) {
            querySoftwarePort(softwareQuery, assetSoftwareDetailResponse);
        }

        // 3 是否需要查询license信息
        if (softwareQuery.getQueryLicense()) {
            querySoftwareLicense(softwareQuery, assetSoftwareDetailResponse);
        }

        return assetSoftwareDetailResponse;
    }

    @Override
    public void downloadSoftware(AssetSoftwareQuery query, HttpServletResponse response) throws Exception {
        query.setPageSize(Constants.MAX_PAGESIZE);
        PageResult<AssetSoftwareResponse> pageResult = this.findPageAssetSoftware(query);
        List<AssetSoftwareResponse> items = pageResult.getItems();
        while (!CollectionUtils.isNotEmpty(items)) {

            // 如果返回数据小于当前页，那么直接退出循环
            pageResult.setCurrentPage(getNextPage(pageResult));
            if (items.size() < pageResult.getPageSize()) {
                break;
            }
            query.setCurrentPage(pageResult.getCurrentPage());
            items = this.findPageAssetSoftware(query).getItems();
        }
        DownloadVO downloadVO = new DownloadVO();
        downloadVO.setDownloadList(Collections.singletonList(items));
        excelDownloadUtil.excelDownload(response, "软件导出", downloadVO);

    }

    @Override
    public void exportData(AssetSoftwareQuery assetSoftwareQuery, HttpServletResponse response) throws Exception {
        exportData(AssetSoftwareEntity.class, "软件信息表", assetSoftwareQuery, response);
    }

    @Override
    public List<AssetSoftwareResponse> findInstallList(AssetSoftwareQuery softwareQuery) throws Exception {
        List<AssetSoftware> assetSoftwareList = assetSoftwareDao.findInstallList(softwareQuery);
        List<AssetSoftwareResponse> assetSoftwareResponseList = BeanConvert.convert(assetSoftwareList,
            AssetSoftwareResponse.class);
        return assetSoftwareResponseList;
    }

    @Override
    public PageResult<AssetSoftwareResponse> findPageInstall(AssetSoftwareQuery softwareQuery) throws Exception {
        return new PageResult<>(softwareQuery.getPageSize(), this.findCountInstall(softwareQuery),
            softwareQuery.getCurrentPage(), this.findInstallList(softwareQuery));
    }

    @Override
    public List<AssetSoftwareInstallResponse> findAssetInstallList(AssetSoftwareQuery softwareQuery) throws Exception {
        return BeanConvert.convert(assetSoftwareDao.findAssetInstallList(softwareQuery),
            AssetSoftwareInstallResponse.class);
    }

    public Integer findAssetInstallCount(AssetSoftwareQuery query) throws Exception {
        return assetSoftwareDao.findAssetInstallCount(query);
    }

    @Override
    public PageResult<AssetSoftwareInstallResponse> findPageAssetInstall(AssetSoftwareQuery softwareQuery) throws Exception {
        return new PageResult<>(softwareQuery.getPageSize(), this.findAssetInstallCount(softwareQuery),
            softwareQuery.getCurrentPage(), this.findAssetInstallList(softwareQuery));
    }

    public Integer findCountInstall(AssetSoftwareQuery query) throws Exception {
        return assetSoftwareDao.findCount(query);
    }

    @Override
    public PageResult<AssetSoftwareResponse> findPageInstallList(AssetSoftwareQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountInstall(query), query.getCurrentPage(),
            this.findInstallList(query));
    }

    @Override
    public String importExcel(MultipartFile file, String areaId) throws Exception {
        ImportResult<AssetSoftwareEntity> re = ExcelUtils.importExcelFromClient(AssetSoftwareEntity.class, file, 0, 0);
        List<AssetSoftwareEntity> resultDataList = re.getDataList();
        int success = 0;
        // int repeat=0;
        int error = 0;
        StringBuilder builder = new StringBuilder();
        for (AssetSoftwareEntity entity : resultDataList) {
            if (StringUtils.isBlank(entity.getName())) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("软件名称为空");
                continue;
            }
            if (StringUtils.isBlank(entity.getVersion())) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("软件版本");
                continue;
            }
            AssetSoftware asset = new AssetSoftware();
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
            // 可分析
            asset.setSoftwareStatus(2);
            asset.setName(entity.getName());
            asset.setVersion(entity.getVersion());
            asset.setManufacturer(entity.getManufacturer());
            asset.setOperationSystem(entity.getOperationSystem());
            asset.setSerial(entity.getSerial());
            asset.setBuyDate(entity.getBuyDate());
            asset.setServiceLife(entity.getServiceLife());
            asset.setAuthorization(entity.getAuthorization());
            asset.setMemo(entity.getDescription());
            asset.setCategoryModel(entity.getCategory());
            asset.setSize(entity.getSize());

            assetSoftwareDao.insert(asset);
            // 记录资产操作流程
            AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
            assetOperationRecord.setTargetObjectId(asset.getId());
            assetOperationRecord.setTargetType(AssetOperationTableEnum.SOFTWARE.getCode());
            assetOperationRecord.setTargetStatus(AssetStatusEnum.ANALYZE.getCode());
            assetOperationRecord.setContent("登记软件资产");
            assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
            assetOperationRecord.setGmtCreate(System.currentTimeMillis());
            assetOperationRecordDao.insert(assetOperationRecord);

            // // TODO: 2019/1/22 根据区域ID 查询全部的分析人员

            Map<String, Object> formData = new HashMap();
            // formData.put("analyzeBaselineUserId", analyzeBaselineUserId);
            formData.put("discard", 0);
            ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
            manualStartActivityRequest.setBusinessId(asset.getId().toString());
            manualStartActivityRequest.setFormData(JSONObject.toJSONString(formData));
            manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getName());
            manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.SOFTWARE_ADMITTANCE.getCode());
            activityClient.manualStartProcess(manualStartActivityRequest);
            success++;
        }

        String res = "导入成功" + success + "条";
        // res += repeat > 0 ? ", " + repeat + "条编号重复" : "";
        res += error > 0 ? ", " + error + "条数据导入失败" : "";
        StringBuilder stringBuilder = new StringBuilder();
        if (error > 0) {
            stringBuilder.append(re).append("其中").append(builder);
        }
        // 写入业务日志
        LogHandle.log(resultDataList.toString(), AssetEventEnum.SOFT_EXPORT.getName(),
                AssetEventEnum.SOFT_EXPORT.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.SOFT_EXPORT.getName() + " {}", resultDataList.toString());
        return stringBuilder.toString();
    }

    private void exportData(Class<AssetSoftwareEntity> assetSoftwareEntityClass, String s,
                            AssetSoftwareQuery assetSoftwareQuery, HttpServletResponse response) throws Exception {
        assetSoftwareQuery.setAreaIds(
            ArrayTypeUtil.ObjectArrayToIntegerArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser().toArray()));
        assetSoftwareQuery.setQueryAssetCount(true);
        List<AssetSoftwareResponse> list = this.findListAssetSoftware(assetSoftwareQuery);
        ParamterExceptionUtils.isEmpty(list, "资产数据不能为空");
        DownloadVO downloadVO = new DownloadVO();
        downloadVO.setSheetName("资产信息表");
        List<ExportSoftwareEntity> softwareEntities = softwareEntityConvert.convert(list, ExportSoftwareEntity.class);
        downloadVO.setDownloadList(softwareEntities);
        excelDownloadUtil.excelDownload(response, s, downloadVO);
    }

    @Override
    public Integer changeStatusById(Map<String, Object> map) throws Exception {
        return assetSoftwareDao.changeStatusById(map);
    }

    @Override
    public void exportTemplate() throws Exception {
        exportToClient(AssetSoftwareEntity.class, "软件信息模板.xlsx", "软件信息");
    }

    @Override
    public List<String> pulldownManufacturer() {
        return assetSoftwareDao.pulldownManufacturer();
    }

    private void exportToClient(Class clazz, String fileName, String title) {
        ExcelUtils.exportTemplet(clazz, fileName, title);
    }

    private int getNextPage(PageResult pageResult) {
        int currentPage = pageResult.getCurrentPage() + 1;
        int pages = pageResult.getTotalPages();
        if (pages > 0) {
            return currentPage > pages ? pages : currentPage < 1 ? 1 : currentPage;
        }
        return 0;
    }

    /**
     * 查询软件端口信息
     * @param softwareQuery
     * @param assetSoftwareDetailResponse
     * @throws Exception
     */
    private void querySoftwarePort(SoftwareQuery softwareQuery,
                                   AssetSoftwareDetailResponse assetSoftwareDetailResponse) throws Exception {
        AssetPortProtocolQuery assetPortProtocolQuery = new AssetPortProtocolQuery();
        assetPortProtocolQuery.setAssetSoftId(softwareQuery.getPrimaryKey());
        assetPortProtocolQuery.setPageSize(Constants.MAX_PAGESIZE);
        List<AssetPortProtocolResponse> assetPortProtocolResponses = iAssetPortProtocolService
            .findListAssetPortProtocol(assetPortProtocolQuery);
        assetSoftwareDetailResponse.setSoftwarePort(assetPortProtocolResponses);
    }

    /**
     * 查询软件license 信息
     * @param softwareQuery
     * @param assetSoftwareDetailResponse
     * @throws Exception
     */
    private void querySoftwareLicense(SoftwareQuery softwareQuery,
                                      AssetSoftwareDetailResponse assetSoftwareDetailResponse) throws Exception {
        AssetSoftwareLicenseQuery assetSoftwareLicenseQuery = new AssetSoftwareLicenseQuery();
        assetSoftwareLicenseQuery.setSoftwareId(DataTypeUtils.stringToInteger(softwareQuery.getPrimaryKey()));
        assetSoftwareLicenseQuery.setPageSize(Constants.MAX_PAGESIZE);
        List<AssetSoftwareLicenseResponse> assetSoftwareLicenseResponses = iAssetSoftwareLicenseService
            .findListAssetSoftwareLicense(assetSoftwareLicenseQuery);
        assetSoftwareDetailResponse.setSoftwareLicense(assetSoftwareLicenseResponses);
    }

}

@Component
class SoftwareEntityConvert extends BaseConverter<AssetSoftwareResponse, ExportSoftwareEntity> {
    @Override
    protected void convert(AssetSoftwareResponse assetSoftware, ExportSoftwareEntity exportSoftwareEntity) {
        exportSoftwareEntity.setCategoryName(assetSoftware.getCategoryModelName());
        if (Objects.nonNull(assetSoftware.getSoftwareStatus())) {
            AssetStatusEnum assetStatusEnum = SoftwareStatusEnum.getAssetByCode(assetSoftware.getSoftwareStatus());
            exportSoftwareEntity.setStatus(assetStatusEnum == null ? "" : assetStatusEnum.getMsg());
        }
        if (Objects.nonNull(assetSoftware.getStringId())) {
            exportSoftwareEntity.setId(Integer.parseInt(assetSoftware.getStringId()));
        }
        super.convert(assetSoftware, exportSoftwareEntity);
    }
}