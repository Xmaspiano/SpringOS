package com.SpringOS.system.service;

import com.SpringOS.system.entity.ShiroResources;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * Created by AlbertXmas on 16/8/29.
 */
public interface ShiroResourcesService extends CommonService<ShiroResources>{

    public List<ShiroResources> findOneBy(ShiroResources shiroResources);

    public List<ShiroResources> findOneBySome(ShiroResources shiroResources);

    public ShiroResources clearRepetitionData(ShiroResources shiroResources);
}
