package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetOaOrderDao;
import com.antiy.asset.dao.AssetOaOrderHandleDao;
import com.antiy.asset.dao.AssetOaOrderResultDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetOaOrder;
import com.antiy.asset.entity.AssetOaOrderHandle;
import com.antiy.asset.entity.AssetOaOrderResult;
import com.antiy.asset.service.IAssetOaOrderHandleService;
import com.antiy.asset.vo.enums.AssetOaOrderStatusEnum;
import com.antiy.asset.vo.enums.AssetOaOrderTypeEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetOaOrderHandleQuery;
import com.antiy.asset.vo.request.AssetOaOrderHandleRequest;
import com.antiy.asset.vo.response.AssetOaOrderHandleResponse;
import com.antiy.biz.file.FileRespVO;
import com.antiy.biz.file.FileResponse;
import com.antiy.biz.file.FileUtils;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 订单处理关联资产表 服务实现类
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */
@Transactional(rollbackFor = {Exception.class})
@Service
public class AssetOaOrderHandleServiceImpl extends BaseServiceImpl<AssetOaOrderHandle> implements IAssetOaOrderHandleService {

    private static final Logger logger = LogUtils.get();

    @Resource
    private AssetOaOrderHandleDao assetOaOrderHandleDao;
    @Resource
    private AssetOaOrderDao assetOaOrderDao;
    @Resource
    private AssetOaOrderResultDao assetOaOrderResultDao;
    @Resource
    private AssetDao assetDao;

    @Resource
    private BaseConverter<AssetOaOrderHandleRequest, AssetOaOrderHandle> requestConverter;
    @Resource
    private BaseConverter<AssetOaOrderHandle, AssetOaOrderHandleResponse> responseConverter;

    @Resource
    private AesEncoder aesEncoder;

    @Resource
    public FileUtils fileUtils;

    @Value("${modelName}")
    private String modelName;
    @Value("${hdfs.fsUri}")
    private String fsUri;

    @Override
    public Integer saveAssetOaOrderHandle(AssetOaOrderHandleRequest request) throws Exception {
        if (CollectionUtils.isEmpty(request.getAssetIds())) {
            throw new BusinessException("请关联资产");
        }
        if(request.getAssetIds().size() > 1 && !AssetOaOrderTypeEnum.LEND.getCode().equals(request.getHandleType())){
            throw new BusinessException("只允许关联一条资产信息");
        }
        String orderNumber = request.getOrderNumber();
        AssetOaOrder assetOaOrder = assetOaOrderDao.getByNumber(orderNumber);
        if (AssetOaOrderTypeEnum.LEND.getCode().equals(assetOaOrder.getOrderType())) {
            //如果是出借，需要保存出借记录,1 拒绝出借，0允许出借
            AssetOaOrderResult assetOaOrderResult = new AssetOaOrderResult();
            assetOaOrderResult.setOrderNumber(orderNumber);
            assetOaOrderResult.setLendStatus(request.getLendStatus());
            assetOaOrderResult.setGmtCreate(System.currentTimeMillis());
            assetOaOrderResult.setExcuteUserId(assetOaOrder.getOrderType());
            if (!request.getLendStatus().equals(1)) {
                logger.info("----------不许出借,OrderNumber：{}", request.getOrderNumber());
                assetOaOrderResult.setRefuseReason(request.getRefuseReason());
                assetOaOrderResultDao.insert(assetOaOrderResult);
                return 0;
            } else {
                logger.info("----------允许出借,OrderNumber：{}", request.getOrderNumber());
                assetOaOrderResult.setLendUserId(request.getLendUserId());
                assetOaOrderResult.setLendTime(request.getLendTime());
                assetOaOrderResult.setReturnTime(request.getReturnTime());
                assetOaOrderResult.setLendRemark(request.getLendRemark());
                assetOaOrderResultDao.insert(assetOaOrderResult);
            }
        }else if (AssetOaOrderTypeEnum.BACK.getCode().equals(assetOaOrder.getOrderType()) || AssetOaOrderTypeEnum.SCRAP.getCode().equals(assetOaOrder.getOrderType())) {
            logger.info("----------允许出借,OrderNumber：{}", request.getOrderNumber());
            //如果是出借，需要保存出借记录,1 拒绝出借，0允许出借
            AssetOaOrderResult assetOaOrderResult = new AssetOaOrderResult();
            assetOaOrderResult.setOrderNumber(orderNumber);
            assetOaOrderResult.setGmtCreate(System.currentTimeMillis());
            assetOaOrderResult.setPlan(request.getPlan());
            assetOaOrderResult.setFileUrl(request.getFileUrl());
            assetOaOrderResult.setFileName(request.getFileName());
            assetOaOrderResult.setHandleType(request.getHandleType());
            assetOaOrderResult.setExcuteUserId(assetOaOrder.getOrderType());
            assetOaOrderResultDao.insert(assetOaOrderResult);
        }
        //保存订单与资产关联关系
        List<AssetOaOrderHandle> assetOaOrderHandles = new ArrayList<AssetOaOrderHandle>();
        for (String assetId : request.getAssetIds()) {
            assetId = assetId.endsWith("==") ? aesEncoder.decode(assetId, LoginUserUtil.getLoginUser().getUsername()) : assetId;
            Integer assetIdInt = Integer.parseInt(assetId);
            AssetOaOrderHandle assetOaOrderHandle = new AssetOaOrderHandle();
            assetOaOrderHandle.setAssetId(assetIdInt);
            assetOaOrderHandle.setOrderNumber(orderNumber);
            assetOaOrderHandles.add(assetOaOrderHandle);
        }
        assetOaOrderHandleDao.insertBatch(assetOaOrderHandles);
        //对资产做相应操作
        if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.INNET.getCode())) {
            //如果是入网，不更改资产状态
        } else if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.BACK.getCode())) {
            //如果是退回,资产状态改为待退回
            Asset asset = new Asset();
            asset.setId(Integer.parseInt(request.getAssetIds().get(0)));
            asset.setAssetStatus(AssetStatusEnum.WAIT_RETIRE.getCode());
            assetDao.updateStatus(asset);
        } else if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.SCRAP.getCode())) {
            //如果是报废，资产状态改为待报废
            Asset asset = new Asset();
            asset.setId(Integer.parseInt(request.getAssetIds().get(0)));
            asset.setAssetStatus(AssetStatusEnum.WAIT_SCRAP.getCode());
            assetDao.updateStatus(asset);
        } else if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.LEND.getCode())) {
            //如果是出借，调用金楚迅提供接口

        }
        //更改订单状态为已处理
        assetOaOrder.setOrderStatus(AssetOaOrderStatusEnum.OVER_HANDLE.getCode());
        assetOaOrderDao.update(assetOaOrder);
        return request.getAssetIds().size();
    }

    @Override
    public Integer updateAssetOaOrderHandle(AssetOaOrderHandleRequest request) throws Exception {
        AssetOaOrderHandle assetOaOrderHandle = requestConverter.convert(request, AssetOaOrderHandle.class);
        return assetOaOrderHandleDao.update(assetOaOrderHandle);
    }

    @Override
    public List<AssetOaOrderHandleResponse> findListAssetOaOrderHandle(AssetOaOrderHandleQuery query) throws Exception {
        List<AssetOaOrderHandle> assetOaOrderHandleList = assetOaOrderHandleDao.findQuery(query);
        //TODO
        List<AssetOaOrderHandleResponse> assetOaOrderHandleResponse = responseConverter.convert(assetOaOrderHandleList, AssetOaOrderHandleResponse.class);
        return assetOaOrderHandleResponse;
    }

    @Override
    public PageResult<AssetOaOrderHandleResponse> findPageAssetOaOrderHandle(AssetOaOrderHandleQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(), this.findListAssetOaOrderHandle(query));
    }

    @Override
    public void uploadToHdfs(MultipartFile tmpFile, List<FileRespVO> fileRespVOS) throws Exception {
        try {
            long startTime = System.currentTimeMillis();
            LogUtils.info(logger, "上传开始:{}", startTime);
            //验证文件名长度，限制200
            fileUtils.verfyFileNameLength(tmpFile.getOriginalFilename());
            //调用上传接口
            FileResponse<FileRespVO> fileResponse = fileUtils.uploadFileFromLocal(tmpFile, modelName);
            if (RespBasicCode.SUCCESS.getResultCode().equals(fileResponse.getCode())) {
                fileRespVOS.add(fileResponse.getData());
            }

            long endTime = System.currentTimeMillis();
            LogUtils.info(logger, "上传结束：{},耗时：{}", endTime, (endTime - startTime));

        } catch (IOException e) {
            LogUtils.error(logger, e, "文件操作异常 ");
            throw new BusinessException("文件操作异常");
        }
    }

    @Override
    public void downloadFromHdfs(HttpServletRequest request, HttpServletResponse response, String fileName, String url) throws Exception {
        url = fsUri + url;
        long startTime = System.currentTimeMillis();
        LogUtils.info(logger, "下载开始,url:{},时间{}", url, startTime);
        // 清空response
        response.reset();
        File file = null;
        try {
            FileResponse fileResponse = fileUtils.downloadFromLocal(url, fileName, modelName);
            if (fileResponse != null && RespBasicCode.SUCCESS.getResultCode().equals(fileResponse.getCode())) {
                String fileUrl = (String) fileResponse.getData();
                file = new File(fileUrl);
                writeResponse(request, response, fileName, file);
                long endTime = System.currentTimeMillis();
                LogUtils.info(logger, "下载结束：{},耗时：{}", endTime, (endTime - startTime));
            }

        } catch (FileNotFoundException e) {
            LogUtils.error(logger, e, "资源不存在");
            throw new BusinessException("资源不存在");
        } catch (IOException e) {
            LogUtils.error(logger, e, "文件下载异常");
            throw new BusinessException("文件下载异常");
        } finally {
            if (null != file && !Files.deleteIfExists(file.toPath())) {
                LogUtils.info(logger, "资源删除失败");
            }
        }
    }

    /**
     * @param response
     * @param fileName
     * @param file
     * @return void
     * @Description 文件流写入Response
     * @Date 13:56 2019/5/27
     */
    private void writeResponse(HttpServletRequest request, HttpServletResponse response, String fileName, File file) throws IOException {
        if (null != file && file.length() > 0) {
            try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                 OutputStream toClient = new BufferedOutputStream(response.getOutputStream())) {
                // 设置response的Header
                response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", handlerFileName(request, fileName)));
                response.addHeader("Content-Length", "" + file.length());
                response.setContentType("application/octet-stream");
                byte[] buffer = new byte[1024 * 1024 * 5];
                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    toClient.write(buffer, 0, len);
                    toClient.flush();
                }
                toClient.flush();
            }
        }
    }

    /**
     * @param request
     * @param fileName
     * @return java.lang.String
     * @Description 设置不同浏览器的编码
     * @Date 17:25 2019/5/22
     */
    private String handlerFileName(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        if (Objects.isNull(request)) {
            fileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
            return fileName;
        }

        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent != null && (userAgent.indexOf("firefox") >= 0 ||
                userAgent.indexOf("chrome") >= 0 ||
                userAgent.indexOf("safari") >= 0)) {
            fileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
            return fileName;
        }
        // 其他浏览器
        fileName = URLEncoder.encode(fileName, "UTF-8");
        return fileName.replaceAll("\\+", "%20");
    }


}
