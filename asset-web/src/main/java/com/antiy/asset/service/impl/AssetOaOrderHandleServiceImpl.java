package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.service.IAssetLendRelationService;
import com.antiy.asset.service.IAssetOaOrderHandleService;
import com.antiy.asset.vo.enums.AssetOaLendStatusEnum;
import com.antiy.asset.vo.enums.AssetOaOrderStatusEnum;
import com.antiy.asset.vo.enums.AssetOaOrderTypeEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetLendRelationQuery;
import com.antiy.asset.vo.query.AssetOaOrderHandleQuery;
import com.antiy.asset.vo.request.AssetLendInfosRequest;
import com.antiy.asset.vo.request.AssetOaOrderHandleRequest;
import com.antiy.asset.vo.response.AssetOaOrderHandleResponse;
import com.antiy.biz.file.FileRespVO;
import com.antiy.biz.file.FileResponse;
import com.antiy.biz.file.FileUtils;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.AssetEnum;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
    private AssetOperationRecordDao assetOperationRecordDao;
    @Resource
    private AssetLendRelationDao assetLendRelationDao;

    @Resource
    private IAssetLendRelationService assetLendRelationService;

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
        if (request.getLendStatus() == 1 && CollectionUtils.isEmpty(request.getAssetIds())) {
            throw new BusinessException("请关联资产");
        }
        if (request.getAssetIds().size() > 1 && !AssetOaOrderTypeEnum.LEND.getCode().equals(request.getHandleType())) {
            throw new BusinessException("只允许关联一条资产信息");
        }
        String orderNumber = request.getOrderNumber();
        AssetOaOrder assetOaOrder = assetOaOrderDao.getByNumber(orderNumber);
        //已处理状态，说明不能再进行处理
        if (AssetOaOrderStatusEnum.OVER_HANDLE.getCode().equals(assetOaOrder.getOrderStatus())) {
            throw new BusinessException("订单已被其他人员抢先处理");
        }
        //判断资产状态
        judgeAssetStatus(request, assetOaOrder);
        //保存订单与资产关系
        saveAssetToOrder(request);
        //其他逻辑处理
        if (AssetOaOrderTypeEnum.INNET.getCode().equals(assetOaOrder.getOrderType())) {
            //入网处理
            saveAssetOaOrderHandleWhenInnet(request, assetOaOrder);
        } else if (AssetOaOrderTypeEnum.BACK.getCode().equals(assetOaOrder.getOrderType())) {
            //退回处理
            saveAssetOaOrderHandleWhenBack(request, assetOaOrder);
        } else if (AssetOaOrderTypeEnum.SCRAP.getCode().equals(assetOaOrder.getOrderType())) {
            //报废处理
            saveAssetOaOrderHandleWhenScript(request, assetOaOrder);
        } else if (AssetOaOrderTypeEnum.LEND.getCode().equals(assetOaOrder.getOrderType())) {
            //出借处理
            saveAssetOaOrderHandleWhenlend(request, assetOaOrder);
        }
        //更新已处理
        assetOaOrder.setOrderStatus(AssetOaOrderStatusEnum.OVER_HANDLE.getCode());
        assetOaOrderDao.update(assetOaOrder);
        //操作日志
        logger.info("--------订单处理完成,orderNumber:{}", request.getOrderNumber());
        LogUtils.recordOperLog(new BusinessData(
                AssetOaOrderTypeEnum.getValueByCode(assetOaOrder.getOrderType()).getMsg() + "处理",
                assetOaOrder.getId(),
                assetOaOrder.getNumber(),
                JSONObject.toJSON(assetOaOrder),
                BusinessModuleEnum.OA_ORDER_MANAGE,
                BusinessPhaseEnum.OA_HANDLE,
                AssetEnum.NOT_ASSET_NO
        ));
        return request.getAssetIds().size();
    }

    /**
     * 判断资产状态
     */
    void judgeAssetStatus(AssetOaOrderHandleRequest request, AssetOaOrder assetOaOrder) throws Exception {
        List<String> assetIds = request.getAssetIds();
        if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.INNET.getCode())) {
            //入网处理
            for (String assetId : assetIds) {
                assetId = assetId.endsWith("==") ? aesEncoder.decode(assetId, LoginUserUtil.getLoginUser().getUsername()) : assetId;
                Asset asset = assetDao.getById(assetId);
                if (!AssetStatusEnum.WAIT_REGISTER.getCode().equals(asset.getAssetStatus())
                        && !AssetStatusEnum.NET_IN.getCode().equals(asset.getAssetStatus())) {
                    throw new BusinessException("资产"
                            + asset.getName() + "状态为"
                            + AssetStatusEnum.getAssetByCode(asset.getAssetStatus()).getMsg()
                            + ",入网处理只允许关联"
                            + AssetStatusEnum.WAIT_REGISTER.getMsg()
                            + "和"
                            + AssetStatusEnum.NET_IN.getMsg()
                            + "资产");
                }
            }
        } else if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.BACK.getCode())) {
            //退回处理
            for (String assetId : assetIds) {
                assetId = assetId.endsWith("==") ? aesEncoder.decode(assetId, LoginUserUtil.getLoginUser().getUsername()) : assetId;
                Asset asset = assetDao.getById(assetId);
                if (!AssetStatusEnum.NET_IN.getCode().equals(asset.getAssetStatus())) {
                    throw new BusinessException("资产"
                            + asset.getName() + "状态为"
                            + AssetStatusEnum.getAssetByCode(asset.getAssetStatus()).getMsg()
                            + ",退回处理只允许关联"
                            + AssetStatusEnum.NET_IN.getMsg()
                            + "资产");
                }
            }
        } else if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.SCRAP.getCode())) {
            //报废处理
            for (String assetId : assetIds) {
                assetId = assetId.endsWith("==") ? aesEncoder.decode(assetId, LoginUserUtil.getLoginUser().getUsername()) : assetId;
                Asset asset = assetDao.getById(assetId);
                if (!AssetStatusEnum.RETIRE.getCode().equals(asset.getAssetStatus())
                        && !AssetStatusEnum.NET_IN.getCode().equals(asset.getAssetStatus())) {
                    throw new BusinessException("资产"
                            + asset.getName() + "状态为"
                            + AssetStatusEnum.getAssetByCode(asset.getAssetStatus()).getMsg()
                            + ",报废处理只允许关联"
                            + AssetStatusEnum.RETIRE.getMsg()
                            + "和"
                            + AssetStatusEnum.NET_IN.getMsg()
                            + "资产");
                }
            }
        } else if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.LEND.getCode())) {
            //出借处理
            for (String assetId : assetIds) {
                assetId = assetId.endsWith("==") ? aesEncoder.decode(assetId, LoginUserUtil.getLoginUser().getUsername()) : assetId;
                Integer count = assetOaOrderHandleDao.countLendByAssetId(Integer.parseInt(assetId));
                if(count == 0){
                    Asset asset = assetDao.getById(assetId);
                    throw new BusinessException("资产" + asset.getName() + "未处于保管中,不能被出借");
                }
            }
        }
    }


    /**
     * 当入网
     */
    void saveAssetOaOrderHandleWhenInnet(AssetOaOrderHandleRequest request, AssetOaOrder assetOaOrder) throws Exception {
        logger.info("----------入网处理,OrderNumber：{}", request.getOrderNumber());
        //如果是入网，不更改资产状态
        logger.info("入网处理，orderNumber:{}", request.getOrderNumber());
    }

    /**
     * 当退回
     */
    void saveAssetOaOrderHandleWhenBack(AssetOaOrderHandleRequest request, AssetOaOrder assetOaOrder) throws Exception {
        logger.info("----------退回处理,OrderNumber：{}", request.getOrderNumber());
        judgeStringLength(request.getPlan(), "拟定退回方案", 255, false);
        //如果是出借，需要保存出借记录,1 拒绝出借，0允许出借
        AssetOaOrderResult assetOaOrderResult = new AssetOaOrderResult();
        assetOaOrderResult.setOrderNumber(request.getOrderNumber());
        assetOaOrderResult.setGmtCreate(System.currentTimeMillis());
        assetOaOrderResult.setPlan(request.getPlan());
        assetOaOrderResult.setFileUrl(request.getFileUrl());
        assetOaOrderResult.setFileName(request.getFileName());
        assetOaOrderResult.setHandleType(request.getHandleType());
        assetOaOrderResult.setExcuteUserId(assetOaOrder.getOrderType());
        assetOaOrderResultDao.insert(assetOaOrderResult);
        //操作记录，仅对退回和报废,存入到asset_operation_record
        String assetId = request.getAssetIds().get(0);
        assetId = assetId.endsWith("==") ? aesEncoder.decode(assetId, LoginUserUtil.getLoginUser().getUsername()) : assetId;
        Asset asset = assetDao.getById(Integer.parseInt(assetId));
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setTargetObjectId(assetId);
        assetOperationRecord.setOriginStatus(asset.getStatus());
        assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_RETIRE.getCode());
        assetOperationRecord.setContent("OA订单退回执行");
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
        assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setNote(request.getPlan());
        assetOperationRecord.setFileInfo(request.getFileUrl());
        logger.info("----------订单处理保存操作记录,assetOperationRecord:{}", JSONObject.toJSONString(assetOperationRecord));
        assetOperationRecordDao.insert(assetOperationRecord);
        //如果是退回,资产状态改为待退回
        logger.info("退回处理，orderNumber:{}", request.getOrderNumber());
        Asset asset1 = new Asset();
        asset1.setAssetStatus(AssetStatusEnum.WAIT_RETIRE.getCode());
        asset1.setGmtModified(System.currentTimeMillis());
        asset1.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetDao.updateStatus(asset1);
    }

    /**
     * 当报废
     */
    void saveAssetOaOrderHandleWhenScript(AssetOaOrderHandleRequest request, AssetOaOrder assetOaOrder) throws Exception {
        logger.info("----------报废处理,OrderNumber：{}", request.getOrderNumber());
        judgeStringLength(request.getPlan(), "拟定报废方案", 255, false);
        //如果是出借，需要保存出借记录,1 拒绝出借，0允许出借
        AssetOaOrderResult assetOaOrderResult = new AssetOaOrderResult();
        assetOaOrderResult.setOrderNumber(request.getOrderNumber());
        assetOaOrderResult.setGmtCreate(System.currentTimeMillis());
        assetOaOrderResult.setPlan(request.getPlan());
        assetOaOrderResult.setFileUrl(request.getFileUrl());
        assetOaOrderResult.setFileName(request.getFileName());
        assetOaOrderResult.setHandleType(request.getHandleType());
        assetOaOrderResult.setExcuteUserId(assetOaOrder.getOrderType());
        assetOaOrderResultDao.insert(assetOaOrderResult);
        //操作记录，仅对退回和报废,存入到asset_operation_record
        String assetId = request.getAssetIds().get(0);
        assetId = assetId.endsWith("==") ? aesEncoder.decode(assetId, LoginUserUtil.getLoginUser().getUsername()) : assetId;
        Asset asset = assetDao.getById(Integer.parseInt(assetId));
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setTargetObjectId(assetId);
        assetOperationRecord.setOriginStatus(asset.getStatus());
        assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SCRAP.getCode());
        assetOperationRecord.setContent("OA订单报废执行");
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
        assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setNote(request.getPlan());
        assetOperationRecord.setFileInfo(request.getFileUrl());
        logger.info("----------订单处理保存操作记录,assetOperationRecord:{}", JSONObject.toJSONString(assetOperationRecord));
        assetOperationRecordDao.insert(assetOperationRecord);
        //如果是报废，资产状态改为待报废
        logger.info("报废处理，orderNumber:{}", request.getOrderNumber());
        Asset asset1 = new Asset();
        asset1.setId(Integer.parseInt(request.getAssetIds().get(0)));
        asset1.setAssetStatus(AssetStatusEnum.WAIT_SCRAP.getCode());
        asset1.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetDao.updateStatus(asset1);
    }

    /**
     * 当出借
     */
    void saveAssetOaOrderHandleWhenlend(AssetOaOrderHandleRequest request, AssetOaOrder assetOaOrder) throws Exception {
        logger.info("----------出借处理,OrderNumber：{}", request.getOrderNumber());
        //如果是出借，需要保存出借记录,1 拒绝出借，0允许出借
        AssetOaOrderResult assetOaOrderResult = new AssetOaOrderResult();
        assetOaOrderResult.setOrderNumber(request.getOrderNumber());
        assetOaOrderResult.setLendStatus(request.getLendStatus());
        assetOaOrderResult.setGmtCreate(System.currentTimeMillis());
        assetOaOrderResult.setExcuteUserId(assetOaOrder.getOrderType());
        if(!AssetOaLendStatusEnum.YES.getCode().equals(request.getLendStatus())){
            return;
        }
        if (!request.getLendStatus().equals(1)) {
            logger.info("----------不许出借,OrderNumber：{}", request.getOrderNumber());
            judgeStringLength(request.getRefuseReason(), "拒绝原因", 255, false);
            assetOaOrderResult.setRefuseReason(request.getRefuseReason());
            assetOaOrderResultDao.insert(assetOaOrderResult);
        } else {
            logger.info("----------允许出借,OrderNumber：{}", request.getOrderNumber());
            judgeStringLength(request.getLendRemark(), "出借说明", 255, false);
            ParamterExceptionUtils.isNull(request.getReturnTime(), "归还时间不能为空");
            ParamterExceptionUtils.isNull(request.getLendTime(), "出借时间不能为空");
            ParamterExceptionUtils.isNull(request.getLendUserId(), "出借人不能为空");
            assetOaOrderResult.setLendUserId(request.getLendUserId());
            assetOaOrderResult.setLendTime(request.getLendTime());
            assetOaOrderResult.setReturnTime(request.getReturnTime());
            assetOaOrderResult.setLendRemark(request.getLendRemark());
            assetOaOrderResultDao.insert(assetOaOrderResult);
            //如果是出借，调用金楚迅提供接口
            logger.info("出借处理，orderNumber:{}", request.getOrderNumber());
            AssetLendInfosRequest assetLendInfosRequest = new AssetLendInfosRequest();
            assetLendInfosRequest.setAssetIds(request.getAssetIds());
            assetLendInfosRequest.setLendStatus(request.getLendStatus());
            assetLendInfosRequest.setLendTime(request.getLendTime());
            assetLendInfosRequest.setLendPeriods(request.getReturnTime());
            assetLendInfosRequest.setOrderNumber(request.getOrderNumber());
            assetLendInfosRequest.setUseId(request.getLendUserId());
            assetLendInfosRequest.setLendPurpose("");
            assetLendRelationService.saveLendInfos(assetLendInfosRequest);
            //保存数据到asset_lend_relation
            //saveLendRelation(request);
        }
    }

    /**
     * 保存数据到asset_lend_relation
     */
    void saveLendRelation(AssetOaOrderHandleRequest request){
        if(CollectionUtils.isEmpty(request.getAssetIds())){
            for(String assetId : request.getAssetIds()){
                assetId = assetId.endsWith("==") ? aesEncoder.decode(assetId, LoginUserUtil.getLoginUser().getUsername()) : assetId;
                AssetLendRelation assetLendRelation = new AssetLendRelation();
                assetLendRelation.setAssetId(assetId);
                assetLendRelation.setUseId(request.getExcuteUserId());
                assetLendRelation.setLendPurpose(request.getLendRemark());
                assetLendRelation.setLendPeriods(request.getReturnTime());
                assetLendRelation.setLendTime(request.getLendTime());
                //1出借，2保管中
                assetLendRelation.setLendStatus(1);
                assetLendRelation.setOrderNumber(request.getOrderNumber());
                assetLendRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetLendRelation.setGmtCreate(System.currentTimeMillis());
                assetLendRelation.setModifyUser(LoginUserUtil.getLoginUser().getId());
                assetLendRelation.setGmtModified(System.currentTimeMillis());
            }
        }
    }

    /**
     * 保存订单与资产关系
     */
    void saveAssetToOrder(AssetOaOrderHandleRequest request) {
        List<AssetOaOrderHandle> assetOaOrderHandles = new ArrayList<AssetOaOrderHandle>();
        if(!AssetOaLendStatusEnum.YES.getCode().equals(request.getLendStatus())){
            return;
        }
        for (String assetId : request.getAssetIds()) {
            assetId = assetId.endsWith("==") ? aesEncoder.decode(assetId, LoginUserUtil.getLoginUser().getUsername()) : assetId;
            Integer assetIdInt = Integer.parseInt(assetId);
            AssetOaOrderHandle assetOaOrderHandle = new AssetOaOrderHandle();
            assetOaOrderHandle.setAssetId(assetIdInt);
            assetOaOrderHandle.setOrderNumber(request.getOrderNumber());
            assetOaOrderHandles.add(assetOaOrderHandle);
        }
        assetOaOrderHandleDao.insertBatch(assetOaOrderHandles);
    }

    /**
     * 判断字符串长度
     */
    void judgeStringLength(String str, String StrName, int length, boolean isCanBeEmpty){
        if(!isCanBeEmpty){
            if(StringUtils.isEmpty(str)){
                throw new BusinessException(StrName + "不能为空");
            }
        }
        if(str.length() > length){
            throw new BusinessException(StrName + "不能超过" + length +"个字符");
        }
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
