package com.SpringOS.system.web;

import com.SpringOS.BasicTest;
import com.SpringOS.system.entity.OsMenu;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by AlbertXmas on 16/8/17.
 */
public class MenuControllerTest extends BasicTest {

    @Test
    public void testIndex() throws Exception {
        super.mockMvc.perform(post("/menu/save")
                .param("id","92")
                .param("appid","aaa")
                .param("life","1")
                .param("name","vvvv")
                .param("remark","sss")
                .param("parentid","1")
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void testSaveMenuInfo() throws Exception {

    }

    @Test
    public void testDeleteMenuInfo() throws Exception {

    }

    @Test
    public void testGetTreeGridDate() throws Exception {

    }
}