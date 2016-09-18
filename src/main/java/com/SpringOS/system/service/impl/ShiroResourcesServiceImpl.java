package com.SpringOS.system.service.impl;

import com.SpringOS.system.entity.ShiroResources;
import com.SpringOS.system.repository.ShiroResourcesRepository;
import com.SpringOS.system.service.ShiroResourcesService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by AlbertXmas on 16/8/29.
 */
@Service
@Transactional
public class ShiroResourcesServiceImpl
        extends CommonServiceImpl<ShiroResources, ShiroResourcesRepository>
        implements ShiroResourcesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroResourcesService.class);

    public List<ShiroResources> findOneBy(ShiroResources shiroResources){
        return getRepository().findByRealNameAndMethod(shiroResources.getRealName(), shiroResources.getMethod());
    }

    public List<ShiroResources> findOneBySome(ShiroResources shiroResources){
        return getRepository().findOneByRealNameAndMethodAndShiroAuth(
                shiroResources.getRealName(),
                shiroResources.getMethod(),
                shiroResources.getShiroAuth()
        );
    }

    public ShiroResources clearRepetitionData(ShiroResources shiroResources){
        List<ShiroResources> list = findOneBySome(shiroResources);
        for (int i = 1; i<list.size(); i++){
            getRepository().delete(list.get(i));
        }
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

}
