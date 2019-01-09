package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDepartmentDao;
import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.NodeUtilsConverter;
import com.antiy.asset.vo.query.AssetDepartmentQuery;
import com.antiy.asset.vo.request.AssetDepartmentRequest;
import com.antiy.asset.vo.response.AssetDepartmentNodeResponse;
import com.antiy.asset.vo.response.AssetDepartmentResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资产部门信息 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetDepartmentServiceImpl extends BaseServiceImpl<AssetDepartment> implements IAssetDepartmentService {


    @Resource
    private AssetDepartmentDao assetDepartmentDao;
    @Resource
    private BaseConverter<AssetDepartmentRequest, AssetDepartment> requestConverter;
    @Resource
    private BaseConverter<AssetDepartment, AssetDepartmentResponse> responseConverter;
    @Resource
    private AssetUserDao assetUserDao;

    @Override
    public Integer saveAssetDepartment(AssetDepartmentRequest request) throws Exception {
        AssetDepartment assetDepartment = requestConverter.convert(request, AssetDepartment.class);
        assetDepartmentDao.insert(assetDepartment);
        return assetDepartment.getId();
    }



    private void updateUserDepartment(Integer id) throws Exception {
        HashMap map = new HashMap();
        map.put("departmentId", id);
        List<AssetUser> list = assetUserDao.getByWhere(map);
        for (AssetUser assetUser : list) {
            assetUser.setDepartmentId(null);
            assetUserDao.setDepartmentIdNull(assetUser);
        }
    }

    @Override
    public Integer updateAssetDepartment(AssetDepartmentRequest request) throws Exception {
        AssetDepartment assetDepartment = requestConverter.convert(request, AssetDepartment.class);
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
            if (assetDepartment.getId() == id)
                result.add(assetDepartment);
        }
        recursion(result, list, id);
        return result;
    }

    /**
     * 递归查询出所有的部门和其子部门
     *
     * @param result 查询的结果集
     * @param list   查询的数据集
     * @param id     递归的参数
     */
    private void recursion(List<AssetDepartment> result, List<AssetDepartment> list, Integer id) {
        for (AssetDepartment assetDepartment : list) {
            if (assetDepartment.getParentId() == id) {
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
        NodeUtilsConverter nodeResponseNodeUtilsConverter = new NodeUtilsConverter<>();
        List<AssetDepartmentNodeResponse> assetDepartmentNodeResponses = nodeResponseNodeUtilsConverter
                .columnToNode(assetDepartment, AssetDepartmentNodeResponse.class);
        return CollectionUtils.isNotEmpty(assetDepartmentNodeResponses) ? assetDepartmentNodeResponses.get(0) : null;
    }

    @Override
    public Integer deleteById(Serializable id) throws Exception {
        List<AssetDepartment> idList = recursionSearch((Integer) id);
        if (idList != null && idList.size() > 0) {
            Map map = new HashMap();
            map.put("idList", idList);
            return assetDepartmentDao.delete(map);
        } else {
            return 0;
        }
    }
}
