package com.antiy.asset.templet;

import com.antiy.asset.service.impl.AssetEntryServiceImpl;
import com.antiy.asset.vo.request.AssetEntryRequest;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author liulusheng
 * @since 2020/3/9
 */
@Component
public class EntryRestore {
    private Logger logger = LogUtils.get(this.getClass());
    @Resource
    private AssetEntryServiceImpl entryService;
    private volatile Thread thread;
    volatile List<EntryRestoreRequest> restoreRequests = new ArrayList<>();
    private volatile Runnable runnable;
    private volatile Boolean isSorted = false;
    private long waitTimes = 12 * 60 * 60_000;
//    private long waitTimes = 10_000;
    public synchronized void initRestoreRequest(AssetEntryRequest entryRequest) {
        logger.info("自动恢复初始化中");
        EntryRestoreRequest entryRestoreRequest = new EntryRestoreRequest(entryRequest, System.currentTimeMillis() + waitTimes);
        restoreRequests.add(entryRestoreRequest);
        restoreRequests.sort(new Comparator<EntryRestoreRequest>() {
            //升序排序
            @Override
            public int compare(EntryRestoreRequest o1, EntryRestoreRequest o2) {
                long a = o1.getWaitTime() - o2.getWaitTime();
                if (a < 0) {
                    return -1;
                } else if (a == 0) {
                    return 0;
                }
                return 1;
            }
        });
        isSorted = true;
        if (Objects.isNull(runnable)) {
            runnable = () -> {
                try {
                    EntryRestoreRequest request = null;
                    int i = 0;
                    while (CollectionUtils.isNotEmpty(restoreRequests)) {
                        if (isSorted || i>=restoreRequests.size()) {
                            i = 0;
                        }
                        isSorted = false;
                        request = restoreRequests.get(i);
                        i++;
                        long waitTime = request.getWaitTime() - System.currentTimeMillis();
                        if (waitTime >= 0) {
                            System.out.println(Thread.currentThread().getName());
                            logger.info("休眠时间：{}",waitTime);
                            Thread.sleep(waitTime);
//                            Thread.currentThread().wait(waitTime);
                        }

                        //检查是否启动自动准入  request.getEntryRequest().getAssetActivityRequests()数量会发生变化
                        //currentEntryRequest 不满足自动准入的部分资产
                        AssetEntryRequest currentEntryRequest = entryService.scanTask(request.getEntryRequest());
                        if (CollectionUtils.isNotEmpty(request.getEntryRequest().getAssetActivityRequests())) {
                            AssetEntryRequest assetEntryRequest = request.getEntryRequest();
                            assetEntryRequest.setAssetActivityRequests(new ArrayList<>(assetEntryRequest.getAssetActivityRequests()));
                            new Thread(() -> {
                                try {
                                    entryService.updateEntryStatus(assetEntryRequest);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }).start();
                        }
                        if (CollectionUtils.isEmpty(currentEntryRequest.getAssetActivityRequests())) {
                            restoreRequests.remove(request);
                            i--;
                        } else {
                            request.setEntryRequest(currentEntryRequest);
                            request.setWaitTime(System.currentTimeMillis() + waitTimes);
                            restoreRequests.set(i-1, request);
                        }
                    }
                    logger.info("恢复完毕");
                    //任务执行完毕，发送中断信号
                    thread.interrupt();
                    thread = null;
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    thread = null;
                    logger.info("准入管理-自动恢复入网任务执行完毕");
                    throw new BusinessException(e);
                }
            };
        }
        if (Objects.isNull(thread)) {
            thread = new Thread(runnable);
            thread.start();
        }

    }


    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public List<EntryRestoreRequest> getRestoreRequests() {
        return restoreRequests;
    }

    public void setRestoreRequests(List<EntryRestoreRequest> restoreRequests) {
        this.restoreRequests = restoreRequests;
    }
}

class EntryRestoreRequest {
    private AssetEntryRequest entryRequest;
    private long waitTime;

    public EntryRestoreRequest(AssetEntryRequest entryRequest, long waitTime) {
        this.entryRequest = entryRequest;
        this.waitTime = waitTime;
    }

    public AssetEntryRequest getEntryRequest() {
        return entryRequest;
    }

    public void setEntryRequest(AssetEntryRequest entryRequest) {
        this.entryRequest = entryRequest;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public String toString() {
        return "EntryRestoreRequest{" +
                "entryRequest=" + entryRequest +
                ", waitTime=" + waitTime +
                '}';
    }
}
