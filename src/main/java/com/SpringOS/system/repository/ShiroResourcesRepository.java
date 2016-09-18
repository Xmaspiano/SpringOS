package com.SpringOS.system.repository;

import com.SpringOS.system.entity.ShiroResources;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by AlbertXmas on 16/8/29.
 */
public interface ShiroResourcesRepository
        extends CrudRepository<ShiroResources, Long>,CommonRepository<ShiroResources, ShiroResourcesRepository> {

    public List<ShiroResources> findByRealNameAndMethod(String RealName, String method);

    public List<ShiroResources> findOneByRealNameAndMethodAndShiroAuth(String RealName, String method, String ShiroAuth);

}
