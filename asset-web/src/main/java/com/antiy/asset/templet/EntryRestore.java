package com.antiy.asset.templet;

import com.antiy.asset.service.impl.AssetEntryServiceImpl;
import com.antiy.asset.vo.request.AssetEntryRequest;
import com.antiy.common.utils.LogUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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

    public synchronized void initRestoreRequest(AssetEntryRequest entryRequest) {
        EntryRestoreRequest entryRestoreRequest = new EntryRestoreRequest(entryRequest, System.currentTimeMillis() + 12 * 60 * 60_1000);
        restoreRequests.add(entryRestoreRequest);
        if (Objects.isNull(runnable)) {
            runnable = () -> {
                try {
                    Iterator<EntryRestoreRequest> requests = null;

                    while ((requests = restoreRequests.iterator()).hasNext()) {
                        EntryRestoreRequest request = requests.next();
                        long waitTime = request.getWaitTime() - System.currentTimeMillis();
                        if (waitTime >= 0) {
                            Thread.currentThread().wait(waitTime);
                        }

                        //检查是否启动自动准入  request.getEntryRequest().getAssetActivityRequests()数量会发生变化
                        //currentEntryRequest 不满足自动准入的部分资产
                        AssetEntryRequest currentEntryRequest = entryService.scanTask(request.getEntryRequest());
                        if (CollectionUtils.isNotEmpty(request.getEntryRequest().getAssetActivityRequests())) {
                            new Thread(() -> entryService.updateEntryStatus(request.getEntryRequest())).start();
                        }
                        if (CollectionUtils.isEmpty(currentEntryRequest.getAssetActivityRequests())) {
                            requests.remove();
                        }
                    }
                    //任务执行完毕，发送中断信号
//                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    logger.info("准入管理-自动恢复入网任务执行完毕");
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


}
