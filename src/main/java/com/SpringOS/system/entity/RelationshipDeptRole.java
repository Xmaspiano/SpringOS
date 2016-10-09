package com.SpringOS.system.entity;

import com.SpringOS.system.entity.common.IdEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by AlbertXmas on 16/8/17.
 */
@Entity
@Table(name = "os_relationship_dept_role")
public class RelationshipDeptRole extends IdEntity {

    private Long deptId;
    private Long roleId;
    private Role role;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    //    @Transient
    @OneToOne(targetEntity = Role.class,cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId",insertable=false, updatable=false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
