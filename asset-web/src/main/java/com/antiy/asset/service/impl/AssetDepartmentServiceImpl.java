package com.antiy.asset.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.antiy.asset.convert.NodeConverter;
import com.antiy.common.base.*;
import com.antiy.common.utils.LogUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDepartmentDao;
import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.vo.query.AssetDepartmentQuery;
import com.antiy.asset.vo.request.AssetDepartmentRequest;
import com.antiy.asset.vo.response.AssetDepartmentNodeResponse;
import com.antiy.asset.vo.response.AssetDepartmentResponse;

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
    private AssetUserDao                                            assetUserDao;

    @Override
    public ActionResponse saveAssetDepartment(AssetDepartmentRequest request) throws Exception {
        AssetDepartment assetDepartment = requestConverter.convert(request, AssetDepartment.class);
        AssetDepartment parent = assetDepartmentDao.getById(assetDepartment.getParentId());
        if (checkNameRepeat(request)) {
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "该品类名已存在");
        }
        if (Objects.isNull(parent)) {
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "父级部门不存在");
        }
        assetDepartment.setStatus(1);
        assetDepartment.setGmtCreate(System.currentTimeMillis());
        assetDepartmentDao.insert(assetDepartment);
        return ActionResponse.success(assetDepartment.getId());
    }

    boolean checkNameRepeat(AssetDepartmentRequest request) throws Exception {
        if (Objects.nonNull(request.getName())) {
            AssetDepartmentQuery assetDepartmentQuery = new AssetDepartmentQuery();
            assetDepartmentQuery.setName(request.getName());
            return assetDepartmentDao.findCount(assetDepartmentQuery) >= 1;
        }
        return false;
    }

    @Override
    public ActionResponse updateAssetDepartment(AssetDepartmentRequest request) throws Exception {
        AssetDepartment assetDepartment = requestConverter.convert(request, AssetDepartment.class);
        if (checkNameRepeat(request)) {
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "该品类名已存在");
        }
        assetDepartment.setParentId(null);
        assetDepartment.setStatus(1);
        assetDepartment.setGmtModified(System.currentTimeMillis());
        return ActionResponse.success(assetDepartmentDao.update(assetDepartment));
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
        List<AssetDepartment> result = new ArrayList();
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
            if (Objects.equals(assetDepartment.getParentId(), id)) {
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
        query.setStatus(1);
        List<AssetDepartment> assetDepartment = assetDepartmentDao.findListAssetDepartment(query);
        NodeConverter nodeConverter = new NodeConverter();
        List<AssetDepartmentNodeResponse> assetDepartmentNodeResponses = nodeConverter.columnToNode(assetDepartment,
            AssetDepartmentNodeResponse.class);
        return CollectionUtils.isNotEmpty(assetDepartmentNodeResponses) ? assetDepartmentNodeResponses.get(0) : null;
    }

    /**
     * 删除部门及子部门，并将这些部门下的人员部门信息置为null
     *
     * @param id
     * @return 影响的数据库行数
     * @throws Exception
     */
    public ActionResponse deleteAllById(Serializable id) throws Exception {
        List<AssetDepartment> list = recursionSearch((Integer) id);
        if (CollectionUtils.isNotEmpty(list)) {
            return ActionResponse.success(assetDepartmentDao.delete(list));
        } else {
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "该部门不存在");
        }
    }

    /**
     * 删除部门，并将这些部门下的人员部门信息置为null
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
        if (CollectionUtils.isNotEmpty(list)) {
            return ActionResponse.success(assetDepartmentDao.delete(list));
        } else {
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "该部门不存在");
        }
    }
}

@Component
class DepartmentRequestConvert extends BaseConverter<AssetDepartmentRequest, AssetDepartment> {
    Logger logger = LogUtils.get(DepartmentRequestConvert.class);

    @Override
    protected void convert(AssetDepartmentRequest assetDepartmentRequest, AssetDepartment assetDepartment) {
        try {
            assetDepartment.setId(Integer.parseInt(assetDepartmentRequest.getId()));
        } catch (Exception e) {
            logger.error("String转Integer出错");
        }
        super.convert(assetDepartmentRequest, assetDepartment);
    }
}
