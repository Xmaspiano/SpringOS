package com.SpringOS.system.entity;

import com.SpringOS.system.entity.common.IdEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "os_user")
public class User extends IdEntity {
    private String username; //用户名
    private String password; //密码
    private String salt; //加密密码的盐
//    private Long roleIds;
    private Boolean locked = Boolean.FALSE;

    private List<RelationshipUserRole> RelationshipUserRoleList;
    private Long roleId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

//    public Long getRoleIds() {
//        return roleIds;
//    }
//
//    public void setRoleIds(Long roleIds) {
//        this.roleIds = roleIds;
//    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @OneToMany(targetEntity = RelationshipUserRole.class,
            cascade = {
                    CascadeType.REFRESH,
                    CascadeType.REMOVE,
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH
            }, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", updatable = false)
    public List<RelationshipUserRole> getRelationshipUserRoleList() {
        return RelationshipUserRoleList;
    }

    public void setRelationshipUserRoleList(List<RelationshipUserRole> relationshipUserRoleList) {
        RelationshipUserRoleList = relationshipUserRoleList;
    }

    @Transient
    public String getCredentialsSalt() {
        return username + salt;
    }

    @Transient
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
