/**
 * **********************************************************************
 * All rights Reserved, Designed By changhong
 *
 * @Title: IEventService.java
 * @Package com.changhong.zbcloud.system.service
 * @Description: TODTO;
 * @author: chenxinchong
 * @date: 2018年1月17日 上午10:35:33
 * @version V1.0
 * @Copyright: 2018 www.changhong.com Inc. All rights reserved.
 * **********************************************************************
 */
package com.antiy.asset.service;

import com.antiy.asset.entity.Event;
import com.antiy.common.base.IBaseService;

import java.util.List;


/**
 * @author chenxinchong
 * @version 1.0.0
 * @ClassName IEventService
 * @Description TODTO;
 * @Date 2018年1月17日 上午10:35:33
 */
public interface IEventService extends IBaseService<Event> {

    /**
     * 查询事件列表
     */
    List<Event> getEventlist(Integer idStars) throws Exception;

    /**
     * 保存事件
     */
    void saveEvent(Event event) throws Exception;

    /**
     * 编辑事件
     */
    void editEvent(Event event) throws Exception;

    /**
     * 删除事件
     */
    void deleteEventById(Event event) throws Exception;

}
