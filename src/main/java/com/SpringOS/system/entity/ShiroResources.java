package com.SpringOS.system.entity;

import com.SpringOS.system.entity.common.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by AlbertXmas on 16/8/29.
 */
@Entity
@Table(name = "os_shiro_resources")
public class ShiroResources extends IdEntity {
    String realName = "";
    String name = "";
    String method = "";
    String shiroAuth = "";
    boolean available = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getShiroAuth() {
        return shiroAuth;
    }

    public void setShiroAuth(String shiroAuth) {
        this.shiroAuth = shiroAuth;
    }
}
