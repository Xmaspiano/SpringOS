package com.SpringOS.system.repository;

import com.SpringOS.system.entity.Resource;
import com.SpringOS.system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by AlbertXmas on 16/8/8.
 */
public interface ResourceRepository extends JpaRepository<Resource, Long>,CommonRepository<Resource, ResourceRepository> {

}
