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

    private Logger logger = LogUtils.get(this.getClass());

    @Resource
    private AssetCpeTreeDao assetCpeTreeDao;
    @Resource
    private BaseConverter<AssetCpeTreeRequest, AssetCpeTree> requestConverter;
    @Resource
    private BaseConverter<AssetCpeTree, AssetCpeTreeResponse> responseConverter;
    @Resource
    private BaseConverter<AssetCpeTreeResponse, AssetCpeTree> responseReverseConverter;

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
            allList.addAll(cpeTreeList);
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

    @Override
    public List<AssetCpeTreeResponse> queryTree(String isCommmon) throws Exception {

        List<AssetCpeTreeResponse> responseList = Lists.newArrayList();
        // 获取顶级大类
        AssetCpeTreeCondition condition = new AssetCpeTreeCondition();
        condition.setIsCommon(isCommmon);
        List<AssetCpeTree> topNodeList = assetCpeTreeDao.findOSTopNode(condition);
        for (AssetCpeTree assetCpeTree : topNodeList) {
            condition.setPid(assetCpeTree.getUniqueId());
            List<AssetCpeTreeResponse> treeItem = responseConverter.convert(assetCpeTreeDao.findNextNode(condition),
                    AssetCpeTreeResponse.class);
            // 查找子节点数据
            buildChildrenNode(treeItem, isCommmon, true);
            // 组装基准大类树形数据
            AssetCpeTreeResponse assetCpeTreeResponse = responseConverter.convert(assetCpeTree,
                    AssetCpeTreeResponse.class);
            assetCpeTreeResponse.setShow(true);
            assetCpeTreeResponse.setChildrenNode(treeItem);
            responseList.add(assetCpeTreeResponse);
        }
        return responseList;
    }

    /**
     * 根据节点名获取对应的UniqueId
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public String queryUniqueIdByNodeName(AssetCpeTreeRequest request) throws Exception {
        ParamterExceptionUtils.isBlank(request.getTitle(), "节点名为空!");
        return assetCpeTreeDao.queryUniqueIdByNodeName(request.getTitle());
    }

    @Override
    public List<String> queryOsNameList() throws Exception {
        return assetCpeTreeDao.getOsNameList();
    }

    @Override
    public List<AssetCpeTreeResponse> queryAssetOs() throws Exception {
        List<AssetCpeTreeResponse> responseList = Lists.newArrayList();
        // 获取顶级大类
        AssetCpeTreeCondition condition = new AssetCpeTreeCondition();
        condition.setIsCommon("0");
        List<AssetCpeTree> topNodeList = assetCpeTreeDao.findOSTopNode(condition);
        for (AssetCpeTree assetCpeTree : topNodeList) {
            condition.setPid(assetCpeTree.getUniqueId());
            List<AssetCpeTreeResponse> treeItem = responseConverter.convert(assetCpeTreeDao.findNextNode(condition),
                    AssetCpeTreeResponse.class);
            // 查找子节点数据
            buildChildrenNode(treeItem, "0", false);
            // 组装基准大类树形数据
            AssetCpeTreeResponse assetCpeTreeResponse = responseConverter.convert(assetCpeTree,
                    AssetCpeTreeResponse.class);
            assetCpeTreeResponse.setShow(true);
            assetCpeTreeResponse.setChildrenNode(treeItem);
            responseList.add(assetCpeTreeResponse);
        }
        return responseList;
    }

    private void buildChildrenNode(List<AssetCpeTreeResponse> cpeTreeResponseList, String isCommon, boolean isContinue) {
        AssetCpeTreeCondition condition = new AssetCpeTreeCondition();
        condition.setIsCommon(isCommon);
        for (AssetCpeTreeResponse assetCpeTree : cpeTreeResponseList) {
            condition.setPid(assetCpeTree.getUniqueId());
            Integer hasNext = assetCpeTreeDao.countNextNode(condition);
            if (hasNext > 0) {
                assetCpeTree.setShow(false);
                List<AssetCpeTree> assetCpeTreeList = assetCpeTreeDao.findNextNode(condition);
                List<AssetCpeTreeResponse> responseList = responseConverter.convert(assetCpeTreeList,
                        AssetCpeTreeResponse.class);
                assetCpeTree.setChildrenNode(responseList);
                if (isContinue) {
                    buildChildrenNode(assetCpeTree.getChildrenNode(), isCommon, isContinue);
                }
            }
        }
    }
}
