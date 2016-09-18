package com.SpringOS.system.entity;

import com.SpringOS.system.entity.common.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Created by AlbertXmas on 16/9/7.
 */
@Entity
@Table(name = "os_favorite")
public class Favarite extends IdEntity{
    private Long userId;
    private Long menuId;
    private String sort;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
