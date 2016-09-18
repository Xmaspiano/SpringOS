package com.SpringOS.system.web;

import com.SpringOS.BasicTest;
import com.SpringOS.system.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
/**
 * Created by AlbertXmas on 16/8/17.
 */
public class UserControllerTest extends BasicTest {

    @Test
    public void testSaveMenuInfo() throws Exception {
        super.mockMvc.perform(post("/user/save")
                .param("id","11")
                .param("salt","sss")
                .param("locked","0")
//                .param("username","1vvvv")
                .param("password","sss")
                .param("roleId","1")
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    public void testIndex() throws Exception {
        super.mockMvc.perform(get("/user")

        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void testDeleteMenuInfo() throws Exception {
        super.mockMvc.perform(post("/user/delete")
                .param("id","9")
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void testGetTreeGridDate() throws Exception {
        super.mockMvc.perform(post("/user/date_treegrid.json")

        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}