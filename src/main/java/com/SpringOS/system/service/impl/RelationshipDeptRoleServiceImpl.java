package com.SpringOS.system.service.impl;

import com.SpringOS.system.entity.Department;
import com.SpringOS.system.entity.RelationshipDeptRole;
import com.SpringOS.system.entity.Role;
import com.SpringOS.system.repository.RelationshipDeptRoleRepository;
import com.SpringOS.system.service.RelationshipDeptRoleService;
import com.SpringOS.system.service.RoleService;
import com.SpringOS.system.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RelationshipDeptRoleServiceImpl
        extends CommonServiceImpl<RelationshipDeptRole, RelationshipDeptRoleRepository>
        implements RelationshipDeptRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipDeptRoleServiceImpl.class);

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;

    @Override
    public RelationshipDeptRole save(RelationshipDeptRole relationshipDeptRole) {
        Role role = roleService.save(relationshipDeptRole.getRole());
        relationshipDeptRole.setRoleId(role.getId());
        return super.save(relationshipDeptRole);
    }

    @Override
    public void delete(Long id) {
        RelationshipDeptRole relationshipDeptRole = getRepository().findOne(id);
        roleService.delete(relationshipDeptRole.getRoleId());
        super.delete(id);
    }

    /**
     * 根据组织机构ID查询角色
     * @param deptId
     * @return
     */
    public List<Role> findRolesByDept(Long deptId) {
        return findRoles(findRelationshipsByDept(deptId));
    }

    /**
     * 根据组织机构ID查询关联表
     * @param deptId
     * @return
     */
    public List<RelationshipDeptRole> findRelationshipsByDept(Long deptId) {
        return getRepository().findByDeptId(deptId);
    }

    public List<Department> findDepts(List<RelationshipDeptRole> relationshipDeptRoleList) {
        List<Department> DeptList = new ArrayList<Department>();
        for(RelationshipDeptRole relationshipDeptRole : relationshipDeptRoleList) {
            Department Dept = departmentService.findOne(relationshipDeptRole.getDeptId());
            if(Dept != null) {
                DeptList.add(Dept);
            }
        }
        return DeptList;
    }

    public List<Role> findRoles(List<RelationshipDeptRole> relationshipDeptRoleList) {
        List<Role> roleList = new ArrayList<Role>();
        for(RelationshipDeptRole relationshipDeptRole : relationshipDeptRoleList) {
            Role role = roleService.findOne(relationshipDeptRole.getRoleId());
            if(role != null) {
                roleList.add(role);
            }
        }
        return roleList;
    }

}