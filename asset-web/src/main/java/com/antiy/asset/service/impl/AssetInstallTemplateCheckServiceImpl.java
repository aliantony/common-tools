package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetInstallTemplateCheckDao;
import com.antiy.asset.entity.AssetInstallTemplateCheck;
import com.antiy.asset.service.IAssetInstallTemplateCheckService;
import com.antiy.asset.vo.enums.AssetInstallTemplateCheckStautsEnum;
import com.antiy.asset.vo.query.AssetInstallTemplateCheckQuery;
import com.antiy.asset.vo.request.AssetInstallTemplateCheckRequest;
import com.antiy.asset.vo.response.AssetInstallTemplateCheckResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 装机模板审核表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */
@Service
public class AssetInstallTemplateCheckServiceImpl extends BaseServiceImpl<AssetInstallTemplateCheck>
                                                  implements IAssetInstallTemplateCheckService {

    private Logger                                                                      logger = LogUtils
        .get(this.getClass());

    @Resource
    private AssetInstallTemplateCheckDao                                                assetInstallTemplateCheckDao;
    @Resource
    private BaseConverter<AssetInstallTemplateCheckRequest, AssetInstallTemplateCheck>  requestConverter;
    @Resource
    private BaseConverter<AssetInstallTemplateCheck, AssetInstallTemplateCheckResponse> responseConverter;
    @Resource
    private RedisUtil redisUtil;
    @Override
    public String saveAssetInstallTemplateCheck(AssetInstallTemplateCheckRequest request) throws Exception {
        AssetInstallTemplateCheck assetInstallTemplateCheck = requestConverter.convert(request,
            AssetInstallTemplateCheck.class);
        assetInstallTemplateCheckDao.insert(assetInstallTemplateCheck);
        return assetInstallTemplateCheck.getStringId();
    }

    @Override
    public String updateAssetInstallTemplateCheck(AssetInstallTemplateCheckRequest request) throws Exception {
        AssetInstallTemplateCheck assetInstallTemplateCheck = requestConverter.convert(request,
            AssetInstallTemplateCheck.class);
        return assetInstallTemplateCheckDao.update(assetInstallTemplateCheck).toString();
    }

    @Override
    public List<AssetInstallTemplateCheckResponse> queryListAssetInstallTemplateCheck(AssetInstallTemplateCheckQuery query) throws Exception {
        List<AssetInstallTemplateCheck> assetInstallTemplateCheckList = assetInstallTemplateCheckDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetInstallTemplateCheckList, AssetInstallTemplateCheckResponse.class);
    }

    @Override
    public PageResult<AssetInstallTemplateCheckResponse> queryPageAssetInstallTemplateCheck(AssetInstallTemplateCheckQuery query) throws Exception {
        return new PageResult<AssetInstallTemplateCheckResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetInstallTemplateCheck(query));
    }

    @Override
    public AssetInstallTemplateCheckResponse queryAssetInstallTemplateCheckById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetInstallTemplateCheckResponse assetInstallTemplateCheckResponse = responseConverter.convert(
            assetInstallTemplateCheckDao.getById(queryCondition.getPrimaryKey()),
            AssetInstallTemplateCheckResponse.class);
        return assetInstallTemplateCheckResponse;
    }

    @Override
    public String deleteAssetInstallTemplateCheckById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetInstallTemplateCheckDao.deleteById(baseRequest.getStringId()).toString();
    }
    @Override
    public List<AssetInstallTemplateCheckResponse> queryTemplateCheckByTemplateId(QueryCondition queryCondition) throws Exception {

        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "装机模板Id不能为空");
        List<AssetInstallTemplateCheck> installTemplateCheckList = assetInstallTemplateCheckDao.queryTemplateCheckByTemplateId(queryCondition.getPrimaryKey());
        List<AssetInstallTemplateCheckResponse> responseList = responseConverter.convert(installTemplateCheckList, AssetInstallTemplateCheckResponse.class);
        for(int i=0;i<installTemplateCheckList.size();i++){
            AssetInstallTemplateCheck templateCheck=installTemplateCheckList.get(i);

            Integer userId=templateCheck.getUserId();
            Integer result = templateCheck.getResult();
            //从redis上获取用户信息
            String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysUser.class,
                    userId);
            SysUser sysUser = redisUtil.getObject(key, SysUser.class);
            AssetInstallTemplateCheckResponse templateCheckResponse=responseList.get(i);
            if(sysUser==null){
                logger.info("该用户不存在");
                templateCheckResponse.setName("");
            }else{
                templateCheckResponse.setName(sysUser.getName());
            }
            AssetInstallTemplateCheckStautsEnum currentStatus = AssetInstallTemplateCheckStautsEnum.getStatusEnumByCode(templateCheckResponse.getResult());
            if(currentStatus==null){
                logger.info("无效状态");
            }else{
                templateCheckResponse.setResultStr(currentStatus.getMsg());
            }


        }
        return responseList;
    }
}
