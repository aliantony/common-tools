package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetHardAssemblyRelationService;
import com.antiy.asset.vo.request.AssetHardAssemblyRelationRequest;
import com.antiy.asset.vo.response.AssetHardAssemblyRelationResponse;
import com.antiy.asset.vo.query.AssetHardAssemblyRelationQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetHardAssemblyRelation", description = "硬件与组件关系表")
@RestController
@RequestMapping("/api/v1/asset/assethardassemblyrelation")
public class AssetHardAssemblyRelationController {

}
