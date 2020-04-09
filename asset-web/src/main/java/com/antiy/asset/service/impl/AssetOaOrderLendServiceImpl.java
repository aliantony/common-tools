package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetOaOrderLendDao;
import com.antiy.asset.entity.AssetOaOrderLend;
import com.antiy.asset.service.IAssetOaOrderLendService;
import com.antiy.asset.vo.query.AssetOaOrderLendQuery;
import com.antiy.asset.vo.request.AssetOaOrderLendRequest;
import com.antiy.asset.vo.response.AssetOaOrderLendResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 出借订单拒绝表 服务实现类
 * </p>
 *
 * @author shenliang
 * @since 2020-04-09
 */
@Service
public class AssetOaOrderLendServiceImpl extends BaseServiceImpl<AssetOaOrderLend> implements IAssetOaOrderLendService {

        private static final Logger logger = LogUtils.get();

        @Resource
        private AssetOaOrderLendDao assetOaOrderRefuseDao;
        @Resource
        private BaseConverter<AssetOaOrderLendRequest, AssetOaOrderLend>  requestConverter;
        @Resource
        private BaseConverter<AssetOaOrderLend, AssetOaOrderLendResponse> responseConverter;

        @Override
        public Integer saveAssetOaOrderRefuse(AssetOaOrderLendRequest request) throws Exception {
            AssetOaOrderLend assetOaOrderRefuse = requestConverter.convert(request, AssetOaOrderLend.class);
            assetOaOrderRefuseDao.insert(assetOaOrderRefuse);
            return assetOaOrderRefuse.getId();
        }

        @Override
        public Integer updateAssetOaOrderRefuse(AssetOaOrderLendRequest request) throws Exception {
            AssetOaOrderLend assetOaOrderRefuse = requestConverter.convert(request, AssetOaOrderLend.class);
            return assetOaOrderRefuseDao.update(assetOaOrderRefuse);
        }

        @Override
        public List<AssetOaOrderLendResponse> findListAssetOaOrderRefuse(AssetOaOrderLendQuery query) throws Exception {
            List<AssetOaOrderLend> assetOaOrderRefuseList = assetOaOrderRefuseDao.findQuery(query);
            //TODO
            List<AssetOaOrderLendResponse> assetOaOrderRefuseResponse = responseConverter.convert(assetOaOrderRefuseList, AssetOaOrderLendResponse.class );
            return assetOaOrderRefuseResponse;
        }

        @Override
        public PageResult<AssetOaOrderLendResponse> findPageAssetOaOrderRefuse(AssetOaOrderLendQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCount(query),query.getCurrentPage(), this.findListAssetOaOrderRefuse(query));
        }
}
