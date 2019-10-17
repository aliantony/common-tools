package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetBaselineFileService;
import com.antiy.asset.vo.query.AssetBaselineFileQuery;
import com.antiy.asset.vo.response.AssetBaselineFileResponse;
import com.antiy.common.base.ActionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhouye
 * 资产-检查-结果验证流程关联资产是否已上传结果附件
 */
@Api(value = "BaselineFile", description = "资产流程操作是否上传相关附件")
@RestController
@RequestMapping("/api/v1/asset/baselineFile")
public class AssetBaselineFileController {
	@Resource
	private IAssetBaselineFileService service;
	@ApiOperation(value = "资产流程操作是否上传相关附件查询接口", notes = "传入实体对象信息")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetBaselineFileResponse.class)})
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	ActionResponse<List<AssetBaselineFileResponse>> queryBaselineFileIsExist(@RequestBody AssetBaselineFileQuery query) {
		return service.queryBaselineFileIsExist(query.getIds(), query.getType());

	}
}
