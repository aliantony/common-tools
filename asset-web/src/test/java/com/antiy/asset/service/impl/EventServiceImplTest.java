package com.antiy.asset.service.impl;

import com.antiy.asset.dao.IEventDao;
import com.antiy.asset.entity.Event;
import com.antiy.asset.service.IEventService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventServiceImplTest {

    @MockBean
    private IEventDao eventDao;
    @SpyBean
    private IEventService iEventService;

    @Test
    public void getEventlist() throws Exception {
        List<Event> list=new ArrayList<>();
        when(eventDao.getEventlist(anyInt())).thenReturn(list);

        List<Event> eventlist=iEventService.getEventlist(11);
        Assert.assertEquals(0,eventlist.size());

    }

    @Test
    public void saveEvent() throws Exception {
        Event event=new Event();
        iEventService.saveEvent(event);
        System.out.println("成功");
    }

    @Test
    public void editEvent() throws Exception {
        Event event=new Event();
        iEventService.editEvent(event);
        System.out.println("成功");
    }

    @Test
    public void deleteEventById() throws Exception {
        Event event=new Event();
        event.setId(1);
        iEventService.deleteEventById(event);
        System.out.println("成功");
    }
}