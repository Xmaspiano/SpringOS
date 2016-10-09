package com.SpringOS.system.web.pagevo;

import com.SpringOS.system.entity.RelationshipDeptRole;
import com.SpringOS.system.entity.Role;
import com.SpringOS.system.entity.common.IdEntity;
import org.springframework.stereotype.Component;

/**
 * Created by AlbertXmas on 2016/9/28.
 */
@Component
public class DeptRoleVO extends IdEntity {
    Long deptid;
    Long roleid;
    String role;
    String description;
    boolean available;

    public Long getDeptid() {
        return deptid;
    }

    public void setDeptid(Long deptid) {
        this.deptid = deptid;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Role toRoleEntity(){
        Role ro = new Role();
        ro.setId(roleid);
        ro.setDescription(description);
        ro.setAvailable(available);
        ro.setRole(role);
        return ro;
    }

    public RelationshipDeptRole toDeptRoleEntity(){
        RelationshipDeptRole DeptRole = new RelationshipDeptRole();
        DeptRole.setId(getId());
        DeptRole.setRoleId(roleid);
        DeptRole.setDeptId(deptid);
        return DeptRole;
    }
}
