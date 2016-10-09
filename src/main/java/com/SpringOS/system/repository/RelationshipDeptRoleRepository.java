package com.SpringOS.system.repository;

import com.SpringOS.system.entity.RelationshipDeptRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by AlbertXmas on 16/8/8.
 */
public interface RelationshipDeptRoleRepository
        extends JpaRepository<RelationshipDeptRole, Long>,
        CommonRepository<RelationshipDeptRole, RelationshipDeptRoleRepository> {

    public List<RelationshipDeptRole> findByDeptId(Long deptId);

}
