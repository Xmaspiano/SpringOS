package com.SpringOS.system.entity;

import com.SpringOS.system.entity.common.IdEntity;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlbertXmas on 16/8/8.
 */
@Entity
@Table(name = "os_deptartment")
public class Department extends IdEntity {
    private String code;
    private String name;
    private Long parentid;
    private String remark;
    private Date datemark;
    private boolean life;

    private List<Department> departmentList = new ArrayList<Department>();
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentid", updatable = false)
    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public Date getDatemark() {
        return datemark;
    }

    public void setDatemark(Date datemark) {
        this.datemark = datemark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isLife() {
        return life;
    }

    public void setLife(boolean life) {
        this.life = life;
    }

}
