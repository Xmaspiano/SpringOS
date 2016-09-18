package com.SpringOS.system.entity;

import com.SpringOS.system.entity.common.IdEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "os_role")
public class Role extends IdEntity {
    private String role; //角色标识 程序中判断使用,如"admin"
    private String description; //角色描述,UI界面显示使用
    private Boolean available = Boolean.FALSE; //是否可用,如果不可用将不会添加给用户

    private List<RelationshipUserRole> RelationshipUserRoleList;
    private Long userId;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @OneToMany(targetEntity = RelationshipUserRole.class,
            cascade = {
                    CascadeType.REFRESH,
                    CascadeType.REMOVE,
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH
            }, fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId", updatable = false)
    public List<RelationshipUserRole> getRelationshipUserRoleList() {
        return RelationshipUserRoleList;
    }

    public void setRelationshipUserRoleList(List<RelationshipUserRole> relationshipUserRoleList) {
        RelationshipUserRoleList = relationshipUserRoleList;
    }

    @Transient
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
