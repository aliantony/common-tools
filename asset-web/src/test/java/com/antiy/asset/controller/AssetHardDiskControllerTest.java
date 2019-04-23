package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetHardDiskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetHardDiskControllerTest {

    @InjectMocks
    private AssetHardDiskController assetHardDiskController;
    @Mock
    private IAssetHardDiskService iAssetHardDiskService;
    private MockMvc mockMvc;

    @Before
    public void setUp(){
    }
    @Test
    public void saveSingle()throws Exception {
    }

    @Test
    public void updateSingle() {
    }

    @Test
    public void queryList() {
    }

    @Test
    public void queryById() {
    }

    @Test
    public void deleteById() {
    }
}