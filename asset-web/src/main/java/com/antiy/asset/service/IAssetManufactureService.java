package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetManufacture;
import com.antiy.asset.vo.query.AssetManufactureQuery;
import com.antiy.asset.vo.request.AssetManufactureRequest;
import com.antiy.asset.vo.response.AssetManufactureResponse;
import com.antiy.asset.vo.response.AssetManufactureTreeResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

/**
 * <p> 安全厂商表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2020-03-05
 */
public interface IAssetManufactureService extends IBaseService<AssetManufacture> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetManufacture(AssetManufactureRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetManufacture(AssetManufactureRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetManufactureResponse> queryListAssetManufacture(AssetManufactureQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetManufactureResponse> queryPageAssetManufacture(AssetManufactureQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetManufactureResponse queryAssetManufactureById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetManufactureById(BaseRequest baseRequest) throws Exception;

    /**
     * 树形厂商数据
     * @return
     */
    List<AssetManufactureTreeResponse> safetyManufacture() throws Exception;

    /**
     * 树形厂商数据
     * @return
     */
    // List<String> queryDeviceById() throws Exception;

}
