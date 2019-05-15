package com.antiy.asset.vo.request;

import java.util.List;

import com.antiy.asset.dto.AssetDTO;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.common.validation.ObjectValidator;

/**
 * <p> 给智甲的软件数据请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class SoftwareInstallRequest implements ObjectValidator {

    /**
     * 软件ID
     */
    private String         id;

    /**
     * 安装包路径
     */
    private String         path;
    /**
     * 软件关联资产集合
     */
    private List<AssetDTO> assetDTOList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<AssetDTO> getAssetDTOList() {
        return assetDTOList;
    }

    public void setAssetDTOList(List<AssetDTO> assetDTOList) {
        this.assetDTOList = assetDTOList;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        ParamterExceptionUtils.isBlank(path, "软件ID不能为空");
        ParamterExceptionUtils.isBlank(path, "安装包路径不能为空");
    }

}