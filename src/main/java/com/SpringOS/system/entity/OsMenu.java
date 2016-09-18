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
public class OsMenu extends IdEntity {
    private String appid;
    private String name;
    private Long parentid;
    private String remark;
    private Date datemark;
    private boolean life;

    private List<OsMenu> OsMenuList = new ArrayList<OsMenu>();
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentid", updatable = false)
    public List<OsMenu> getOsMenuList() {
        return OsMenuList;
    }

    public void setOsMenuList(List<OsMenu> osMenuList) {
        OsMenuList = osMenuList;
    }

    public Date getDatemark() {
        return datemark;
    }

    public void setDatemark(Date datemark) {
        this.datemark = datemark;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
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
