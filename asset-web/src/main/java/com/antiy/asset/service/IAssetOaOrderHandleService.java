package com.antiy.asset.service;

import com.antiy.asset.entity.AssetOaOrderHandle;
import com.antiy.asset.vo.query.AssetOaOrderHandleQuery;
import com.antiy.asset.vo.request.AssetOaOrderHandleRequest;
import com.antiy.asset.vo.response.AssetOaOrderHandleResponse;
import com.antiy.biz.file.FileRespVO;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * <p>
 * 订单处理关联资产表 服务类
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */
public interface IAssetOaOrderHandleService extends IBaseService<AssetOaOrderHandle> {

        /**
         * 保存
         * @param request
         * @return
         */
        Integer saveAssetOaOrderHandle(AssetOaOrderHandleRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetOaOrderHandle(AssetOaOrderHandleRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetOaOrderHandleResponse> findListAssetOaOrderHandle(AssetOaOrderHandleQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetOaOrderHandleResponse> findPageAssetOaOrderHandle(AssetOaOrderHandleQuery query) throws Exception;

        /**
         * 上传附件到hdfs
         */
        void uploadToHdfs(MultipartFile tmpFile, List<FileRespVO> fileRespVOS) throws Exception;

        /**
         * 从hdfs下载
         */
        void downloadFromHdfs(HttpServletRequest request, HttpServletResponse response, String fileName, String url) throws Exception;
}
