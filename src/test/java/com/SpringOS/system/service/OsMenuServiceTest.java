package com.SpringOS.system.service;

import com.SpringOS.BasicTest;
import com.SpringOS.system.entity.OsMenu;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by AlbertXmas on 16/8/17.
 */
public class OsMenuServiceTest extends BasicTest {

    @Autowired
    OsMenuService osMenuService;

    @Test
    public void testFindAllBySuper() throws Exception {

    }

    @Test
    public void testFindOne() throws Exception {

    }

    @Test
    public void testSave() throws Exception {
        OsMenu osMenu = new OsMenu();
        osMenu.setId(92l);
        osMenu.setAppid("aaaa");
        osMenu.setLife(true);
        osMenu.setName("vvvv");
        osMenu.setRemark("ssss");
        osMenu.setParentid(1l);
        System.out.println(osMenuService.save(osMenu));
    }

    @Test
    public void testFindAll() throws Exception {

    }

    @Test
    public void testFindAllRealLife() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }
}