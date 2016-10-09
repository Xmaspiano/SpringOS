package com.SpringOS.system.service;



import com.SpringOS.system.entity.Department;
import com.SpringOS.system.entity.RelationshipDeptRole;
import com.SpringOS.system.entity.Role;


import java.util.List;
import java.util.Set;

public interface RelationshipDeptRoleService extends CommonService<RelationshipDeptRole> {

    public List<Role> findRolesByDept(Long deptId);

    public List<RelationshipDeptRole> findRelationshipsByDept(Long deptId);

    public List<Department> findDepts(List<RelationshipDeptRole> relationshipDeptRoleList);

    public List<Role> findRoles(List<RelationshipDeptRole> relationshipDeptRoleList);
}
