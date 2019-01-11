package com.antiy.asset.dao;

import com.antiy.asset.entity.Event;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuyu
 * @date
 */
public interface IEventDao extends IBaseDao<Event> {
    /**
     * 获取
     *
     * @param idStars 参数
     * @return eventlist
     */
    List<Event> getEventlist(@Param(value = "idStars") Integer idStars);

}

