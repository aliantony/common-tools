package com.antiy.asset.controller;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.enums.AssetStatusEnum;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.asset.vo.templet.AssetEntity;
import com.antiy.asset.vo.templet.ImportResult;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "Asset", description = "资产主表")
@RestController
@RequestMapping("/v1/asset")
public class AssetController {

    @Resource
    public IAssetService iAssetService;

    /**
     * 保存
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "asset") AssetRequest asset) throws Exception {
        iAssetService.saveAsset(asset);
        return ActionResponse.success();
    }

    /**
     * 修改
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.PUT)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "asset") AssetRequest asset) throws Exception {
        iAssetService.updateAsset(asset);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "asset") AssetQuery asset) throws Exception {
        return ActionResponse.success(iAssetService.findPageAsset(asset));
    }

    /**
     * 通过ID查询
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "asset") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetService.getById(Integer.parseInt(query.getPrimaryKey())));
    }

    /**
     * 通过ID删除
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/delete/id", method = RequestMethod.DELETE)
    public ActionResponse deleteById(@ApiParam(value = "query") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetService.deleteById(Integer.parseInt(query.getPrimaryKey())));
    }

    /**
     * 通过ID删除
     *
     * @param assetQuery 封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "根据条件导出硬件信息", notes = "主键封装对象", produces = "application/octet-stream")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/export/file", method = RequestMethod.GET)
    public void export(@ApiParam(value = "query") AssetQuery assetQuery, ServletResponse response) throws Exception {
        List<AssetResponse> list = iAssetService.findListAsset(assetQuery);
        List entities = BeanConvert.convert(list, AssetEntity.class);
        ExcelUtils.exportToClient(AssetEntity.class, "硬件信息表.xls", "硬件信息", entities);
    }

    /**
     * 通过ID删除
     *
     * @param assetQuery 封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "根据条件导出硬件信息", notes = "主键封装对象", produces = "application/octet-stream")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/export/template", method = RequestMethod.GET)
    public void exportTemplate(@ApiParam(value = "query") AssetQuery assetQuery, ServletResponse response) throws Exception {
        ExcelUtils.exportToClient(AssetEntity.class, "硬件信息表.xls", "硬件信息", null);
    }



    /**
     * 导入文件
     *
     * @param multipartFile
     * @return actionResponse
     */
    @ApiOperation(value = "导入文件", notes = "主键封装对象")
    @RequestMapping(value = "/import/file", method = RequestMethod.POST)
    public ActionResponse exportFile(@ApiParam(value = "multipartFile") MultipartFile multipartFile) throws Exception {
        ImportResult importResult = ExcelUtils.importExcelFromClient(AssetEntity.class, multipartFile, 1, 0);
        List<?> list = importResult.getDataList();
        List<Object> asset = BeanConvert.convert(list, Asset.class);
        Integer successNum = iAssetService.batchSave(transferList(asset));
        Boolean success = successNum == asset.size();
        if (success) {
            return ActionResponse.success();
        } else {
            return ActionResponse.fail(RespBasicCode.ERROR, "失败了" + (asset.size() - successNum) + "条数据");
        }
    }

    private List<Asset> transferList(List<Object> objects) {
        List<Asset> assets = new ArrayList<>();
        objects.forEach(x -> assets.add((Asset) x));
        return assets;
    }

    /**
     * 批量修改资产状态
     *
     * @param ids
     * @param targetStatus
     * @return actionResponse
     */
    @ApiOperation(value = "批量修改资产状态接口", notes = "传入资产状态和资产ID数组")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/changeStatus", method = RequestMethod.POST)
    public ActionResponse changeStatus(@ApiParam(value = "资产ID数组") @RequestParam Integer[] ids, @ApiParam(value = "资产新状态") @RequestParam("targetStatus")Integer targetStatus) throws Exception {
        //资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6-已入网，7-待退役，8-已退役
        boolean isChange = false;
        if (ids != null && ids.length > 0){
            for (Integer id : ids){
                Asset asset = iAssetService.getById(id);
                Integer currStatus = asset.getAssetStatus();
                if (currStatus < AssetStatusEnum.WAIT_SETTING.getCode() && targetStatus.equals(AssetStatusEnum.WAIT_SETTING.getCode())){
                    return ActionResponse.fail (RespBasicCode.BUSSINESS_EXCETION,"请先完成资产登记！");
                }else if (currStatus < AssetStatusEnum.WAIT_VALIDATE.getCode() && targetStatus.equals(AssetStatusEnum.WAIT_VALIDATE.getCode())){
                    //TODO 配置验证是否通过
                    return ActionResponse.fail (RespBasicCode.BUSSINESS_EXCETION,"请先完成资产配置！");
                }else if (currStatus < AssetStatusEnum.WAIT_NET.getCode() && targetStatus.equals(AssetStatusEnum.WAIT_NET.getCode())){
                    //TODO 资产验证是否通过
                    return ActionResponse.fail (RespBasicCode.BUSSINESS_EXCETION,"请先完成资产验证！");
                }else if (currStatus < AssetStatusEnum.NET_IN.getCode() && targetStatus.equals(AssetStatusEnum.NET_IN.getCode())){
                    if (currStatus.equals(AssetStatusEnum.WAIT_NET.getCode())){
                        return ActionResponse.fail (RespBasicCode.BUSSINESS_EXCETION,"当前资产状态不是待入网，禁止入网！");
                    }
                }else if (currStatus < AssetStatusEnum.WAIT_RETIRE.getCode() && targetStatus.equals(AssetStatusEnum.WAIT_RETIRE.getCode())){
                    //TODO 资产是否登记
                    if (currStatus < AssetStatusEnum.WAIT_SETTING.getCode()){
                        return ActionResponse.fail (RespBasicCode.BUSSINESS_EXCETION,"资产未登记，不能退役！");
                    }
                    //TODO 是否有关联资产

                }else if (currStatus < AssetStatusEnum.RETIRE.getCode() && targetStatus.equals(AssetStatusEnum.RETIRE.getCode())){
                    //TODO 资产是否待退役
                    if (currStatus.equals(AssetStatusEnum.WAIT_RETIRE.getCode())){
                        return ActionResponse.fail (RespBasicCode.BUSSINESS_EXCETION,"只有待退役得资产才能执行退役操作！");
                    }
                }else if (targetStatus.equals(currStatus)){
                    //
                }else if (targetStatus < currStatus){
                    isChange = true;
                }
//                if (currStatus.equals(AssetStatusEnum.RETIRE) && targetStatus < currStatus){
//                    throw new Exception("退役资产禁止修改状态！");
//                }
            }
        }
        if (isChange){
            iAssetService.changeStatus(ids,targetStatus);
            return ActionResponse.success();
        }else {
            return ActionResponse.fail (RespBasicCode.BUSSINESS_EXCETION,"非法的资产状态变更操作！");
        }
    }
}

