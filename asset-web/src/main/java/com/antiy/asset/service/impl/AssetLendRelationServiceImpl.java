package com.antiy.asset.service.impl;

import com.antiy.asset.convert.LendConvert;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLendRelationDao;
import com.antiy.asset.entity.AssetLendRelation;
import com.antiy.asset.login.LoginTool;
import com.antiy.asset.service.IAssetLendRelationService;
import com.antiy.asset.templet.AssetLendRelationEntity;
import com.antiy.asset.util.Constants;
import com.antiy.asset.util.SnowFlakeUtil;
import com.antiy.asset.vo.query.ApproveListQuery;
import com.antiy.asset.vo.query.AssetLendRelationQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.*;
import com.antiy.common.download.DownloadVO;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.DateUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.netty.util.internal.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2020-04-07
 */
@Service
public class AssetLendRelationServiceImpl extends BaseServiceImpl<AssetLendRelation> implements IAssetLendRelationService {

    private Logger logger = LogUtils.get(this.getClass());

    @Resource
    private AssetLendRelationDao assetLendRelationDao;
    @Resource
    private ExcelDownloadUtil excelDownloadUtil;
    @Resource
    private LendConvert lendConvert;
    @Resource
    private AssetDao assetDao;
    @Resource
    private BaseConverter<AssetLendRelationRequest, AssetLendRelation> requestConverter;
    @Resource
    private BaseConverter<AssetLendRelation, AssetLendRelationResponse> responseConverter;
    @Resource
    private BaseConverter<AssetLendInfoRequest, AssetLendRelation> lendRelationBaseConverter;
    @Resource
    private BaseConverter<AssetLendInfosRequest, AssetLendRelation> lendInfosRequestAssetLendRelationBaseConverter;
    @Resource
    private LoginUserUtil loginUserUtil;
    @Resource
    private AesEncoder aesEncoder;

    @Override
    public String saveAssetLendRelation(AssetLendRelationRequest request) throws Exception {
        AssetLendRelation assetLendRelation = requestConverter.convert(request, AssetLendRelation.class);
        assetLendRelationDao.insert(assetLendRelation);
        return assetLendRelation.getStringId();
    }

    @Override
    public String updateAssetLendRelation(AssetLendRelationRequest request) throws Exception {
        AssetLendRelation assetLendRelation = requestConverter.convert(request, AssetLendRelation.class);
        return assetLendRelationDao.update(assetLendRelation).toString();
    }

    @Override
    public List<AssetLendRelationResponse> queryListAssetLendRelation(AssetLendRelationQuery query) throws Exception {
        List<AssetLendRelationResponse> assetLendRelationList = assetLendRelationDao.getLendRelationList(query);
        return assetLendRelationList;
    }

    @Override
    public PageResult<AssetLendRelationResponse> queryPageAssetLendRelation(AssetLendRelationQuery query) throws Exception {
        List<String> areaIdsOfCurrentUser = LoginTool.getLoginUser().getAreaIdsOfCurrentUser();
        query.setAreaIds(areaIdsOfCurrentUser);
        return new PageResult<AssetLendRelationResponse>(query.getPageSize(), this.findCount(query), query.getCurrentPage(), this.queryListAssetLendRelation(query));
    }

    @Override
    public void exportData(AssetLendRelationQuery assetQuery, HttpServletResponse response,
                           HttpServletRequest request) throws Exception {
        if ((assetQuery.getStart() != null && assetQuery.getEnd() != null)) {
            assetQuery.setStart(assetQuery.getStart() - 1);
            assetQuery.setEnd(assetQuery.getEnd() - assetQuery.getStart());
        }
        assetQuery.setPageSize(Constants.ALL_PAGE);
        assetQuery.setAreaIds(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        DownloadVO downloadVO = new DownloadVO();
        List<AssetLendRelationResponse> items = queryPageAssetLendRelation(assetQuery).getItems();
        List<AssetLendRelationEntity> assetEntities = lendConvert.convert(items, AssetLendRelationEntity.class);
        downloadVO.setDownloadList(assetEntities);
        downloadVO.setSheetName("出借管理表");
        // 3种导方式 1 excel 2 cvs 3 xml
        if (CollectionUtils.isNotEmpty(downloadVO.getDownloadList())) {

            excelDownloadUtil.excelDownload(request, response,
                    "出借管理表" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT), downloadVO);

            LogUtils.recordOperLog(
                    new BusinessData("导出《出借管理表" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT) + "》", 0,
                            "", assetQuery, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
        } else {
            throw new BusinessException("导出数据为空");
        }
    }

    @Override
    public AssetLendRelationResponse queryAssetLendRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetLendRelationResponse assetLendRelationResponse = responseConverter
                .convert(assetLendRelationDao.getById(queryCondition.getPrimaryKey()), AssetLendRelationResponse.class);
        return assetLendRelationResponse;
    }

    @Override
    public String deleteAssetLendRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetLendRelationDao.deleteById(baseRequest.getStringId()).toString();
    }

    @Override
    public AssetLendRelationResponse queryInfo(String uniqueId) {

        return assetLendRelationDao.queryInfo(uniqueId);
    }

    @Override
    public Integer returnConfirm(AssetLendRelationRequest assetLendRelationRequest) {
        ParamterExceptionUtils.isNull(assetLendRelationRequest.getReturnTime(), "归还时间不能为空！");
        Integer result = assetLendRelationDao.returnConfirm(assetLendRelationRequest);
        LogUtils.recordOperLog(
                new BusinessData("归还出借资产", assetLendRelationRequest.getAssetId(), assetDao.getNumberById(assetLendRelationRequest.getAssetId()),
                        assetLendRelationRequest, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE)
        );
        return result;
    }

    @Override
    public List<AssetLendRelationResponse> queryHistory(ObjectQuery objectQuery) {

        List<AssetLendRelationResponse> assetLendRelationResponses = assetLendRelationDao.queryHistory(objectQuery);
        if (CollectionUtils.isNotEmpty(assetLendRelationResponses)) {
            assetLendRelationResponses.forEach(t -> {
                if (t.getLendStatus() == 2) {
                    t.setLendStatusDesc("已归还");
                }
            });
        }
        return assetLendRelationResponses;

    }

    @Override
    public String saveLendInfo(AssetLendInfoRequest request) throws Exception {
        ParamterExceptionUtils.isBlank(String.valueOf(request.getAssetId()), "资产Id不能为空");
        AssetLendRelation assetLendRelation = lendRelationBaseConverter.convert(request, AssetLendRelation.class);

        assetLendRelation.setAssetId(aesEncoder.decode(request.getAssetId(), LoginUserUtil.getLoginUser().getUsername()));
        assetLendRelation.setLendStatus(Integer.valueOf(1));
        assetLendRelation.setUniqueId(SnowFlakeUtil.getSnowId());
        assetLendRelation.setGmtCreate(System.currentTimeMillis());
        assetLendRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetLendRelation.setStatus(Integer.valueOf(1));
        //校验资产状态和是否以出借
        this.checkAssetStatus(Arrays.asList(assetLendRelation.getAssetId()));
        //出借保存
        this.insertAssetLendRelation(assetLendRelation);
        //日志
        LogUtils.recordOperLog(
                new BusinessData("借出资产", assetLendRelation.getAssetId(), assetDao.getNumberById(assetLendRelation.getAssetId()),
                        request, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE)
        );

        return request.toString();
    }

    @Override
    public PageResult<ApproveListResponse> queryApproveList(ApproveListQuery query) {
        List<ApproveListResponse> assetLendRelationResponses = assetLendRelationDao.queryApproveList(query);
        return new PageResult<ApproveListResponse>(query.getPageSize(), assetLendRelationDao.queryApproveListCount(query), query.getCurrentPage(), assetLendRelationResponses);

    }

    @Override
    public List<UserListResponse> queryUserList(ApplicantRequest request) {
        return assetLendRelationDao.queryUserList(request);
    }

    @Override
    public UserInfoResponse queryUserInfo(UserInfoRequest request) {
        UserInfoResponse response = assetLendRelationDao.queryUserInfo(request);
        String department = new String();
        if (Objects.nonNull(response)) {
            this.getDepartment(response.getDepartmentId(), department);
            response.setDepartment(department);
        }
        return response;
    }

    @Override
    public String saveLendInfos(AssetLendInfosRequest request) throws Exception {
        ParamterExceptionUtils.isNull(request.getUseId(), "用户ID不能为空");
        ParamterExceptionUtils.isNull(request.getAssetIds(), "资产列表不能为空");
        ParamterExceptionUtils.isNull(request.getOrderNumber(), "OA编号不能为空");

        String userName = LoginUserUtil.getLoginUser().getUsername();

        AssetLendRelation assetLendRelation = lendInfosRequestAssetLendRelationBaseConverter.convert(request, AssetLendRelation.class);
        assetLendRelation.setLendStatus(Integer.valueOf(1));
        assetLendRelation.setUniqueId(SnowFlakeUtil.getSnowId());
        assetLendRelation.setGmtCreate(System.currentTimeMillis());
        assetLendRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetLendRelation.setStatus(Integer.valueOf(1));

        //校验资产状态
        this.checkAssetStatus(request.getAssetIds());
        for (String item : request.getAssetIds()) {
            //出借保存
            assetLendRelation.setAssetId(aesEncoder.decode(item, userName));
            //由oa系统发起出借，出借模块无需添加日志
            this.insertAssetLendRelation(assetLendRelation);
        }
        return request.toString();
    }

    @Override
    public AssetResponse queryAssetInfo(Integer id) {

        AssetResponse assetResponse = assetDao.queryInfoByAssetId(id);
        return assetResponse;
    }

    public void getDepartment(String departmentId, String department) {
        department = assetLendRelationDao.queryDepartment(departmentId) + department;
        String parentId = assetLendRelationDao.queryDepartmentParent(departmentId);
        if (!StringUtil.isNullOrEmpty(parentId)) {
            getDepartment(parentId, department);
        }
    }

    private void insertAssetLendRelation(AssetLendRelation assetLendRelation) throws Exception {
        if (assetLendRelationDao.countAsset(assetLendRelation.getAssetId()) <= 0) {
            logger.info("资产{}出借成功！", assetLendRelation.getAssetId());
            assetLendRelationDao.insert(assetLendRelation);
        } else {
            logger.error("资产{}重复出借！", assetLendRelation.getAssetId());
            throw new BusinessException("资产借出，请勿重复出借！");
        }
    }

    private void checkAssetStatus(List<String> assetIds) {
        if (assetLendRelationDao.checkStatusByAssetId(assetIds)) {
            logger.error("出借资产{}状态异常！", assetIds.toString());
            throw new BusinessException("资产状态异常，出借失败！");
        }
        for (String assetId : assetIds) {
            if (assetLendRelationDao.countAssetLend(assetId) > 0){
                throw new BusinessException("存在以出借资产，出借失败！");
            }
        }
    }
}
