package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetNettypeManageDao;
import com.antiy.asset.entity.AssetNettypeManage;
import com.antiy.asset.service.IAssetNettypeManageService;
import com.antiy.asset.vo.query.AssetNettypeManageQuery;
import com.antiy.asset.vo.request.AssetNettypeManageRequest;
import com.antiy.asset.vo.response.AssetNettypeManageResponse;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangqian
 * @since 2020-04-07
 */
@Service
public class AssetNettypeManageServiceImpl extends BaseServiceImpl<AssetNettypeManage> implements IAssetNettypeManageService {

    private Logger logger = LogUtils.get(this.getClass());

    @Resource
    private AssetNettypeManageDao assetNettypeManageDao;
    @Resource
    private BaseConverter<AssetNettypeManageRequest, AssetNettypeManage> requestConverter;
    @Resource
    private BaseConverter<AssetNettypeManage, AssetNettypeManageResponse> responseConverter;
    @Autowired
    private RedisUtil redisUtil;

    @PostConstruct
    public void init() throws Exception {
        List<AssetNettypeManage> all = assetNettypeManageDao.getAll();
        Map<String, Object> map = new HashMap<>();
        for (AssetNettypeManage en : all) {
            map.put(ObjectUtils.toString(en.getId()), en);
        }
        redisUtil.hmset("net-type", map);
    }

    @Override
    public String saveAssetNettypeManage(AssetNettypeManageRequest request) throws Exception {
        AssetNettypeManage assetNettypeManage = requestConverter.convert(request, AssetNettypeManage.class);
        assetNettypeManage.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetNettypeManage.setGmtCreate(System.currentTimeMillis());
        Integer count = assetNettypeManageDao.getCountByNetTypeName(null, assetNettypeManage.getNetTypeName());
        if (count > 0) {
            throw new BusinessException("网络类型名称已存在");
        }
        assetNettypeManageDao.insert(assetNettypeManage);
        redisUtil.hset("net-type", assetNettypeManage.getStringId(), assetNettypeManage);
        return assetNettypeManage.getStringId();
    }

    @Override
    public String updateAssetNettypeManage(AssetNettypeManageRequest request) throws Exception {
        AssetNettypeManage assetNettypeManage = requestConverter.convert(request, AssetNettypeManage.class);
        Integer count = assetNettypeManageDao.getCountByNetTypeName(assetNettypeManage.getId(), assetNettypeManage.getNetTypeName());
        if (count > 0) {
            throw new BusinessException("网络类型名称已存在");
        }
        assetNettypeManage.setModifiedUser(LoginUserUtil.getLoginUser().getId());
        assetNettypeManage.setGmtModified(System.currentTimeMillis());
        String id = assetNettypeManageDao.update(assetNettypeManage).toString();
        redisUtil.hset("net-type", id, assetNettypeManage);
        return id;
    }

    @Override
    public List<AssetNettypeManageResponse> queryListAssetNettypeManage(AssetNettypeManageQuery query) throws Exception {
        List<AssetNettypeManage> assetNettypeManageList = assetNettypeManageDao.findQuery(query);
        return responseConverter.convert(assetNettypeManageList, AssetNettypeManageResponse.class);
    }

    @Override
    public List<AssetNettypeManageResponse> getAllList() throws Exception {
        List<AssetNettypeManage> assetNettypeManageList = assetNettypeManageDao.getAll();
        return responseConverter.convert(assetNettypeManageList, AssetNettypeManageResponse.class);
    }

    @Override
    public Integer getIdsByName(String name) {
        return assetNettypeManageDao.findIdsByName(name);
    }

    @Override
    public PageResult<AssetNettypeManageResponse> queryPageAssetNettypeManage(AssetNettypeManageQuery query) throws Exception {
        return new PageResult<AssetNettypeManageResponse>(query.getPageSize(), this.findCount(query), query.getCurrentPage(), this.queryListAssetNettypeManage(query));
    }

    @Override
    public AssetNettypeManageResponse queryAssetNettypeManageById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetNettypeManageResponse assetNettypeManageResponse = responseConverter
                .convert(assetNettypeManageDao.getById(queryCondition.getPrimaryKey()), AssetNettypeManageResponse.class);
        return assetNettypeManageResponse;
    }

    @Override
    public String deleteAssetNettypeManageById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        String id = assetNettypeManageDao.deleteById(baseRequest.getStringId()).toString();
        redisUtil.hdel("net-type", id);
        return id;
    }
}
