package com.antiy.asset.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.vo.query.AssetUserQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDepartmentDao;
import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.util.NodeUtilsConverter;
import com.antiy.asset.vo.query.AssetDepartmentQuery;
import com.antiy.asset.vo.request.AssetDepartmentRequest;
import com.antiy.asset.vo.response.AssetDepartmentNodeResponse;
import com.antiy.asset.vo.response.AssetDepartmentResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;

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
    public Integer saveAssetDepartment(AssetDepartmentRequest request) throws Exception {
        AssetDepartment assetDepartment = requestConverter.convert(request, AssetDepartment.class);
        assetDepartment.setStatus(1);
        assetDepartment.setGmtCreate(System.currentTimeMillis());
        assetDepartmentDao.insert(assetDepartment);
        return assetDepartment.getId();
    }

    @Override
    public Integer updateAssetDepartment(AssetDepartmentRequest request) throws Exception {
        AssetDepartment assetDepartment = requestConverter.convert(request, AssetDepartment.class);
        assetDepartment.setParentId(null);
        assetDepartment.setStatus(1);
        assetDepartment.setGmtCreate(System.currentTimeMillis());
        return assetDepartmentDao.update(assetDepartment);
    }

    @Override
    public List<AssetDepartmentResponse> findListAssetDepartment(AssetDepartmentQuery query) throws Exception {
        List<AssetDepartment> assetDepartment = assetDepartmentDao.findListAssetDepartment(query);
        return responseConverter.convert(assetDepartment, AssetDepartmentResponse.class);
    }

    private List<AssetDepartmentResponse> convert(List<AssetDepartment> list) {
        return responseConverter.convert(list, AssetDepartmentResponse.class);
    }

    private AssetDepartmentResponse convert(AssetDepartment assetDepartment) {
        return responseConverter.convert(assetDepartment, AssetDepartmentResponse.class);
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
            if (Objects.equals(assetDepartment.getId(), id) )
                result.add(assetDepartment);
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
            if (Objects.equals(assetDepartment.getParentId(),id) ) {
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

    /**
     *
     * @param id
     * @param isConfirm 是否二次确认删除
     * @return -1表示存在子部门或人员，需要确认，>0表示影响的部门数和人数
     * @throws Exception
     */
    @Override
    public Integer delete(Serializable id, boolean isConfirm) throws Exception {
        if (isConfirm) {
            return confirmDelete((Integer) id);
        }
        return notConfirmDelete((Integer) id);

    }

    Integer confirmDelete(Integer id) throws Exception {
        return deleteAllById(id);
    }

    Integer notConfirmDelete(Integer id) throws Exception {
        List<AssetDepartment> list = recursionSearch((Integer) id);
        if (list.size() > 1) {
            return -1;
        } else {
            AssetUserQuery assetUserQuery = new AssetUserQuery();
            assetUserQuery.setDepartmentId(id);
            Integer count = assetUserDao.findListCount(assetUserQuery);
            if (count > 0) {
                return -1;
            } else {
                return deleteById(id);
            }
        }
    }

    @Override
    public AssetDepartmentNodeResponse findDepartmentNode() throws Exception {
        AssetDepartmentQuery query = new AssetDepartmentQuery();
        query.setStatus(1);
        List<AssetDepartment> assetDepartment = assetDepartmentDao.findListAssetDepartment(query);
        NodeUtilsConverter nodeResponseNodeUtilsConverter = new NodeUtilsConverter<>();
        List<AssetDepartmentNodeResponse> assetDepartmentNodeResponses = nodeResponseNodeUtilsConverter
            .columnToNode(assetDepartment, AssetDepartmentNodeResponse.class);
        return CollectionUtils.isNotEmpty(assetDepartmentNodeResponses) ? assetDepartmentNodeResponses.get(0) : null;
    }

    /**
     * 删除部门及子部门，并将这些部门下的人员部门信息置为null
     *
     * @param id
     * @return 影响的数据库行数
     * @throws Exception
     */
    public Integer deleteAllById(Serializable id) throws Exception {
        List<AssetDepartment> list = recursionSearch((Integer) id);
        if (CollectionUtils.isNotEmpty(list)) {
            return assetDepartmentDao.delete(list);
        } else {
            return 0;
        }
    }

    /**
     * 删除部门，并将这些部门下的人员部门信息置为null
     *
     * @param id
     * @return 影响的数据库行数
     * @throws Exception
     */
    public Integer deleteById(Serializable id) throws Exception {
        List<AssetDepartment> list = new ArrayList<>();
        AssetDepartment assetDepartment = new AssetDepartment();
        assetDepartment.setId((Integer) id);
        list.add(assetDepartment);
        if (CollectionUtils.isNotEmpty(list)) {
            return assetDepartmentDao.delete(list);
        } else {
            return 0;
        }
    }
}
