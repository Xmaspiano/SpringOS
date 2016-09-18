package com.SpringOS.system.entity;

import com.SpringOS.system.entity.common.IdEntity;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by AlbertXmas on 16/8/17.
 */
@Entity
@Table(name = "os_relationship_user_role")
public class RelationshipUserRole extends IdEntity {

    private Long userId;
    private Long roleId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
