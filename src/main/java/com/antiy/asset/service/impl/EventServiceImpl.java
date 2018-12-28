/**
 * **********************************************************************
 * All rights Reserved, Designed By changhong
 *
 * @Title: AccountFlowServiceImpl.java
 * @Package com.changhong.zbcloud.system.service.impl
 * @Description:
 * @author: WangYongjie
 * @date: 2017年8月8日 下午3:33:32
 * @version V1.0
 * @Copyright: 2017 www.changhong.com Inc. All rights reserved.
 * **********************************************************************
 */
package com.antiy.asset.service.impl;


import com.antiy.asset.dao.IEventDao;
import com.antiy.asset.entity.Event;
import com.antiy.asset.service.IEventService;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author chenxinchong
 * @version 1.0.0
 * @ClassName EventServiceImpl
 * @Description 事件定义.
 * @Date 2018年1月17日 上午10:40:08
 */
@Slf4j
@Service
public class EventServiceImpl extends BaseServiceImpl<Event> implements IEventService {

    private static final Logger logger = LogUtils.get();

    @Autowired
    private IEventDao eventDao;


    @Override
    public List<Event> getEventlist(Integer idStars) {
        return eventDao.getEventlist(idStars);
    }

    @Override
    public void saveEvent(Event event) throws Exception {
        LogUtils.info(logger, "保存事件定义入口：参数：event-{}", event);
        eventDao.insert(event);
        LogUtils.info(logger, "保存事件定义完成");
        LogUtils.info(logger, "保存事件修改完成");
    }


    @Override
    public void editEvent(Event event) throws Exception {
        LogUtils.info(logger, "编辑事件定义入口：参数：event-{}", event);
        event.setUpdateTime((int) (System.currentTimeMillis() / 1000));
        eventDao.update(event);
        LogUtils.info(logger, "编辑事件定义完成");
        LogUtils.info(logger, "事件定义编辑保存事件修改完成");
    }


    @Override
    public void deleteEventById(Event event) throws Exception {
        eventDao.deleteById(event.getId());
        LogUtils.info(logger, "事件定义删除保存事件修改完成");
    }
}
