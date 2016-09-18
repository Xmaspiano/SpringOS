package com.SpringOS.system.service.impl;

import com.SpringOS.system.entity.RelationshipUserRole;
import com.SpringOS.system.entity.Role;
import com.SpringOS.system.entity.User;
import com.SpringOS.system.repository.RelationshipUserRoleRepository;
import com.SpringOS.system.service.RelationshipUserRoleService;
import com.SpringOS.system.service.RoleService;
import com.SpringOS.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RelationshipUserRoleServiceImpl
        extends CommonServiceImpl<RelationshipUserRole, RelationshipUserRoleRepository>
        implements RelationshipUserRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipUserRoleServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<String> findRoles(String username) {
        User user = userService.findByUsername(username);
        if(user == null) {
            return Collections.EMPTY_SET;
        }

        return getRoleSBySet(
                findRoles(user.getRelationshipUserRoleList())
            );
    }

    public List<User> findUsers(List<RelationshipUserRole> relationshipUserRoleList) {
        List<User> userList = new ArrayList<User>();
        for(RelationshipUserRole relationshipUserRole : relationshipUserRoleList) {
            User user = userService.findOne(relationshipUserRole.getUserId());
            if(user != null) {
                userList.add(user);
            }
        }
        return userList;
    }

    public List<Role> findRoles(List<RelationshipUserRole> relationshipUserRoleList) {
        List<Role> roleList = new ArrayList<Role>();
        for(RelationshipUserRole relationshipUserRole : relationshipUserRoleList) {
            Role role = roleService.findOne(relationshipUserRole.getRoleId());
            if(role != null) {
                roleList.add(role);
            }
        }
        return roleList;
    }

    private Set<String> getRoleSBySet(List<Role> roleList){
        Set<String> roles = new HashSet<String>();
        for(Role role : roleList) {
            if(role != null) {
                roles.add(role.getRole());
            }
        }
        return roles;
    }
}