package com.SpringOS.system.service;

import com.SpringOS.system.entity.OsMenu;

import java.util.List;

/**
 * Created by AlbertXmas on 16/8/8.
 */
public interface OsMenuService extends CommonService<OsMenu>{

    public List<OsMenu> findAllBySuper(long id);

    public List<OsMenu> findAllRealLife();

}
