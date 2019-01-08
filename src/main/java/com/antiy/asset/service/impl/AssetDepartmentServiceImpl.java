package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDepartmentDao;
import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.NodeUtilsConverter;
import com.antiy.asset.vo.query.AssetDepartmentQuery;
import com.antiy.asset.vo.request.AssetDepartmentRequest;
import com.antiy.asset.vo.response.AssetDepartmentNodeResponse;
import com.antiy.asset.vo.response.AssetDepartmentResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @Override
    public Integer saveAssetDepartment(AssetDepartmentRequest request) throws Exception {
        AssetDepartment assetDepartment = requestConverter.convert(request, AssetDepartment.class);
        return assetDepartmentDao.insert(assetDepartment);
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
        List<AssetDepartmentResponse> result = new ArrayList<>();
        HashMap map = new HashMap();
        map.put("parent_id", id);
        List<AssetDepartment> list = new ArrayList<>();
        list.add(assetDepartmentDao.getById(id));
        recursion(result,list);
        return result;
    }

    /**
     * 递归查询出所有的部门和其子部门
     * @param result 查询的结果集
     * @param assetDepartments 递归的参数
     */
    private void recursion(List<AssetDepartmentResponse> result, List<AssetDepartment> assetDepartments) {
        if (assetDepartments.size() != 0) {
            for (int i = 0; i < assetDepartments.size(); i++) {
                AssetDepartment assetDepartment = assetDepartments.get(i);
                result.add(convert(assetDepartment));
                HashMap map = new HashMap();
                map.put("parentId", assetDepartment.getId());
                try {
                    recursion(result, assetDepartmentDao.getByWhere(map));
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
}
