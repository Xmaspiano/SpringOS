package com.SpringOS.system.service;


import com.SpringOS.system.entity.RelationshipUserRole;
import com.SpringOS.system.entity.Role;
import com.SpringOS.system.entity.User;

import java.util.List;
import java.util.Set;

public interface RelationshipUserRoleService extends CommonService<RelationshipUserRole> {

    public Set<String> findRoles(String username);

    public List<User> findUsers(List<RelationshipUserRole> relationshipUserRoleList);

    public List<Role> findRoles(List<RelationshipUserRole> relationshipUserRoleList);
}
