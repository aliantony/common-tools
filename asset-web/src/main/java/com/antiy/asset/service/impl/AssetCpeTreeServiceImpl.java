package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetCpeTreeDao;
import com.antiy.asset.entity.AssetCpeTree;
import com.antiy.asset.service.IAssetCpeTreeService;
import com.antiy.asset.vo.query.AssetCpeTreeCondition;
import com.antiy.asset.vo.query.AssetCpeTreeQuery;
import com.antiy.asset.vo.request.AssetCpeTreeRequest;
import com.antiy.asset.vo.response.AssetCpeTreeResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.google.common.collect.Lists;

/**
 * <p> CPE过滤表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2020-03-15
 */
@Service
public class AssetCpeTreeServiceImpl extends BaseServiceImpl<AssetCpeTree> implements IAssetCpeTreeService {

    private Logger                                            logger = LogUtils.get(this.getClass());

    @Resource
    private AssetCpeTreeDao                                   assetCpeTreeDao;
    @Resource
    private BaseConverter<AssetCpeTreeRequest, AssetCpeTree>  requestConverter;
    @Resource
    private BaseConverter<AssetCpeTree, AssetCpeTreeResponse> responseConverter;

    @Override
    public String saveAssetCpeTree(AssetCpeTreeRequest request) throws Exception {
        AssetCpeTree assetCpeTree = requestConverter.convert(request, AssetCpeTree.class);
        assetCpeTreeDao.insert(assetCpeTree);
        return assetCpeTree.getStringId();
    }

    @Override
    public String updateAssetCpeTree(AssetCpeTreeRequest request) throws Exception {
        AssetCpeTree assetCpeTree = requestConverter.convert(request, AssetCpeTree.class);
        return assetCpeTreeDao.update(assetCpeTree).toString();
    }

    @Override
    public List<AssetCpeTreeResponse> queryListAssetCpeTree(AssetCpeTreeQuery query) throws Exception {
        List<AssetCpeTree> assetCpeTreeList = assetCpeTreeDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetCpeTreeList, AssetCpeTreeResponse.class);
    }

    @Override
    public PageResult<AssetCpeTreeResponse> queryPageAssetCpeTree(AssetCpeTreeQuery query) throws Exception {
        return new PageResult<AssetCpeTreeResponse>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.queryListAssetCpeTree(query));
    }

    @Override
    public AssetCpeTreeResponse queryAssetCpeTreeById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetCpeTreeResponse assetCpeTreeResponse = responseConverter
            .convert(assetCpeTreeDao.getById(queryCondition.getPrimaryKey()), AssetCpeTreeResponse.class);
        return assetCpeTreeResponse;
    }

    @Override
    public String deleteAssetCpeTreeById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetCpeTreeDao.deleteById(baseRequest.getStringId()).toString();
    }

    @Override
    public List<AssetCpeTreeResponse> querySubTreeById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetCpeTreeQuery query = new AssetCpeTreeQuery();
        query.setPid(queryCondition.getPrimaryKey());
        List<AssetCpeTree> cpeTreeList;
        List<AssetCpeTree> allList = Lists.newArrayList();

        Integer num = assetCpeTreeDao.findCount(query);

        if (num > 0) {
            cpeTreeList = assetCpeTreeDao.findQuery(query);
            for (AssetCpeTree cpeTree : cpeTreeList) {
                recursionTree(new AssetCpeTreeQuery(), cpeTree, allList);
            }
        }

        return responseConverter.convert(allList, AssetCpeTreeResponse.class);
    }

    private void recursionTree(AssetCpeTreeQuery query, AssetCpeTree assetCpeTree,
                               List<AssetCpeTree> allList) throws Exception {
        query.setPid(assetCpeTree.getUniqueId());
        List<AssetCpeTree> cpeTreeList = assetCpeTreeDao.findQuery(query);
        for (AssetCpeTree cpeTree : cpeTreeList) {
            allList.add(cpeTree);
            recursionTree(query, cpeTree, allList);
        }
    }

    @Override
    public List<AssetCpeTreeResponse> queryNextNodeById(AssetCpeTreeCondition condition) throws Exception {
        Integer num = assetCpeTreeDao.countNextNode(condition);
        if (num > 0) {
            return responseConverter.convert(assetCpeTreeDao.findNextNode(condition), AssetCpeTreeResponse.class);
        } else {
            return Lists.newArrayList();
        }
    }
}
