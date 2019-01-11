package com.antiy.asset.controller;

import javax.annotation.Resource;

import com.antiy.asset.vo.enums.TopologyTypeEnum;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.ITopologyService;
import com.antiy.asset.vo.request.TopologyRequest;
import com.antiy.asset.vo.response.TopologyResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @Auther: zhangbing
 * @Date: 2019/1/2 14:08
 * @Description: 网络拓扑
 */
@Api(value = "Scheme", description = "网络拓扑")
@RestController
@RequestMapping("/v1/asset/topology")
public class TopologyController {

    @Resource
    private ITopologyService iTopologyService;

    /**
     * 初始化拓扑请求数据
     *
     * @return actionResponse
     */
    @ApiOperation(value = "查询网络拓扑初始化信息", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = TopologyResponse.class) })
    @RequestMapping(value = "/query/init", method = RequestMethod.GET)
    public ActionResponse queryTopologyInit() throws Exception {
        return ActionResponse.success(iTopologyService.queryTopologyInit());
    }

    /**
     * 保存拓扑信息,全部传入
     *
     * @param topologyRequest
     * @return
     */
    @ApiOperation(value = "保存网络拓扑信息", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class) })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ActionResponse saveTopology(@RequestBody @ApiParam(value = "网络拓扑保存信息") TopologyRequest topologyRequest) throws Exception {
        return ActionResponse.success(iTopologyService.saveTopology(topologyRequest));
    }

    /**
     * 查询网络拓扑信息
     *
     * @return
     */
    @ApiOperation(value = "查询网络拓扑信息", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class) })
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ActionResponse<String> queryTopology(String topologyType) throws Exception {
        ParamterExceptionUtils.isNull(TopologyTypeEnum.getTopologyByName(topologyType), "拓扑类型错误");
        return ActionResponse
            .success(iTopologyService.queryTopology(TopologyTypeEnum.getTopologyByName(topologyType).getCode()));
    }
}
