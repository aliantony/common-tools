package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetTopologyDao;
import com.antiy.asset.entity.AssetTopology;
import com.antiy.asset.entity.Topology;
import com.antiy.asset.vo.enums.TopologyTypeEnum;
import com.antiy.asset.vo.request.TopologyRequest;
import com.antiy.asset.vo.response.TopologyResponse;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class TopologyServiceImplTest {

    /**
     * 被测试service
     */
    @InjectMocks
    private TopologyServiceImpl service;

    /**
     * 模拟数据库访问类
     */
    @Mock
    private AssetDao assetDao;

    @Mock
    private AssetTopologyDao assetTopologyDao;

    /**
     * 初始化查询拓扑测试
     * 情景1：数据库中存在拓扑信息，返回拓扑信息列表
     * 断言返回列表大小
     * @throws Exception except
     */
    @Test
    public void queryTopologyInit() throws Exception {
        List<Topology> topologies = new ArrayList<>();
        Topology topology = new Topology();
        topology.setName("Leo");
        topology.setParentId(1);
        topology.setId(1);
        topology.setType(1);
        topologies.add(topology);
        Mockito.when(assetDao.findTopologyList(Mockito.any())).thenReturn(topologies);

        List<TopologyResponse> list = service.queryTopologyInit();
        assertThat(list.size(), Matchers.equalTo(1));
    }

    /**
     * 初始化查询拓扑测试
     * 情景2：数据库中不存在拓扑信息，返回空列表
     * 断言返回列表为空
     * @throws Exception except
     */
    @Test
    public void queryTopologyInitEmpty() throws Exception {
        List<Topology> topologies = new ArrayList<>();
        Mockito.when(assetDao.findTopologyList(Mockito.any())).thenReturn(topologies);

        List<TopologyResponse> list = service.queryTopologyInit();
        assertThat(list, Matchers.nullValue());
    }

    /**
     * 初始化查询拓扑测试
     * 情景3：数据库中存在拓扑信息，父id为0，返回拓扑信息列表
     * 断言返回列表大小
     * @throws Exception except
     */
    @Test
    public void queryTopologyInitElse() throws Exception {
        List<Topology> topologies = new ArrayList<>();
        Topology topology = new Topology();
        topology.setName("Leo");
        topology.setParentId(0);
        topology.setId(1);
        topology.setType(1);
        topologies.add(topology);
        Mockito.when(assetDao.findTopologyList(Mockito.any())).thenReturn(topologies);

        List<TopologyResponse> list = service.queryTopologyInit();
        assertThat(list.size(), Matchers.equalTo(1));
    }

    /**
     * 初始化查询拓扑测试
     * 情景4：数据库中存在拓扑信息，父id不为0，，id与父id不同，返回拓扑信息列表
     * 断言返回列表大小
     * @throws Exception except
     */
    @Test
    public void queryTopologyInitElse2() throws Exception {
        List<Topology> topologies = new ArrayList<>();
        Topology topology = new Topology();
        topology.setName("Leo");
        topology.setParentId(11);
        topology.setId(1);
        topology.setType(1);
        topologies.add(topology);
        Mockito.when(assetDao.findTopologyList(Mockito.any())).thenReturn(topologies);

        List<TopologyResponse> list = service.queryTopologyInit();
        assertThat(list.size(), Matchers.equalTo(1));
    }

    /**
     * 查询拓扑测试
     * 情景1：传入拓扑类型，数据库存在拓扑信息，返回拓扑信息
     * 断言拓扑信息
     * @throws Exception except
     */
    @Test
    public void queryTopology() throws Exception {
        List<AssetTopology> topologies = new ArrayList<>();
        AssetTopology assetTopology = new AssetTopology();
        assetTopology.setRelation("relation");
        topologies.add(assetTopology);
        Mockito.when(assetTopologyDao.getByWhere(Mockito.any())).thenReturn(topologies);

        String s = service.queryTopology(998);
        assertThat(s, Matchers.equalTo("relation"));
    }

    /**
     * 查询拓扑测试
     * 情景2：传入拓扑类型，数据库不存在拓扑信息，返回空
     * 断言拓扑信息为空
     * @throws Exception except
     */
    @Test
    public void queryTopologyElse() throws Exception {
        Mockito.when(assetTopologyDao.getByWhere(Mockito.any())).thenReturn(null);

        String s = service.queryTopology(998);
        assertNull(s);
    }

    /**
     * 保存拓扑信息测试
     * 传入请求参数，向数据库存入拓扑信息，返回保存结果
     * 断言保存结果
     * @throws Exception except
     */
    @Test
    public void saveTopology() throws Exception {
        Mockito.when(assetTopologyDao.insert(Mockito.any())).thenReturn(998);
        TopologyRequest request = new TopologyRequest();
        request.setTopologyType(TopologyTypeEnum.PHYSICS);
        int num = service.saveTopology(request);
        assertThat(num, Matchers.equalTo(998));
    }
}