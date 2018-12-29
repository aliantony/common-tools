package com.antiy.asset.asset.dao;

import com.antiy.asset.base.IBaseDao;
import com.antiy.asset.entity.Event;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author liuyu
 * @date
 *
 */
public interface IEventDao extends IBaseDao<Event> {
	/**
	 *  获取
	 * @param idStars 参数
	 * @return eventlist
	 */
	List<Event> getEventlist(@Param(value = "idStars") Integer idStars);

}

