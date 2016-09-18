package com.SpringOS.system.service.impl;

import com.SpringOS.system.entity.OsMenu;
import com.SpringOS.system.entity.Role;
import com.SpringOS.system.repository.OsMenuRepository;
import com.SpringOS.system.repository.RoleRepository;
import com.SpringOS.system.service.OsMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by AlbertXmas on 16/8/8.
 */
@Service
@Transactional
public class OsMenuServiceImpl
        extends CommonServiceImpl<OsMenu, OsMenuRepository>
        implements OsMenuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipUserRoleServiceImpl.class);

    @Override
    @Cacheable(value = "meun_data", key = "#id")//先从缓存中读取，如果没有再调用方法获取数据，然后把数据添加到缓存中
    public OsMenu findOne(Long id) {
        return super.findOne(id);
    }

    @Override
    @CachePut(value = "meun_data", key = "#osMenu.id")//数据放入缓存
    public OsMenu save(OsMenu osMenu) {
        return super.save(osMenu);
    }

    @Override
    @CacheEvict(value = "meun_data", key = "#id") //移除指定key的数据
    public void delete(Long id) {
        super.delete(id);
    }

    public List<OsMenu> findAllBySuper(long id){
        return getRepository().findByParentid(id);
    }

    public List<OsMenu> findAllRealLife(){
        return getRepository().findAllByLife(true);
    }

    private void fullOsMenu(OsMenu osMenu){

    }

    private List<OsMenu> test(List<OsMenu> osMenuList){
        OsMenu osMenu;
        List<OsMenu> liTemp;
        for (int i = 0; i < osMenuList.size(); i++) {
            osMenu = osMenuList.get(i);
            liTemp = getRepository().findByParentid(osMenu.getId());
            if(liTemp.size()>0) {
                osMenu.setOsMenuList(test(liTemp));
            }
        }
        return osMenuList;
    }

}
