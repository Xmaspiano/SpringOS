package com.SpringOS.system.service.impl;

import com.SpringOS.BasicTest;
import com.SpringOS.system.entity.User;
import com.SpringOS.system.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by AlbertXmas on 16/8/17.
 */
public class UserServiceImplTest extends BasicTest {
    @Autowired
    UserService userService;

    @Test
    public void testFindOne() throws Exception {
        System.out.println(userService.findOne(1L));
    }

    @Test
    public void testFindAll() throws Exception {
        System.out.println(userService.findAll());
    }

    @Test
    public void testSave(){
        User user = new User();
        user.setSalt("123");
        user.setLocked(false);
        user.setPassword("1111");
        user.setUsername("admin1");
        user.setRoleId(1l);

//        System.out.println(userService.save(user));
    }

}