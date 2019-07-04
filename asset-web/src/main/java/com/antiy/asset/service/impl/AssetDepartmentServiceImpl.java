package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDepartmentDao;
import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.util.Constants;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.util.NodeUtilsConverter;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.query.AssetDepartmentQuery;
import com.antiy.asset.vo.query.AssetUserQuery;
import com.antiy.asset.vo.request.AssetDepartmentRequest;
import com.antiy.asset.vo.response.AssetDepartmentNodeResponse;
import com.antiy.asset.vo.response.AssetDepartmentResponse;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

import static com.antiy.biz.file.FileHelper.logger;

/**
 * <p> 资产部门信息 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetDepartmentServiceImpl extends BaseServiceImpl<AssetDepartment> implements IAssetDepartmentService {

    @Resource
    private AssetDepartmentDao                                      assetDepartmentDao;
    @Resource
    private BaseConverter<AssetDepartmentRequest, AssetDepartment>  requestConverter;
    @Resource
    private BaseConverter<AssetDepartment, AssetDepartmentResponse> responseConverter;
    @Resource
    private AesEncoder                                              aesEncoder;
    @Resource
    private AssetUserDao                                            assetUserDao;
    private static Map<String, Integer>                             parentMap = new HashMap<>();

    static {
        parentMap.put("0", 0);
    }
    @Override
    @Transactional
    public ActionResponse saveAssetDepartment(AssetDepartmentRequest request) throws Exception {
        request.setId(null);
        AssetDepartment assetDepartment = requestConverter.convert(request, AssetDepartment.class);
        checkParent(request, assetDepartment);
        assetDepartment.setGmtCreate(System.currentTimeMillis());
        Integer result = assetDepartmentDao.insert(assetDepartment);
        if (result != null && !Objects.equals(result, 0)) {
            // 写入业务日志
            LogHandle.log(assetDepartment.toString(), AssetEventEnum.ASSET_DEPARTMENT_INSERT.getName(),
                AssetEventEnum.ASSET_DEPARTMENT_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
            // 记录操作日志和运行日志
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_DEPARTMENT_INSERT.getName(), assetDepartment
                .getId(), assetDepartment.getName(), assetDepartment, BusinessModuleEnum.ASSET_USER,
                BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_DEPARTMENT_INSERT.getName() + " {}", assetDepartment.toString());
        }
        return ActionResponse.success(aesEncoder.encode(assetDepartment.getStringId(), LoginUserUtil.getLoginUser()
            .getUsername()));
    }

    private boolean checkNameRepeat(AssetDepartmentRequest request) throws Exception {
        if (StringUtils.isNotEmpty(request.getName())) {
            AssetDepartmentQuery assetDepartmentQuery = new AssetDepartmentQuery();
            assetDepartmentQuery.setName(request.getName());
            // 根据id是否为null进行不同的查询重名操作
            return assetDepartmentDao.findRepeatName(
                request.getId() == null ? null : DataTypeUtils.stringToInteger(request.getId()), request.getName()) >= 1;
        }
        return false;
    }

    @Override
    @Transactional
    public ActionResponse updateAssetDepartment(AssetDepartmentRequest request) throws Exception {
        AssetDepartment assetDepartment = requestConverter.convert(request, AssetDepartment.class);
        BusinessExceptionUtils.isTrue(!request.getId().equals(request.getParentId()), "上级部门不能为自身");
        checkParent(request, assetDepartment);
        assetDepartment.setGmtModified(System.currentTimeMillis());
        Integer result = assetDepartmentDao.update(assetDepartment);
        if (!Objects.equals(result, 0)) {
            // 写入业务日志
            LogHandle.log(assetDepartment.toString(), AssetEventEnum.ASSET_DEPAETMENT_UPDATE.getName(),
                AssetEventEnum.ASSET_DEPARTMENT_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_DEPAETMENT_UPDATE.getName(), assetDepartment
                .getId(), assetDepartment.getName(), assetDepartment, BusinessModuleEnum.ASSET_USER,
                BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_DEPAETMENT_UPDATE.getName() + " {}", assetDepartment.toString());
        }
        return ActionResponse.success(result);
    }

    private void checkParent(AssetDepartmentRequest request, AssetDepartment assetDepartment) throws Exception {
        AssetDepartment parent = assetDepartmentDao.getById(Integer.parseInt(assetDepartment.getParentId()));
        BusinessExceptionUtils.isTrue(!checkNameRepeat(request), "该部门名已存在");
        String root_parent = "0";// 根节点的父节点为0
        if (!request.getParentId().equals(root_parent)) {
            BusinessExceptionUtils.isNull(parent, "上级部门不存在");
        }
        assetDepartment.setStatus(Constants.EXISTENCE_STATUS);
    }

    @Override
    public List<AssetDepartmentResponse> findListAssetDepartment(AssetDepartmentQuery query) throws Exception {
        List<AssetDepartment> assetDepartment = assetDepartmentDao.findListAssetDepartment(query);
        return responseConverter.convert(assetDepartment, AssetDepartmentResponse.class);
    }

    private List<AssetDepartmentResponse> convert(List<AssetDepartment> list) {
        return responseConverter.convert(list, AssetDepartmentResponse.class);
    }

    @Override
    public List<AssetDepartmentResponse> findAssetDepartmentById(Integer id) throws Exception {
        return convert(recursionSearch(id));
    }

    /**
     * 递归查询出所有的部门和其子部门
     *
     * @param id 查询的部门id
     */
    private List<AssetDepartment> recursionSearch(Integer id) throws Exception {
        List<AssetDepartment> list = assetDepartmentDao.getAll();
        List<AssetDepartment> result = new ArrayList<>();
        for (AssetDepartment assetDepartment : list) {
            if (Objects.equals(assetDepartment.getId(), id)) {
                result.add(assetDepartment);
            }
        }
        recursion(result, list, id);
        return result;
    }

    /**
     * 递归查询出所有的部门和其子部门
     *
     * @param result 查询的结果集
     * @param list 查询的数据集
     * @param id 递归的参数
     */
    private void recursion(List<AssetDepartment> result, List<AssetDepartment> list, Integer id) {
        for (AssetDepartment assetDepartment : list) {
            if (Objects.equals(assetDepartment.getParentId(), Objects.toString(id))) {
                result.add(assetDepartment);
                recursion(result, list, assetDepartment.getId());
            }
        }
    }

    public Integer findCountAssetDepartment(AssetDepartmentQuery query) throws Exception {
        return assetDepartmentDao.findCount(query);
    }

    @Override
    public PageResult<AssetDepartmentResponse> findPageAssetDepartment(AssetDepartmentQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetDepartment(query), query.getCurrentPage(),
            this.findListAssetDepartment(query));
    }

    @Override
    public AssetDepartmentNodeResponse findDepartmentNode() throws Exception {
        AssetDepartmentQuery query = new AssetDepartmentQuery();
        query.setStatus(Constants.EXISTENCE_STATUS);
        query.setPageSize(Constants.ALL_PAGE);
        List<AssetDepartment> assetDepartment = assetDepartmentDao.findListAssetDepartment(query);
        NodeUtilsConverter nodeConverter = new NodeUtilsConverter();
        List<AssetDepartmentNodeResponse> assetDepartmentNodeResponses = nodeConverter.columnToNode(assetDepartment,
            AssetDepartmentNodeResponse.class);
        dealLevel(assetDepartmentNodeResponses);
        return CollectionUtils.isNotEmpty(assetDepartmentNodeResponses) ? assetDepartmentNodeResponses.get(0) : null;
    }

    private void dealLevel(List<AssetDepartmentNodeResponse> assetDepartmentNodeResponses) {
        if (CollectionUtils.isNotEmpty(assetDepartmentNodeResponses)) {
            for (AssetDepartmentNodeResponse assetDepartmentNodeResponse : assetDepartmentNodeResponses) {
                if (!parentMap.containsKey(assetDepartmentNodeResponse.getParentId())) {
                    assetDepartmentNodeResponse.setLevelType(1);
                    parentMap.put(assetDepartmentNodeResponse.getStringId(), 1);
                } else {
                    parentMap.put(assetDepartmentNodeResponse.getStringId(), parentMap.get(assetDepartmentNodeResponse.getParentId())+1);
                    assetDepartmentNodeResponse.setLevelType(parentMap.get(assetDepartmentNodeResponse.getParentId())+1);
                }
                dealLevel(assetDepartmentNodeResponse.getChildrenNode());
            }
        }
    }

    /**
     * 删除部门及子部门
     *
     * @param id
     * @return 影响的数据库行数
     * @throws Exception
     */
    @Override
    public ActionResponse deleteAllById(Serializable id) throws Exception {
        List<AssetDepartment> list = recursionSearch((Integer) id);
        BusinessExceptionUtils.isEmpty(list, "该部门不存在");
        String[] departmentIds = getDepartmentIdArray(list);
        AssetUserQuery assetUserQuery = new AssetUserQuery();
        assetUserQuery.setDepartmentIds(departmentIds);
        List<AssetUser> resultUser = assetUserDao.findListAssetUser(assetUserQuery);
        BusinessExceptionUtils.isTrue(resultUser.size() <= 0, "部门下存在关联用户，不能删除");
        AssetDepartment assetDepartmentLog = assetDepartmentDao.getById(id);
        int result = assetDepartmentDao.delete(list);
        // 写入业务日志
        LogHandle.log(list.toString(), AssetEventEnum.ASSET_DEPAETMENT_DELETE.getName(),
            AssetEventEnum.ASSET_DEPAETMENT_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_DEPAETMENT_DELETE.getName(), assetDepartmentLog.getId(), assetDepartmentLog.getName(), assetDepartmentLog,
            BusinessModuleEnum.ASSET_USER, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_DEPAETMENT_DELETE.getName() + " {}", list.toString());
        return ActionResponse.success(result >= 1 ? 1 : 0);
    }

    private String[] getDepartmentIdArray(List<AssetDepartment> list) {
        String[] array = new String[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i).getStringId();
        }
        return array;
    }

    @Override
    public String getIdByName(String name) {
        return assetDepartmentDao.getIdByName(name);
    }

    /**
     * 删除部门
     *
     * @param id
     * @return 影响的数据库行数
     * @throws Exception
     */
    public ActionResponse delete(Serializable id) throws Exception {
        List<AssetDepartment> list = new ArrayList<>();
        AssetDepartment assetDepartment = new AssetDepartment();
        assetDepartment.setId((Integer) id);
        list.add(assetDepartment);
        BusinessExceptionUtils.isEmpty(list, "该部门不存在");
        Integer result = assetDepartmentDao.delete(list);
        AssetDepartment assetDepartmentLog = assetDepartmentDao.getById(id);
        if (!Objects.equals(result, 0)) {
            // 写入业务日志
            LogHandle.log(list.toString(), AssetEventEnum.ASSET_DEPAETMENT_DELETE.getName(),
                AssetEventEnum.ASSET_DEPAETMENT_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_DEPAETMENT_DELETE.getName(), (Integer)id, assetDepartmentLog.getName(), assetDepartmentLog,
                BusinessModuleEnum.ASSET_USER, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_DEPAETMENT_DELETE.getName() + " {}", list.toString());
        }
        return ActionResponse.success(result);
    }
}

