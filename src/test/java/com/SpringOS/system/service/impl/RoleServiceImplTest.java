package com.SpringOS.system.service.impl;

import com.SpringOS.BasicTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by AlbertXmas on 16/8/17.
 */
public class RoleServiceImplTest extends BasicTest {
    @Autowired
    RoleServiceImpl roleService;

    @Test
    public void testFindOne() throws Exception {
        System.out.println(roleService.findOne(1l));
    }

    @Test
    public void testFindAll() throws Exception {
        System.out.println(roleService.findAll());
    }
}